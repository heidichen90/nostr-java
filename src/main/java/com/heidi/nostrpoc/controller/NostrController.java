package com.heidi.nostrpoc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heidi.nostrpoc.client.NostrWebSocketClient;
import com.heidi.nostrpoc.client.NostrWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NostrController {

    private NostrWebSocketHandler handler = new NostrWebSocketHandler();
    private NostrWebSocketClient client = new NostrWebSocketClient(handler);

    @GetMapping("/nostr/hello")
    public String sayHello() throws JsonProcessingException {
        client.createSession();
        handler.sendNostrEvent(client.getSession());
        return "Hello Nostr!";
    }
}
