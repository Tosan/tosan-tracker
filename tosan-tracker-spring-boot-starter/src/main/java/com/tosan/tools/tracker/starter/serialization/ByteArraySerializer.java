package com.tosan.tools.tracker.starter.serialization;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * @author M.khoshnevisan
 * @since 7/25/2023
 */
public class ByteArraySerializer extends ValueSerializer<byte[]> {

    public ByteArraySerializer() {
    }

    @Override
    public void serialize(byte[] value, JsonGenerator jsonGenerator, SerializationContext serializers)
            throws JacksonException {
        jsonGenerator.writeString("*MASKED");
    }
}
