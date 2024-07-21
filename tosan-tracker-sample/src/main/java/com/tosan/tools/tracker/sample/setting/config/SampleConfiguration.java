package com.tosan.tools.tracker.sample.setting.config;

import com.tosan.tools.mask.starter.business.enumeration.MaskType;
import com.tosan.tools.mask.starter.config.SecureParameter;
import com.tosan.tools.tracker.sample.application.exception.SampleException;
import com.tosan.tools.tracker.starter.config.TrackConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author M.khoshnevisan
 * @since 8/28/2023
 */
@Configuration
public class SampleConfiguration {

    private final Set<SecureParameter> trackerSecureParameters = Set.of(
            new SecureParameter("authorization", MaskType.COMPLETE),
            new SecureParameter("proxy-authorization", MaskType.COMPLETE),
            new SecureParameter("password", MaskType.COMPLETE),
            new SecureParameter("username", MaskType.SEMI),
            new SecureParameter("pin", MaskType.COMPLETE),
            new SecureParameter("cvv2", MaskType.COMPLETE),
            new SecureParameter("expireDate", MaskType.COMPLETE)
    );

    @Bean
    public TrackConfig trackConfig() {
        TrackConfig trackConfig = new TrackConfig();
        trackConfig.setBaseExceptions(Set.of(SampleException.class));
        trackConfig.setServiceExceptionName("sampleServiceException");
        trackConfig.setSecureParameters(trackerSecureParameters);
        trackConfig.setIgnoredArgs(Set.of("bankId"));
        return trackConfig;
    }
}
