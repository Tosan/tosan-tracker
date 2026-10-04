package com.tosan.tools.tracker.starter.serialization;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * @author AmirHossein ZamanZade
 * @since 5/17/2023
 */
public class NumberSerializer extends ValueSerializer<Number> {

    private final BaseFieldMaskSerializer baseSerializer;

    public NumberSerializer(BaseFieldMaskSerializer baseSerializer) {
        this.baseSerializer = baseSerializer;
    }

    @Override
    public void serialize(Number value, JsonGenerator jsonGenerator, SerializationContext serializers)
            throws JacksonException {
        baseSerializer.serialize(value.toString(), jsonGenerator, serializers);
    }
}
