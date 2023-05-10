package com.heidi.nostrpoc.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;

@Slf4j
public class NostrWebSocketClient {
    private static final String NOSTR_RELAY_URL = "wss://relay.nekolicio.us/";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient client;
    private WebSocketSession session;
    private TextWebSocketHandler handler;
    private WebSocketHttpHeaders headers;
    public NostrWebSocketClient(TextWebSocketHandler handler) {
        this.client = new StandardWebSocketClient();
        this.headers = new WebSocketHttpHeaders();
        this.handler = handler;
    }

    public WebSocketSession getSession() {
        return session;
    }


//    public void sendNostrEvent(WebSocketSession session) throws JsonProcessingException {
//        // Add logic to send a Nostr event according to the protocol
//        // For example, you can send a Nostr event to the server:
//        // NostrEvent event = new NostrEvent("test", "test", "test", "test", "test", "test");
//        // String serializedEvent = NostrUtils.serializeEvent(event);
////        // session.sendMessage(new TextMessage(serializedEvent));
////
//        NostrEvent event = new NostrEvent();
//        event.setKind(1);
//        event.setPubkey(PUBLIC_KEY);
//        event.setCreatedAt(LocalDateTime.now().toEpochSecond(UTC));
//        event.setTags(List.of("test"));
//        event.setContent("Hello from Heidi");
//        event.setId(event.generateId());
//        event.setSig(null);
//
//        List<Object> list = new ArrayList<>();
//        list.add("EVENT");
//        list.add(event);
//
//        try {
//            String payload = NostrUtils.serializeEvent(list);
//            log.info("nostr client send payload: {}", payload);
//            session.sendMessage(new TextMessage(payload));
//        } catch (Exception e) {
//            log.info("Error sending Nostr event: " + e.getMessage());
//        }
//    }

    public void createSession() {
        try {
            log.info("Starting Nostr WebSocket client");
            this.session = client.doHandshake(handler, headers, URI.create(NOSTR_RELAY_URL)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    @Override
//    public void run(String... args) throws Exception {
//        log.info("Starting Nostr WebSocket client");
//        WebSocketClient client  = new StandardWebSocketClient();
//        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
//        TextWebSocketHandler handler = new NostrWebSocketHandler();
//        URI uri = URI.create(NOSTR_RELAY_URL);
//        try{
//
//            WebSocketSession session = client.doHandshake(handler, headers, uri).get();
//            sendNostrEvent(session);
////            session.sendMessage(new TextMessage("Hello Nostr!"));
////            session.sendMessage(new TextMessage("Hello Nostr!"));
////            log.info("Message sent");
////            session.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}
