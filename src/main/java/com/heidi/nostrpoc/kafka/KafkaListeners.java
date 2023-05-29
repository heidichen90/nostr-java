package com.heidi.nostrpoc.kafka;

import com.heidi.nostrpoc.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {

    private final EventService eventService;

    @KafkaListener(topics = "nostr-topic", groupId = "groupId")
    void listener(String data){
        eventService.asyncInsertEvent(data);
        log.info("--------Listener Received: " + data);
    }
}
