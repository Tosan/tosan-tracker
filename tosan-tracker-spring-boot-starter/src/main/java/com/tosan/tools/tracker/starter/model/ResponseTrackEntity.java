package com.tosan.tools.tracker.starter.model;

import java.util.Date;
import java.util.Map;

/**
 * @author F.Ebrahimi
 * @since 12/11/2023
 */
public interface ResponseTrackEntity extends TrackerEntity<Long> {

    Map<String, Object> getTrackData();

    void setTrackData(Map<String, Object> trackData);

    Date getRequestDate();

    void setRequestDate(Date requestDate);

    Date getResponseDate();

    void setResponseDate(Date responseDate);

    TrackType getTrackType();

    void setTrackType(TrackType trackType);

    String getExceptionName();

    void setExceptionName(String exceptionName);

    RequestTrackEntity getRequestTrack();

    void setRequestTrack(RequestTrackEntity baseRequestTrack);

}
