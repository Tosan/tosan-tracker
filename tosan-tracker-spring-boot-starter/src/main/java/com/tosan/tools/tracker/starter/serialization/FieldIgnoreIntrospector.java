package com.tosan.tools.tracker.starter.serialization;

import com.tosan.tools.tracker.starter.api.SkipTracking;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.AnnotatedMember;
import tools.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * @author M.khoshnevisan
 * @since 8/23/2023
 */
public class FieldIgnoreIntrospector extends JacksonAnnotationIntrospector {

    @Override
    public boolean hasIgnoreMarker(MapperConfig<?> config, AnnotatedMember member) {
        return super.hasIgnoreMarker(config, member) || member.hasAnnotation(SkipTracking.class);
    }
}
