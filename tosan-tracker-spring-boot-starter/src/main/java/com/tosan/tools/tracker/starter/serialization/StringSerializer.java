package com.tosan.tools.tracker.starter.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author AmirHossein ZamanZade
 * @since 5/17/2023
 */
public class StringSerializer extends JsonSerializer<String> {

    private final BaseFieldMaskSerializer baseSerializer;

    public StringSerializer(BaseFieldMaskSerializer baseSerializer) {
        this.baseSerializer = baseSerializer;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        baseSerializer.serialize(value, jsonGenerator, serializers);
    }
}