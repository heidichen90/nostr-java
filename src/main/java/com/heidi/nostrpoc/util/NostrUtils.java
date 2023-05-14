package com.heidi.nostrpoc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.heidi.nostrpoc.constant.client.NostrId;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class NostrUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<String> deserializeEvent(String serializedEvent) throws JsonMappingException, JsonProcessingException {
        return objectMapper.readValue(serializedEvent, List.class);
    }


    public static String serializeEvent(List<Object> NostrPayload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(NostrPayload);
    }

    public static String serializeHex256(NostrId nostrId) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(nostrId);
        String cleanedJsonString = jsonString.replaceAll("\\s+", "");
        byte[] jsonBytes = cleanedJsonString.getBytes(StandardCharsets.UTF_8);

        String sha256HexString = Hashing.sha256().hashBytes(jsonBytes).toString();

        return sha256HexString;
    }
}
