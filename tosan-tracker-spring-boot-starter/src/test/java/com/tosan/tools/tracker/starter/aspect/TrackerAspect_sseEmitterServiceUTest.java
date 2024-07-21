package com.tosan.tools.tracker.starter.aspect;

import com.tosan.tools.tracker.starter.service.StreamResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author F.Ebrahimi
 * @since 2/13/2024
 */
public class TrackerAspect_sseEmitterServiceUTest extends TrackerAspectBaseUTest {

    @Test
    public void whenTrackWithNoException_thenCallTrackAfterWithCorrectParameters() throws Throwable {
        when(trackerService.trackBeforeService(eq(args), eq(parameterNames), eq(service), eq(requestTrack), eq(tracking))).thenReturn(requestTrack);
        StreamResponse<?> streamResponse = mock(StreamResponse.class);
        when(pjp.proceed()).thenReturn(streamResponse);
        when(signature.getReturnType()).thenReturn(streamResponse.getClass());
        trackerAspect.track(pjp);
        verify(streamResponse).doOnEach(any());
    }

    @Test
    public void whenExceptionOccursDuringTrackAfterService_returnWithNoException() throws Throwable {
        when(trackerService.trackBeforeService(eq(args), eq(parameterNames), eq(service), eq(requestTrack), eq(tracking))).thenReturn(requestTrack);
        doThrow(RuntimeException.class).when(trackerService).trackAfterService(any(), any(), any(), any());
        StreamResponse<?> streamResponse = mock(StreamResponse.class);
        when(pjp.proceed()).thenReturn(streamResponse);
        when(signature.getReturnType()).thenReturn(streamResponse.getClass());
        assertDoesNotThrow(() -> trackerAspect.track(pjp));
        verify(streamResponse).onError(any());
    }
}
