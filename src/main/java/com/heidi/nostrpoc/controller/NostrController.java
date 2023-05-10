package com.heidi.nostrpoc.controller;

import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidi.nostrpoc.client.NostrWebSocketClient;
import com.heidi.nostrpoc.client.NostrWebSocketHandler;
import com.heidi.nostrpoc.client.SimpleWebSocketClient;
import com.heidi.nostrpoc.constant.NostrEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequiredArgsConstructor
public class NostrController {

    private static final String PUBLIC_KEY = "e0131db0689078f518710ad970a2b37f7e1af28769238a447cf95455df72eb4a";

//    private NostrWebSocketHandler handler = new NostrWebSocketHandler();

//    private NostrWebSocketClient client = new NostrWebSocketClient(handler);

    private final SimpleWebSocketClient simpleWebSocketClient;

    private final ObjectMapper objectMapper;


    // you can design the API to send Nostr event by
    @GetMapping("/nostr/hello")
    public String sayHello() throws JsonProcessingException {
//        client.createSession();
//        handler.sendNostrEvent(client.getSession());

        // I think Record will be easier
        NostrEvent event = new NostrEvent();
        event.setKind(1);
        event.setPubkey(PUBLIC_KEY);
        event.setCreatedAt(LocalDateTime.now().toEpochSecond(UTC));
        event.setTags(List.of("test"));
        event.setContent("Hello from Heidi");
        event.setId(event.generateId());
        event.setSig(null);

        List<Object> list = new ArrayList<>();
        list.add("EVENT");
        list.add(event);

        final String json = objectMapper.writeValueAsString(list);

        simpleWebSocketClient.syncSendMessage(json);

        // we dont need close, because it will handle another request the send the Nostr event.
        return "Hello Nostr!";
    }
}