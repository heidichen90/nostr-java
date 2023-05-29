package com.heidi.nostrpoc.constant.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Request(
        @JsonProperty("limit")  Integer limit
) {
}
