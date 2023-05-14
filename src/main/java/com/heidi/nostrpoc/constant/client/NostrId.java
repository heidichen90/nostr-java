package com.heidi.nostrpoc.constant.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.heidi.nostrpoc.util.NostrUtils;
import lombok.Builder;

import java.util.List;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NostrId(
        @JsonProperty("id") int id,
        @JsonProperty("pubkey") String pubkey,
        @JsonProperty("created_at") Long createdAt,
        @JsonProperty("kind") int kind,
        @JsonProperty("tags") List<String> tags,
        @JsonProperty("content") String content,
        @JsonProperty("sig") String sig
) implements IEvent{
    public String getSerializeHex256() throws JsonProcessingException {
        return NostrUtils.serializeHex256(this);
    }
}