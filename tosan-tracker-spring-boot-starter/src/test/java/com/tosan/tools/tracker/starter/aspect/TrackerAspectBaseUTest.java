package com.tosan.tools.tracker.starter.aspect;

import com.tosan.tools.tracker.starter.api.Tracking;
import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.ServiceEntity;
import com.tosan.tools.tracker.starter.service.TrackerService;
import com.tosan.tools.tracker.starter.service.TrackingDataProvider;
import com.tosan.tools.tracker.starter.util.AnnotationUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author F.Ebrahimi
 * @since 2/13/2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TrackerAspectBaseUTest {

    @Mock
    protected TrackingDataProvider trackingDataProvider;

    @Mock
    protected TrackerService trackerService;

    @Mock
    protected ProceedingJoinPoint pjp;

    @Mock
    protected MethodSignature signature;

    @Mock
    protected AnnotationUtil annotationUtil;

    @Mock
    protected RequestTrackEntity requestTrack;

    @Mock
    protected ResponseTrackEntity responseTrack;

    @Mock
    protected ServiceEntity service;

    @Mock
    protected Tracking tracking;

    protected String serviceName = "testServiceName";

    protected Object[] args = new Object[2];

    protected String[] parameterNames = new String[20];

    protected TrackerAspect trackerAspect;

    @BeforeEach
    public void setup() {
        trackerAspect = new TrackerAspect(trackingDataProvider, annotationUtil, trackerService);
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(serviceName);
        when(pjp.getArgs()).thenReturn(args);
        when(signature.getParameterNames()).thenReturn(parameterNames);
        when(annotationUtil.getAnnotation(eq(pjp), eq(Tracking.class))).thenReturn(tracking);
        when(signature.getReturnType()).thenReturn(Object.class);
        when(trackingDataProvider.getService(serviceName)).thenReturn(service);
        when(trackingDataProvider.getRequestTrack(args, parameterNames)).thenReturn(requestTrack);
        when(trackingDataProvider.getResponseTrack(any(), any())).thenReturn(responseTrack);
        when(trackingDataProvider.getExceptionTrack(any(), eq(requestTrack))).thenReturn(responseTrack);
    }
}
