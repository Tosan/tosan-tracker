package com.tosan.tools.tracker.starter.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author F.Ebrahimi
 * @since 2/5/2024
 */
public class AnnotationUtil {

    public <T extends Annotation> T getAnnotation(ProceedingJoinPoint ctx, Class clazz) {
        MethodSignature methodSignature = (MethodSignature) ctx.getSignature();
        Method method = methodSignature.getMethod();
        Method meth = AopUtils.getMostSpecificMethod(method, ctx.getTarget().getClass());
        return (T) meth.getAnnotation(clazz);
    }
}