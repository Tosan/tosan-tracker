package com.tosan.tools.tracker.starter.config;

import com.tosan.tools.mask.starter.business.enumeration.MaskType;
import com.tosan.tools.mask.starter.config.SecureParameter;
import com.tosan.tools.mask.starter.config.SecureParametersConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author M.khoshnevisan
 * @since 8/29/2023
 */
public class TrackerConfigurationUTest {
    private final TrackerConfiguration trackerConfiguration = new TrackerConfiguration();

    @Test
    public void testTrackConfig_normalCall_createConfigCorrectly() {
        TrackConfig trackConfig = trackerConfiguration.trackConfig();
        assertEquals(1, trackConfig.getBaseExceptions().size());
        assertTrue(trackConfig.getBaseExceptions().contains(Exception.class));
        assertEquals("serviceException", trackConfig.getServiceExceptionName());
        assertEquals(0, trackConfig.getIgnoredArgs().size());
        assertEquals(0, trackConfig.getSecureParameters().size());
    }

    @Test
    public void testTrackerSecureParametersConfig_nullSecureParameters_returnEmptyList() {
        TrackConfig trackConfig = Mockito.mock(TrackConfig.class);
        when(trackConfig.getSecureParameters()).thenReturn(null);
        SecureParametersConfig secureParametersConfig = trackerConfiguration.trackerSecureParametersConfig(trackConfig);
        assertNotNull(secureParametersConfig);
        assertEquals(0, secureParametersConfig.getSecuredParametersMap().size());
    }

    @Test
    public void testTrackerSecureParametersConfig_nonNullSecureParameters_returnRelatedSecureParameters() {
        TrackConfig trackConfig = Mockito.mock(TrackConfig.class);
        Set<SecureParameter> secureParameters = Set.of(new SecureParameter("pan", MaskType.SEMI));
        Map<String, SecureParameter> secureParametersMap = secureParameters.stream()
                .collect(Collectors.toMap(SecureParameter::getParameterName, (e) -> e));
        when(trackConfig.getSecureParameters()).thenReturn(secureParameters);
        SecureParametersConfig secureParametersConfig = trackerConfiguration.trackerSecureParametersConfig(trackConfig);
        assertNotNull(secureParametersConfig);
        assertEquals(secureParametersMap, secureParametersConfig.getSecuredParametersMap());
    }
}
