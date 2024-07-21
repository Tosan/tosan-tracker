package com.tosan.tools.tracker.starter.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author M.khoshnevisan
 * @since 7/18/2023
 */
public class TrackerStaticMapper implements ApplicationContextAware {

    public static ObjectMapper objectMapper;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        objectMapper = (context.getBean("tracker-mask-object-mapper", ObjectMapper.class));
    }
}
