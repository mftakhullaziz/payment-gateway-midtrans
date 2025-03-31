package com.application.paymentmidtranssrv.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = false)
public class JsonUtility implements AttributeConverter<JsonNode, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        try {
            return attribute != null ? objectMapper.writeValueAsString(attribute) : null;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JsonNode to String", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? objectMapper.readTree(dbData) : null;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting String to JsonNode", e);
        }
    }

    public static String toJson(Object object) {
        try {
            return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object)
                .replace("\n", "");
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Object to String", e);
        }
    }
}
