package com.tosan.tools.tracker.starter.serialization;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * @author AmirHossein ZamanZade
 * @since 5/17/2023
 */
public class StringSerializer extends ValueSerializer<String> {

    private final BaseFieldMaskSerializer baseSerializer;

    public StringSerializer(BaseFieldMaskSerializer baseSerializer) {
        this.baseSerializer = baseSerializer;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializationContext serializers)
            throws JacksonException {
        baseSerializer.serialize(value, jsonGenerator, serializers);
    }
}
