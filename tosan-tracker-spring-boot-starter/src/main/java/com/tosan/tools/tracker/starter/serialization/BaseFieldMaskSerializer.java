package com.tosan.tools.tracker.starter.serialization;

import com.tosan.tools.mask.starter.replace.JsonReplaceHelperDecider;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * @author AmirHossein ZamanZade
 * @since 5/15/2023
 */
public class BaseFieldMaskSerializer extends ValueSerializer<String> {

    public BaseFieldMaskSerializer() {
    }

    private JsonReplaceHelperDecider jsonReplaceHelperDecider;

    public BaseFieldMaskSerializer(JsonReplaceHelperDecider jsonReplaceHelperDecider) {
        this.jsonReplaceHelperDecider = jsonReplaceHelperDecider;
    }

    public void serialize(String value, JsonGenerator jsonGenerator, SerializationContext serializationContext)
            throws JacksonException {
        if (value == null) {
            return;
        }
        String fieldName = jsonGenerator.streamWriteContext().currentName();
        if (fieldName == null) {
            TokenStreamContext parent = jsonGenerator.streamWriteContext().getParent();
            if (parent != null) {
                fieldName = parent.currentName();
            }
        }
        String maskedValue = jsonReplaceHelperDecider.replace(fieldName, value);
        jsonGenerator.writeString(maskedValue);
    }
}
