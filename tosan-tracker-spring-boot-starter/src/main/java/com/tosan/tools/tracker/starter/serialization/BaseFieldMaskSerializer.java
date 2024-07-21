package com.tosan.tools.tracker.starter.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;

import java.io.IOException;

/**
 * @author AmirHossein ZamanZade
 * @since 5/15/2023
 */
public class BaseFieldMaskSerializer extends JsonSerializer<String> {

    public BaseFieldMaskSerializer() {
    }

    private JsonReplaceHelperDecider jsonReplaceHelperDecider;

    public BaseFieldMaskSerializer(JsonReplaceHelperDecider jsonReplaceHelperDecider) {
        this.jsonReplaceHelperDecider = jsonReplaceHelperDecider;
    }

    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (value == null) {
            return;
        }
        String fieldName = jsonGenerator.getOutputContext().getCurrentName();
        if (fieldName == null) {
            JsonStreamContext parent = jsonGenerator.getOutputContext().getParent();
            if (parent != null) {
                fieldName = parent.getCurrentName();
            }
        }
        String maskedValue = jsonReplaceHelperDecider.replace(fieldName, value);
        jsonGenerator.writeString(maskedValue);
    }
}