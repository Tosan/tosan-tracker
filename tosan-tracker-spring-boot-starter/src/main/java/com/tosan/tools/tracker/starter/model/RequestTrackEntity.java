package com.tosan.tools.tracker.starter.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author F.Ebrahimi
 * @since 12/11/2023
 */
public interface RequestTrackEntity extends TrackerEntity<Long> {

    Map<String, Object> getTrackData();

    void setTrackData(Map<String, Object> trackData);

    Date getRequestDate();

    void setRequestDate(Date requestDate);

    String getClientAddress();

    void setClientAddress(String clientAddress);

    List<? extends ResponseTrackEntity> getResponseTrackList();

    void setResponseTrackList(List<ResponseTrackEntity> responseTrackList);

    ServiceEntity getService();

    void setService(ServiceEntity service);

}