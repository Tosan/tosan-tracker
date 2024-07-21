package com.tosan.tools.tracker.starter.service;

import com.tosan.tools.tracker.starter.api.Tracking;
import com.tosan.tools.tracker.starter.config.TrackConfig;
import com.tosan.tools.tracker.starter.dao.RequestTrackDao;
import com.tosan.tools.tracker.starter.dao.ResponseTrackDao;
import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.ServiceEntity;
import com.tosan.tools.tracker.starter.model.TrackType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 7/17/2023
 */
public class TrackerServiceImpl implements TrackerService {

    public static final String EXCEPTION_PARAM_NAME = "exceptionParam";
    public static final String RESPONSE_PARAM_NAME = "response";
    public static final String MESSAGE_PARAM_NAME = "message";

    private final RequestTrackDao requestTrackDao;

    private final ResponseTrackDao responseTrackDao;

    private final TrackConfig trackConfig;

    private final ExceptionHandlerUtil exceptionHandlerUtil;

    public TrackerServiceImpl(RequestTrackDao requestTrackDao, ResponseTrackDao responseTrackDao, TrackConfig trackConfig,
                              ExceptionHandlerUtil exceptionHandlerUtil) {
        this.requestTrackDao = requestTrackDao;
        this.responseTrackDao = responseTrackDao;
        this.trackConfig = trackConfig;
        this.exceptionHandlerUtil = exceptionHandlerUtil;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RequestTrackEntity trackBeforeService(Object[] args, String[] parameterNames, ServiceEntity service,
                                                 RequestTrackEntity requestTrack, Tracking trackingAnnotation) {
        requestTrack.setService(service);
        Map<String, Object> map = new HashMap<>();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (!trackConfig.getIgnoredArgs().contains(parameterNames[i])) {
                    map.put(parameterNames[i], args[i]);
                }
            }
        }
        if (trackingAnnotation.trackRequest()) {
            requestTrack.setTrackData(map);
        }
        requestTrack.setRequestDate(new Date());
        return requestTrackDao.save(requestTrack);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void trackAfterService(Object arg, RequestTrackEntity requestTrack, Tracking trackingAnnotation,
                                  ResponseTrackEntity responseTrack) {
        responseTrack.setRequestTrack(requestTrack);
        if (trackingAnnotation.trackResponse()) {
            responseTrack.setTrackData(new HashMap<>() {{
                put(RESPONSE_PARAM_NAME, arg);
            }});
        }
        responseTrack.setTrackType(TrackType.RESPONSE);
        responseTrack.setRequestDate(requestTrack.getRequestDate());
        responseTrack.setResponseDate(new Date());
        responseTrackDao.save(responseTrack);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void trackOnException(Throwable exception, RequestTrackEntity requestTrack, ResponseTrackEntity exceptionTrack) {
        exceptionTrack.setRequestTrack(requestTrack);
        for (Class baseException : trackConfig.getBaseExceptions()) {
            if (exception.getClass().isNestmateOf(baseException)) {
                exceptionTrack.setExceptionName(exception.getClass().getSimpleName());
                break;
            }
        }
        if (exceptionTrack.getExceptionName() == null) {
            exceptionTrack.setExceptionName(trackConfig.getServiceExceptionName());
        }
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> errorParams = exceptionHandlerUtil.getExceptionParam(exception);
        if (errorParams != null && !errorParams.isEmpty()) {
            map.put(EXCEPTION_PARAM_NAME, errorParams);
        }
        map.put(MESSAGE_PARAM_NAME, exception.getMessage());
        exceptionTrack.setTrackType(TrackType.EXCEPTION);
        exceptionTrack.setTrackData(map);
        exceptionTrack.setRequestDate(requestTrack.getRequestDate());
        exceptionTrack.setResponseDate(new Date());
        responseTrackDao.save(exceptionTrack);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void trackOnException(String exceptionName, Map<String, Object> errorParams, String exceptionMessage,
                                 RequestTrackEntity requestTrack, ResponseTrackEntity exceptionTrack) {
        exceptionTrack.setRequestTrack(requestTrack);
        exceptionTrack.setExceptionName(exceptionName);

        Map<String, Object> map = new HashMap<>();
        if (errorParams != null && !errorParams.isEmpty()) {
            map.put(EXCEPTION_PARAM_NAME, errorParams);
        }
        map.put(MESSAGE_PARAM_NAME, exceptionMessage);
        exceptionTrack.setTrackType(TrackType.EXCEPTION);
        exceptionTrack.setTrackData(map);
        exceptionTrack.setRequestDate(requestTrack.getRequestDate());
        exceptionTrack.setResponseDate(new Date());
        responseTrackDao.save(exceptionTrack);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void trackOnStreamException(RequestTrackEntity requestTrack, ResponseTrackEntity exceptionTrack) {
        exceptionTrack.setRequestTrack(requestTrack);
        exceptionTrack.setTrackType(TrackType.EXCEPTION);
        exceptionTrack.setRequestDate(requestTrack.getRequestDate());
        exceptionTrack.setResponseDate(new Date());
        responseTrackDao.save(exceptionTrack);
    }
}
