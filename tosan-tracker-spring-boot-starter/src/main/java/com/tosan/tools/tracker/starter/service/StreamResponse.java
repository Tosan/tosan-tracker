package com.tosan.tools.tracker.starter.service;

import java.util.function.Consumer;

/**
 * @author F.Ebrahimi
 * @since 2/7/2024
 */
public interface StreamResponse<T> {

    void doOnEach(Consumer<T> consumer);

    void onError(Consumer<Throwable> callback);
}