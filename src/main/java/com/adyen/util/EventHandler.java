package com.adyen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * Helper class to process AfP events
 */
@Service
public class EventHandler {

    public String getEventType(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode type = jsonNode.get("type");

        return type != null ? type.asText() : null;
    }

    public String getEventEnvironment(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode environment = jsonNode.get("environment");

        return environment != null ? environment.asText() : null;
    }

}