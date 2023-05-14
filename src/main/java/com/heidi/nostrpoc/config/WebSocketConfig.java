package com.heidi.nostrpoc.config;

import com.heidi.nostrpoc.client.NostrWebSocketHandler;
import com.heidi.nostrpoc.client.SimpleWebSocketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * I follow this
 * https://docs.spring.io/spring-framework/docs/6.0.8/reference/html/web.html#websocket-server-handler
 * to register an websocket handler
 */
@EnableWebSocket
@Configuration
@Import(NostrWebSocketHandler.class) // import NostrWebSocketHandler for Configurer.
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private final NostrWebSocketHandler myNostrWebSocketHandler;

  // this is websocket server side register into the spring boot context.
  // 這樣註冊, 別人就可以呼叫 ws://localhost:8080/myHandler
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(myNostrWebSocketHandler, "/myHandler");
  }


  // we will register an websocket client as spring bean.
  @Bean
  public SimpleWebSocketClient simpleWebSocketClient() {
    final SimpleWebSocketClient client = new SimpleWebSocketClient();
    // you can change it to your websocket server (ws://localhost:8080/myHandler)
    client.connect("wss://relay.nekolicio.us/");
    return client;
  }
}