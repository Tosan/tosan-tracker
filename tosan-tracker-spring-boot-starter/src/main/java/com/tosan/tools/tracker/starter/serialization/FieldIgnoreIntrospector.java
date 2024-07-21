package com.tosan.tools.tracker.starter.serialization;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.tosan.tools.tracker.starter.api.SkipTracking;

/**
 * @author M.khoshnevisan
 * @since 8/23/2023
 */
public class FieldIgnoreIntrospector extends JacksonAnnotationIntrospector {

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember member) {
        return super.hasIgnoreMarker(member) || member.hasAnnotation(SkipTracking.class);
    }
}