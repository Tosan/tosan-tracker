package com.tosan.tools.tracker.starter.config;

import com.tosan.tools.mask.starter.config.SecureParameter;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author M.khoshnevisan
 * @since 8/27/2023
 */
public class TrackConfig {

    /**
     * Parameters in the service input that will not be tracked
     */
    private Set<String> ignoredArgs = new HashSet<>();

    /**
     * Base exception classes in the application
     */
    private Set<Class> baseExceptions;

    /**
     * Locale of the application
     */
    private Locale locale;

    /**
     * Errors outside the baseExceptions list or its subsets will be categorized under the serviceExceptionName
     */
    private String serviceExceptionName;

    /**
     * Secure parameters that will be masked
     */
    private Set<SecureParameter> secureParameters = new HashSet<>();

    public Set<String> getIgnoredArgs() {
        return ignoredArgs;
    }

    public void setIgnoredArgs(Set<String> ignoredArgs) {
        this.ignoredArgs = ignoredArgs;
    }

    public Set<Class> getBaseExceptions() {
        return baseExceptions;
    }

    public void setBaseExceptions(Set<Class> baseExceptions) {
        this.baseExceptions = baseExceptions;
    }

    public String getServiceExceptionName() {
        return serviceExceptionName;
    }

    public void setServiceExceptionName(String serviceExceptionName) {
        this.serviceExceptionName = serviceExceptionName;
    }

    public Set<SecureParameter> getSecureParameters() {
        return secureParameters;
    }

    public void setSecureParameters(Set<SecureParameter> secureParameters) {
        this.secureParameters = secureParameters;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
