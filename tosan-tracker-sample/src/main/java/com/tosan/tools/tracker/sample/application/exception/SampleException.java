package com.tosan.tools.tracker.sample.application.exception;

import lombok.Getter;

/**
 * @author M.khoshnevisan
 * @since 8/28/2023
 */
@Getter
public class SampleException extends Exception {
    private final String cif;

    public SampleException(String message, String cif) {
        super(message);
        this.cif = cif;
    }
}
