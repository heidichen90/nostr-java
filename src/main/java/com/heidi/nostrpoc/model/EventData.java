package com.heidi.nostrpoc.model;

import lombok.Builder;

import java.util.Date;

@Builder
public record EventData(
        String context,
        Date createdAt
) {
}
