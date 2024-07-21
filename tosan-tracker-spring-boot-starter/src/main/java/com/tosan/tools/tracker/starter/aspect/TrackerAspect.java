package com.tosan.tools.tracker.starter.aspect;

import com.tosan.tools.tracker.starter.api.Tracking;
import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.ServiceEntity;
import com.tosan.tools.tracker.starter.model.TrackType;
import com.tosan.tools.tracker.starter.service.StreamResponse;
import com.tosan.tools.tracker.starter.service.TrackerService;
import com.tosan.tools.tracker.starter.service.TrackingDataProvider;
import com.tosan.tools.tracker.starter.util.AnnotationUtil;
import jakarta.transaction.Transactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * @author F.Ebrahimi
 * @since 2/5/2024
 */
@Aspect
@Order(20)
@Transactional
public class TrackerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackerAspect.class);

    private final TrackingDataProvider trackingDataProvider;

    private final AnnotationUtil annotationUtil;

    private final TrackerService trackerService;

    public TrackerAspect(TrackingDataProvider trackingDataProvider, AnnotationUtil annotationUtil, TrackerService trackerService) {
        this.trackingDataProvider = trackingDataProvider;
        this.annotationUtil = annotationUtil;
        this.trackerService = trackerService;
    }

    @Around(value = "@annotation(com.tosan.tools.tracker.starter.api.Tracking) ")
    public Object track(ProceedingJoinPoint pjp) throws Throwable {
        String serviceName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        String[] parameterNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Tracking trackingAnnotation = annotationUtil.getAnnotation(pjp, Tracking.class);
        RequestTrackEntity requestTrack = trackingDataProvider.getRequestTrack(args, parameterNames);
        ServiceEntity service = trackingDataProvider.getService(serviceName);
        RequestTrackEntity resultRequestTrack =
                trackerService.trackBeforeService(args, parameterNames, service, requestTrack, trackingAnnotation);
        Object returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
        if (Arrays.asList(((Class) returnType).getInterfaces()).contains(StreamResponse.class)) {
            return trackSseEmitter(pjp, requestTrack, trackingAnnotation);
        } else {
            return trackNormalService(pjp, resultRequestTrack, trackingAnnotation);
        }
    }

    private Object trackSseEmitter(ProceedingJoinPoint pjp, RequestTrackEntity requestTrack, Tracking trackingAnnotation)
            throws Throwable {
        Object proceed = pjp.proceed();
        ((StreamResponse<?>) proceed)
                .doOnEach(responseDto -> trackAfterService(responseDto, requestTrack, trackingAnnotation));
        ((StreamResponse<?>) proceed)
                .onError(throwable -> trackAfterException(requestTrack, throwable));
        return proceed;
    }

    private Object trackNormalService(ProceedingJoinPoint pjp, RequestTrackEntity requestTrack, Tracking trackingAnnotation)
            throws Throwable {
        try {
            Object proceed = pjp.proceed();
            trackAfterService(proceed, requestTrack, trackingAnnotation);
            return proceed;
        } catch (Throwable e) {
            trackAfterException(requestTrack, e);
            throw e;
        }
    }

    private void trackAfterException(RequestTrackEntity requestTrack, Throwable e) {
        try {
            ResponseTrackEntity responseTrack = trackingDataProvider.getExceptionTrack(e, requestTrack);
            trackerService.trackOnException(e, requestTrack, responseTrack);
        } catch (Exception ex) {
            LOGGER.error("error on exception tracking ", e);
        }
    }

    private void trackAfterService(Object result, RequestTrackEntity requestTrack, Tracking trackingAnnotation) {
        try {
            ResponseTrackEntity responseTrack = trackingDataProvider.getResponseTrack(result, requestTrack);
            if (responseTrack.getTrackType() != null && responseTrack.getTrackType().equals(TrackType.EXCEPTION)) {
                trackerService.trackOnStreamException(requestTrack, responseTrack);
            } else {
                trackerService.trackAfterService(result, requestTrack, trackingAnnotation, responseTrack);
            }
        } catch (Exception e) {
            LOGGER.error("error on response tracking ", e);
        }
    }
}
