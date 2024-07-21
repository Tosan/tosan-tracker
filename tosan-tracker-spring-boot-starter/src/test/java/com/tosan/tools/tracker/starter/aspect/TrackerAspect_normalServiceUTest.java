package com.tosan.tools.tracker.starter.aspect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author F.Ebrahimi
 * @since 2/13/2024
 */
public class TrackerAspect_normalServiceUTest extends TrackerAspectBaseUTest {

    @Test
    public void always_callTrackBeforeServiceWithCorrectParameters() throws Throwable {
        when(pjp.getSignature().getName()).thenReturn(serviceName);
        when(signature.getReturnType()).thenReturn(Object.class);
        trackerAspect.track(pjp);
        verify(trackingDataProvider).getRequestTrack(eq(args), eq(parameterNames));
        verify(trackerService).trackBeforeService(eq(args), eq(parameterNames), eq(service), eq(requestTrack), eq(tracking));
    }

    @Test
    public void always_callTrackAfterServiceWithCorrectParameters() throws Throwable {
        when(signature.getReturnType()).thenReturn(Object.class);
        Object result = new Object();
        when(trackerService.trackBeforeService(eq(args), eq(parameterNames), eq(service), eq(requestTrack), eq(tracking))).thenReturn(requestTrack);
        when(pjp.proceed()).thenReturn(result);
        trackerAspect.track(pjp);
        verify(trackingDataProvider).getResponseTrack(eq(result), eq(requestTrack));
        verify(trackerService).trackAfterService(eq(result), eq(requestTrack), eq(tracking), eq(responseTrack));
    }

    @Test
    public void whenExceptionOccurs_thenCallTrackAfterExceptionWithCorrectParameters() throws Throwable {
        when(signature.getReturnType()).thenReturn(Object.class);
        when(trackerService.trackBeforeService(eq(args), eq(parameterNames), eq(service), eq(requestTrack), eq(tracking))).thenReturn(requestTrack);
        RuntimeException runtimeException = new RuntimeException("test exception");
        when(pjp.proceed()).thenThrow(runtimeException);
        assertThrows(RuntimeException.class, () -> trackerAspect.track(pjp));
        verify(trackingDataProvider).getExceptionTrack(eq(runtimeException), eq(requestTrack));
        verify(trackerService, times(1)).trackOnException(eq(runtimeException), eq(requestTrack), any());
    }
}