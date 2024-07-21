package com.tosan.tools.tracker.starter.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author M.khoshnevisan
 * @since 7/25/2023
 */
public class ByteArraySerializer extends JsonSerializer<byte[]> {

    public ByteArraySerializer() {
    }

    @Override
    public void serialize(byte[] value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString("*MASKED");
    }
}