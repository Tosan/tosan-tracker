package com.tosan.tools.tracker.starter.model;

import java.io.Serializable;

/**
 * @author F.Ebrahimi
 * @since 12/11/2023
 */
public interface TrackerEntity<T> extends Serializable {

    T getId();

    void setId(T id);
}
