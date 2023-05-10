package com.heidi.nostrpoc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heidi.nostrpoc.client.NostrWebSocketClient;
import com.heidi.nostrpoc.client.NostrWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * I think NostContoller will make you confusion, so I rename it to RESTful Controller.
 * In this controller, I expected it should hande the HTTP Request not webSocket Request.
 * <p>
 * The process flow Just like this
 * <p>
 * client -> NostrController -> websocket -> WebsocketHandler
 * <p>
 * so this class we dont need the NostrWebSocketHandler,
 * we just need injection the NostrWebSocketClient help me send the websocket message.
 */
@RestController
@Slf4j
public class NostrController {

//    private NostrWebSocketHandler handler = new NostrWebSocketHandler();

//    private NostrWebSocketClient client = new NostrWebSocketClient(handler);

    // todo - I will fix the WebSocketClient before.
    @GetMapping("/nostr/hello")
    public String sayHello() throws JsonProcessingException {
//        client.createSession();
//        handler.sendNostrEvent(client.getSession());
        return "Hello Nostr!";
    }
}