package com.tosan.tools.tracker.starter.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tosan.tools.mask.starter.config.SecureParameter;
import com.tosan.tools.mask.starter.config.SecureParametersConfig;
import com.tosan.tools.mask.starter.replace.JacksonReplaceHelper;
import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;
import com.tosan.tools.mask.starter.replace.RegexReplaceHelper;
import com.tosan.tools.tracker.starter.aspect.TrackerAspect;
import com.tosan.tools.tracker.starter.config.documentation.DocumentationResourceBundle;
import com.tosan.tools.tracker.starter.config.documentation.TrackerKeyDeserializer;
import com.tosan.tools.tracker.starter.dao.RequestTrackDao;
import com.tosan.tools.tracker.starter.dao.ResponseTrackDao;
import com.tosan.tools.tracker.starter.model.TrackerStaticMapper;
import com.tosan.tools.tracker.starter.serialization.*;
import com.tosan.tools.tracker.starter.service.ExceptionHandlerUtil;
import com.tosan.tools.tracker.starter.service.TrackerService;
import com.tosan.tools.tracker.starter.service.TrackerServiceImpl;
import com.tosan.tools.tracker.starter.service.TrackingDataProvider;
import com.tosan.tools.tracker.starter.util.AnnotationUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.deser.jdk.UntypedObjectDeserializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author M.khoshnevisan
 * @since 8/27/2023
 */
@Configuration
public class TrackerConfiguration {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String SERVICE_EXCEPTION_NAME = "serviceException";

    @Bean
    @ConditionalOnMissingBean
    public TrackConfig trackConfig() {
        TrackConfig trackConfig = new TrackConfig();
        trackConfig.setBaseExceptions(Set.of(Exception.class));
        trackConfig.setServiceExceptionName(SERVICE_EXCEPTION_NAME);
        return trackConfig;
    }

    @Bean("tracker-secure-parameters")
    public SecureParametersConfig trackerSecureParametersConfig(TrackConfig trackerConfig) {
        Set<SecureParameter> secureParameters = trackerConfig.getSecureParameters();
        return new SecureParametersConfig(secureParameters == null ? new HashSet<>() : secureParameters);
    }

    @Bean("tracker-replace-helper")
    public JsonReplaceHelperDecider trackerReplaceHelperDecider(
            JacksonReplaceHelper jacksonReplaceHelper,
            RegexReplaceHelper regexReplaceHelper,
            @Qualifier("tracker-secure-parameters") SecureParametersConfig secureParametersConfig) {
        return new JsonReplaceHelperDecider(jacksonReplaceHelper, regexReplaceHelper, secureParametersConfig);
    }

    @Bean("tracker-field-base-mask-serializer")
    public BaseFieldMaskSerializer trackerFieldMaskBaseSerializer(
            @Qualifier("tracker-replace-helper") JsonReplaceHelperDecider jsonReplaceHelperDecider) {
        return new BaseFieldMaskSerializer(jsonReplaceHelperDecider);
    }

    @Bean("tracker-documentation-resource-bundle")
    public DocumentationResourceBundle documentationResourceBundle(TrackConfig trackerConfig) {
        return new DocumentationResourceBundle(trackerConfig);
    }

    @Bean("tracker-key-deserializer")
    public TrackerKeyDeserializer trackerKeyDeserializer(
            @Qualifier("tracker-documentation-resource-bundle") DocumentationResourceBundle documentationResourceBundle) {
        return new TrackerKeyDeserializer(documentationResourceBundle);
    }

    @Bean(name = "tracker-mask-object-mapper", autowireCandidate = false)
    public ObjectMapper trackerObjectMapper(
            @Qualifier("tracker-field-base-mask-serializer") BaseFieldMaskSerializer baseFieldMaskSerializer,
            @Qualifier("tracker-documentation-resource-bundle") DocumentationResourceBundle documentationResourceBundle,
            @Qualifier("tracker-key-deserializer") TrackerKeyDeserializer trackerKeyDeserializer) {
        return createTrackerObjectMapper(baseFieldMaskSerializer, documentationResourceBundle, trackerKeyDeserializer);
    }

    private static ObjectMapper createTrackerObjectMapper(
            BaseFieldMaskSerializer baseFieldMaskSerializer,
            DocumentationResourceBundle resourceBundle,
            TrackerKeyDeserializer trackerKeyDeserializer
    ) {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(Map.class, LinkedHashMap.class);
        module.addSerializer(String.class, new StringSerializer(baseFieldMaskSerializer));
        module.addSerializer(Number.class, new NumberSerializer(baseFieldMaskSerializer));
        module.addSerializer(byte[].class, new ByteArraySerializer());
        if (!resourceBundle.getAllDocumentations().isEmpty()) {
            module.addKeyDeserializer(Object.class, trackerKeyDeserializer);
            module.addDeserializer(Object.class, new UntypedObjectDeserializer(null, null));
        }
        return JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .changeDefaultPropertyInclusion(incl ->
                        JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.ALWAYS))
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .defaultDateFormat(new SimpleDateFormat(DATE_TIME_PATTERN))
                .annotationIntrospector(new FieldIgnoreIntrospector())
                .addModule(module)
                .build();
    }

    @Bean
    public TrackerStaticMapper trackerStaticMapper() {
        return new TrackerStaticMapper();
    }

    @Bean("tracker-exception-handler-util")
    public ExceptionHandlerUtil exceptionHandlerUtil() {
        return new ExceptionHandlerUtil();
    }

    @Bean
    public RequestTrackDao requestTrackDao() {
        return new RequestTrackDao();
    }

    @Bean
    public ResponseTrackDao responseTrackDao() {
        return new ResponseTrackDao();
    }

    @Bean("tracker-annotation-util")
    public AnnotationUtil annotationUtil() {
        return new AnnotationUtil();
    }

    @Bean
    public TrackerAspect trackerAspect(
            TrackingDataProvider trackingDataProvider,
            AnnotationUtil annotationUtil,
            TrackerService trackerService) {
        return new TrackerAspect(trackingDataProvider, annotationUtil, trackerService);
    }

    @Bean("tracker-tracker-service")
    public TrackerService trackerService(
            RequestTrackDao requestTrackDao,
            ResponseTrackDao responseTrackDao,
            TrackConfig trackConfig,
            ExceptionHandlerUtil exceptionHandlerUtil) {
        return new TrackerServiceImpl(requestTrackDao, responseTrackDao, trackConfig, exceptionHandlerUtil);
    }
}
