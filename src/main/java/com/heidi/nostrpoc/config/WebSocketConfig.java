package com.heidi.nostrpoc.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heidi.nostrpoc.client.AggWebSocketClient;
import com.heidi.nostrpoc.client.NostrWebSocketHandler;
import com.heidi.nostrpoc.client.SimpleWebSocketClient;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
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
@Log4j2
public class WebSocketConfig implements WebSocketConfigurer {

  private final NostrWebSocketHandler myNostrWebSocketHandler;
  private final KafkaTemplate kafkaTemplate;

  @Bean
  public WebSocketContainer webSocketContainer() {
    return ContainerProvider.getWebSocketContainer();
  }

  // this is websocket server side register into the spring boot context.
  // 這樣註冊, 別人就可以呼叫 ws://localhost:8081/myHandler
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(myNostrWebSocketHandler, "/myHandler").setAllowedOriginPatterns("*");
  }


//   we will register an websocket client as spring bean.
  @Bean
  public SimpleWebSocketClient simpleWebSocketClient() {
    //do not estabilish connection when application set up. It will be estabilished when needed. otherwise it will cause conflict.
    final SimpleWebSocketClient client = new SimpleWebSocketClient();
    return client;
  }

  @Bean
  public AggWebSocketClient aggWebSocketClient() throws JsonProcessingException {
    //do not estabilish connection when application set up. It will be estabilished when needed. otherwise it will cause conflict.
    final AggWebSocketClient aggWebSocketClient = new AggWebSocketClient(webSocketContainer(), kafkaTemplate);
    aggWebSocketClient.connect("wss://relay.nekolicio.us");

//    List<Object> list = new ArrayList<>();
//    list.add(ClientEventType.REQ);
//    list.add("12j312n31knkajsndaksndas");
//    Request request = Request.builder().limit(5).build();
//    list.add(request);
//    aggWebSocketClient.syncSendMessage(NostrUtils.serializeEvent(list));
    return aggWebSocketClient;
  }

}
