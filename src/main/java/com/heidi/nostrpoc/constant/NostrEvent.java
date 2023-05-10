package com.heidi.nostrpoc.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.heidi.nostrpoc.util.NostrUtils;
import lombok.Data;

import java.util.List;
@Data
public class NostrEvent implements IEvent{
    private String id;
    private String pubkey;
    @JsonProperty("created_at")
    private Long createdAt;
    private Integer kind;
    private List<String> tags;
    private String content;
    private String sig;

    public String generateId() throws JsonProcessingException{
        NostrId nostrId = new NostrId();
        nostrId.setPubkey(this.pubkey);
        nostrId.setId(0);
        nostrId.setCreatedAt(this.createdAt);
        nostrId.setKind(this.kind);
        nostrId.setTags(this.tags);
        nostrId.setContent(this.content);
        //convert current object to string
        return NostrUtils.serializeHex256(nostrId);
    }


}
