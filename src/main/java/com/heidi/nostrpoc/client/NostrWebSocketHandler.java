package com.heidi.nostrpoc.client;

import com.heidi.nostrpoc.constant.client.ClientEventType;
import com.heidi.nostrpoc.constant.server.ServerEventType;
import com.heidi.nostrpoc.service.EventService;
import com.heidi.nostrpoc.util.NostrUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * this handler is webSocket server side, it just like the mvc controller,
 * so this handler dont need any client's stuff (I comment on below)
 * <p>
 * The {@link TextWebSocketHandler} extends form {@link AbstractWebSocketHandler}, that is mean
 * the Handler can handle text message.
 * Just like a RESTful api can handler request (but the request is socket event and message).
 * <p>
 * if you want to the handler work in spring boot context you can follow this doc:
 * https://docs.spring.io/spring-framework/docs/6.0.8/reference/html/web.html#websocket-server
 */
@Slf4j
@RequiredArgsConstructor
public class NostrWebSocketHandler extends TextWebSocketHandler {
    private final EventService eventService;
    private final static HashMap<String, WebSocketSession> requestedClient = new HashMap<>();

    // because this class is server side, so we dont need this fields.
    // private static final String PUBLIC_KEY = "e0131db0689078f518710ad970a2b37f7e1af28769238a447cf95455df72eb4a";
    //private static final String PRIVATE_KEY = "d95b1aff5480ce7733915feca9180192c6b198d4737ab9cfee12b9ba47e085ae";

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Transport error: " + exception.getMessage());
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Connection established" + session.getRemoteAddress());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message: " + message.getPayload() + session.getRemoteAddress() + session.getHandshakeHeaders());
        List<String> list  = NostrUtils.deserializeEvent(message.getPayload());
        ClientEventType clientEventType = ClientEventType.valueOf(list.get(0));
        //save event
        eventService.asyncInsertEvent(message.getPayload());
        if(clientEventType == ClientEventType.REQ){
            requestedClient.put(list.get(1), session);
            log.info("user subscribe with id: " + list.get(1));
        } else if (clientEventType == ClientEventType.EVENT){
            //validate id and sig
            //broadcast the message to all client
            for (Map.Entry<String, WebSocketSession> client : requestedClient.entrySet()){
                WebSocketSession clientSession = client.getValue();
                if(clientSession != null){
                    clientSession.sendMessage(message);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket session closed with status: " + status);
    }


}