package com.heidi.nostrpoc.constant.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NostrEvent(
        @JsonProperty("id") String id,
        @JsonProperty("pubkey") String pubkey,
        @JsonProperty("created_at") Long createdAt,
        @JsonProperty("kind") Integer kind,
        @JsonProperty("tags") List<String> tags,
        @JsonProperty("content") String content,
        @JsonProperty("sig") String sig
) implements IEvent {

    public String generateId() throws JsonProcessingException{
        return NostrId.builder()
                .pubkey(this.pubkey)
                .createdAt(this.createdAt)
                .kind(this.kind)
                .tags(this.tags)
                .content(this.content)
                .build()
                .getSerializeHex256();
    }

}
