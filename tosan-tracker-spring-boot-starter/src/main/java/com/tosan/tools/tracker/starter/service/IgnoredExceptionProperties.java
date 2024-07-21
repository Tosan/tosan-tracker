package com.tosan.tools.tracker.starter.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author AmirHossein ZamanZade
 * @since 2/19/2023
 */
@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "message", "localizedMessage", "httpStatus", "exceptionType",
        "extraParams", "exceptionIncidentState", "errorCode", "errorType"})
public abstract class IgnoredExceptionProperties {
}
