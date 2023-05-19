package com.infogen.loyalty.validators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class ParamsValidator {

    public static Map<String, String> validateRequest(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            String jsonString = objectMapper.writeValueAsString(object);
            jsonNode = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> validationErrors = new HashMap<>();
        Iterator<String> fieldNames = jsonNode.fieldNames();
        JsonNode fieldValue;
        String fieldName;
        while (fieldNames.hasNext()) {
            fieldName = fieldNames.next();
            fieldValue = jsonNode.get(fieldName);
            if (Objects.equals(fieldValue.toString(), "null") || fieldValue.toString().isEmpty()
            || (fieldValue.toString().matches("-?\\d+(\\.\\d+)?") && fieldValue.toString().equals("0.0")
            || fieldValue.toString().equals("0"))) {
                validationErrors.put(fieldName, "parameter is missing or invalid");
            }
        }

        return validationErrors;
    }
}