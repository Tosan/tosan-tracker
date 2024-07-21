package com.tosan.tools.tracker.starter.service;

import com.tosan.tools.tracker.starter.api.Tracking;
import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.ServiceEntity;

import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 8/27/2023
 */
public interface TrackerService {

    RequestTrackEntity trackBeforeService(Object[] args, String[] parameterNames, ServiceEntity service,
                                          RequestTrackEntity requestTrack, Tracking trackingAnnotation);

    void trackAfterService(Object arg, RequestTrackEntity requestTrack, Tracking trackingAnnotation,
                           ResponseTrackEntity responseTrack);

    void trackOnException(Throwable exception, RequestTrackEntity requestTrack, ResponseTrackEntity exceptionTrack);

    void trackOnException(String exceptionName, Map<String, Object> errorParams, String exceptionMessage,
                          RequestTrackEntity requestTrack, ResponseTrackEntity exceptionTrack);

    void trackOnStreamException(RequestTrackEntity requestTrack, ResponseTrackEntity exceptionTrack);
}
