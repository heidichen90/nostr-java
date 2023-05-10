package com.heidi.nostrpoc.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heidi.nostrpoc.constant.EventType;
import com.heidi.nostrpoc.constant.IEvent;
import com.heidi.nostrpoc.constant.NostrEvent;
import com.heidi.nostrpoc.util.NostrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@Slf4j
public class NostrWebSocketHandler extends TextWebSocketHandler {

    private static final String PUBLIC_KEY = "e0131db0689078f518710ad970a2b37f7e1af28769238a447cf95455df72eb4a";
    private static final String PRIVATE_KEY = "d95b1aff5480ce7733915feca9180192c6b198d4737ab9cfee12b9ba47e085ae";

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Transport error: " + exception.getMessage());
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Connection established");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket session closed with status: " + status);
    }

    public void sendNostrEvent(WebSocketSession session) throws JsonProcessingException {
        NostrEvent event = new NostrEvent();
        event.setKind(1);
        event.setPubkey(PUBLIC_KEY);
        event.setCreatedAt(LocalDateTime.now().toEpochSecond(UTC));
        event.setTags(List.of("test"));
        event.setContent("Hello from Heidi");
        event.setId(event.generateId());
        event.setSig(PUBLIC_KEY + PRIVATE_KEY);

        List<IEvent> list = new ArrayList<>();
        list.add(EventType.EVENT);
        list.add(event);
        try {
            String payload = NostrUtils.serializeEvent(list);
            log.info("nostr client send payload: {}", payload);
            session.sendMessage(new TextMessage(payload));
        } catch (Exception e) {
            log.info("Error sending Nostr event: " + e.getMessage());
        }
    }


}
