package com.heidi.nostrpoc.client;

import jakarta.websocket.*;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;

/**
 * Follow this sample
 * https://github.com/rxcats/spring-boot-demo/blob/master/src/main/java/com/example/demo/ws/WebSocketClient.java
 */
@Slf4j
@ClientEndpoint
public class SimpleWebSocketClient {

  private WebSocketContainer container;
  private Session userSession;

  private Object lock;

  private String response;

  public SimpleWebSocketClient() {
    container = ContainerProvider.getWebSocketContainer();
  }

  @OnOpen
  public void onOpen(Session session) {
    log.info("ws-client socket on open, session id: {}", session.getId());
  }

  @OnClose
  public void onClose(Session session, CloseReason closeReason) {
    log.info("ws-client socket on close, session id: {}", session.getId());
  }

  @OnMessage
  public void onMessage(Session session, String msg) {
    try {
      response = msg;

      // i not really understand the lock just follow the example
      synchronized (lock) {
        lock.notify();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void connect(String serverUri) {
    try {
      userSession = container.connectToServer(this, new URI(serverUri));
      userSession.setMaxTextMessageBufferSize(Integer.MAX_VALUE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String syncSendMessage(String msg) {
    try {
      long startTime = System.currentTimeMillis();
      lock = new Object();
      userSession.getBasicRemote().sendText(msg);
      synchronized (lock) {
        lock.wait();
      }

      log.info("estimatedTime: {}", System.currentTimeMillis() - startTime);

      return response;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public void disconnect() {
    try {
      if (userSession != null && userSession.isOpen()) {
        userSession.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}