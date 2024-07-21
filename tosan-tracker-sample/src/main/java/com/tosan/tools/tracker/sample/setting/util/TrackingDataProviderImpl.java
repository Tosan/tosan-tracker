package com.tosan.tools.tracker.sample.setting.util;

import com.tosan.tools.tracker.sample.setting.entity.RequestTrack;
import com.tosan.tools.tracker.sample.setting.entity.ResponseTrack;
import com.tosan.tools.tracker.sample.setting.repository.ServiceRepository;
import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.ServiceEntity;
import com.tosan.tools.tracker.starter.service.TrackingDataProvider;
import org.springframework.stereotype.Component;

/**
 * @author F.Ebrahimi
 * @since 2/14/2024
 */
@Component
public class TrackingDataProviderImpl implements TrackingDataProvider {

    private final ServiceRepository serviceRepository;

    public TrackingDataProviderImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public RequestTrackEntity getRequestTrack(Object[] args, String[] parameterNames) {
        RequestTrack requestTrack = new RequestTrack();
        addExtraDataToRequestTrack(requestTrack);
        return requestTrack;
    }

    @Override
    public ResponseTrackEntity getResponseTrack(Object arg, RequestTrackEntity requestTrack) {
        ResponseTrack responseTrack = new ResponseTrack();
        addExtraDataToResponseTrack(responseTrack);
        return responseTrack;
    }

    @Override
    public ResponseTrackEntity getExceptionTrack(Throwable exception, RequestTrackEntity requestTrack) {
        ResponseTrack exceptionTrack = new ResponseTrack();
        addExtraDataToResponseTrack(exceptionTrack);
        return exceptionTrack;
    }

    @Override
    public ServiceEntity getService(String serviceName) {
        return serviceRepository.findByServiceName(serviceName);
    }

    private void addExtraDataToRequestTrack(RequestTrack requestTrack) {
        String clientAddress = "1.1.1.1";
        requestTrack.setClientAddress(clientAddress);
        String tokenHash = "4958584";
        requestTrack.setTokenHash(tokenHash);
        Long instanceNumber = 2L;
        requestTrack.setInstanceNumber(instanceNumber);
    }

    private void addExtraDataToResponseTrack(ResponseTrack responseTrack) {
        Long instanceNumber = 2L;
        responseTrack.setInstanceNumber(instanceNumber);
    }
}