package com.tosan.tools.tracker.starter.config.documentation;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

/**
 * @author M.khoshnevisan
 * @since 8/13/2023
 */
public class TrackerKeyDeserializer extends KeyDeserializer {

    private final DocumentationResourceBundle documentationResourceBundle;

    public TrackerKeyDeserializer(DocumentationResourceBundle documentationResourceBundle) {
        this.documentationResourceBundle = documentationResourceBundle;
    }

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) {
        String translatedKey = documentationResourceBundle.geDocumentation().get(key != null ? key.toLowerCase() : key);
        key = translatedKey == null ? key : translatedKey;
        return key;
    }
}
