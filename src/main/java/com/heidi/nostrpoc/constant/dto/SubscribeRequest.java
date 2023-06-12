package com.heidi.nostrpoc.constant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubscribeRequest(
        @JsonProperty("nostrServer") String nostrServer
) {
}
