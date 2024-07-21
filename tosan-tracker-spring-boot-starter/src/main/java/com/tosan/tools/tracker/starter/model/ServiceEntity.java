package com.tosan.tools.tracker.starter.model;

/**
 * @author F.Ebrahimi
 * @since 12/11/2023
 */
public interface ServiceEntity extends TrackerEntity<Long> {

    String getServiceName();

    void setServiceName(String serviceName);

}
