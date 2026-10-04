package com.tosan.tools.tracker.starter.service;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.introspect.MixInResolver;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 8/27/2023
 */
public class ExceptionHandlerUtil {

    private final ObjectMapper objectMapper = JsonMapper.shared();

    public <T> Map<String, Object> getExceptionParam(T object) {
        return jsonToMap(objectToJsonForRestException(object, IgnoredExceptionProperties.class));
    }

    private Map<String, Object> jsonToMap(String jsonString) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
            };
            return objectMapper.readValue(jsonString, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("error in converting Json to map JSONObject", e);
        }
    }

    private <T> String objectToJsonForRestException(T object, Class ignorePropertiesRestException) {
        ObjectMapper objectMapperForRestException = createObjectMapperWthMixedInResolver(object, ignorePropertiesRestException);
        try {
            return objectMapperForRestException.writer().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("error in converting object to Json", e);
        }
    }

    private <T> ObjectMapper createObjectMapperWthMixedInResolver(T object, Class ignoreProperties) {
        return objectMapper.rebuild()
                .mixInOverrides(new MixInResolver() {
                    @Override
                    public Class<?> findMixInClassFor(Class<?> cls) {
                        return ignoreProperties;
                    }

                    @Override
                    public boolean hasMixIns() {
                        return true;
                    }

                    @Override
                    public MixInResolver snapshot() {
                        return this;
                    }
                })
                .addMixIn(object.getClass(), ignoreProperties)
                .build();
    }
}
