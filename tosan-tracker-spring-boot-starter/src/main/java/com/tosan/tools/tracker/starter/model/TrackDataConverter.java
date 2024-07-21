package com.tosan.tools.tracker.starter.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.AttributeConverter;

import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 7/15/2023
 */
public class TrackDataConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Object> trackData) {
        if (trackData == null) {
            return null;
        }
        try {
            return TrackerStaticMapper.objectMapper.writeValueAsString(trackData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("invalid json: {}", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertToEntityAttribute(String trackData) {
        if (trackData == null) {
            return null;
        }
        try {
            return TrackerStaticMapper.objectMapper.readValue(trackData, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("invalid json: {}", e);
        }
    }
}