package com.tosan.tools.tracker.starter.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author M.khoshnevisan
 * @since 7/17/2023
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Tracking {

    boolean trackRequest() default true;

    boolean trackResponse() default true;
}