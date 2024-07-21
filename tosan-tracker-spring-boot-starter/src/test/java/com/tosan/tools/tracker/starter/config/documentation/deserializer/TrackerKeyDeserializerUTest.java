package com.tosan.tools.tracker.starter.config.documentation.deserializer;

import com.tosan.tools.tracker.starter.config.documentation.DocumentationResourceBundle;
import com.tosan.tools.tracker.starter.config.documentation.TrackerKeyDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/**
 * @author M.khoshnevisan
 * @since 8/14/2023
 */
@ExtendWith(MockitoExtension.class)
public class TrackerKeyDeserializerUTest {

    @Mock
    private DocumentationResourceBundle documentationResourceBundle;

    private TrackerKeyDeserializer trackerKeyDeserializer;

    @BeforeEach
    public void setup() {
        trackerKeyDeserializer = new TrackerKeyDeserializer(documentationResourceBundle);
    }

    @Test
    public void testDeserializeKey_nullKey_callResourceBundleWithNoException() {
        Object translatedKey = trackerKeyDeserializer.deserializeKey(null, null);
        assertNull(translatedKey);
    }

    @Test
    public void testDeserializeKey_nonNullKey_searchWithLowerCaseKey() {
        String key = "fkdjfdFF";
        String newKey = "newTrackKey";
        Map<String, String> docMap = new HashMap<>() {{
            put(key.toLowerCase(), newKey);
        }};
        when(documentationResourceBundle.geDocumentation()).thenReturn(docMap);
        Object translatedKey = trackerKeyDeserializer.deserializeKey(key, null);
        assertEquals(newKey, translatedKey);
    }

    @Test
    public void testDeserializeKey_keyNotFoundInResourceBundle_returnOriginalKey() {
        String key = "nonExistingKey";
        String newKey = "newTrackKey";
        Map<String, String> docMap = new HashMap<>() {{
            put("irnfksf", newKey);
        }};
        when(documentationResourceBundle.geDocumentation()).thenReturn(docMap);
        Object translatedKey = trackerKeyDeserializer.deserializeKey(key, null);
        assertEquals(key, translatedKey);
    }
}
