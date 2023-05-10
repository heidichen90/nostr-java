package com.heidi.nostrpoc.constant;

import lombok.Data;

import java.util.List;

@Data
public class NostrId {
    private int id ;
    private String pubkey;
    private Long createdAt;
    private int kind;
    private List<String> tags;
    private String content;
}
