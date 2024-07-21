package com.tosan.tools.tracker.starter.service;

import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.ServiceEntity;

/**
 * @author F.Ebrahimi
 * @since 2/5/2024
 */
public interface TrackingDataProvider {

    RequestTrackEntity getRequestTrack(Object[] args, String[] parameterNames);

    ResponseTrackEntity getResponseTrack(Object arg, RequestTrackEntity requestTrack);

    ResponseTrackEntity getExceptionTrack(Throwable exception, RequestTrackEntity requestTrack);

    ServiceEntity getService(String serviceName);
}
