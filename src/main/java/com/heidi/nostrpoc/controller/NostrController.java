package com.heidi.nostrpoc.controller;

import static java.time.ZoneOffset.UTC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidi.nostrpoc.client.SimpleWebSocketClient;
import com.heidi.nostrpoc.constant.client.ClientEventType;
import com.heidi.nostrpoc.constant.client.NostrEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.heidi.nostrpoc.util.NostrUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@EnableAsync
@RestController
@Slf4j
@RequiredArgsConstructor
public class NostrController {

    private static final String PUBLIC_KEY = "e0131db0689078f518710ad970a2b37f7e1af28769238a447cf95455df72eb4a";

//    private NostrWebSocketHandler handler = new NostrWebSocketHandler();

//    private NostrWebSocketClient client = new NostrWebSocketClient(handler);

    private final SimpleWebSocketClient simpleWebSocketClient;

    private final ObjectMapper objectMapper;

    @GetMapping("/nostr/connect")
    public String connect() {
        simpleWebSocketClient.connect("ws://localhost:8081/myHandler");
        return "connect success";
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @GetMapping("/nostr/hello")
    public String sayHello() throws JsonProcessingException {

        NostrEvent event = NostrEvent.builder()
            .kind(1)
            .pubkey(PUBLIC_KEY)
            .createdAt(LocalDateTime.now().toEpochSecond(UTC))
            .tags(List.of("test"))
            .content("Hello from Heidi")
            .id(null)
            .sig(null)
            .build();

        List<Object> list = new ArrayList<>();
        list.add(ClientEventType.EVENT);
        list.add(event);
        simpleWebSocketClient.syncSendMessage(NostrUtils.serializeEvent(list));
        return "send Nostr event success";
    }
}