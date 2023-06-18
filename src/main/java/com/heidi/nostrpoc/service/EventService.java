package com.heidi.nostrpoc.service;

import com.heidi.nostrpoc.model.EventData;

import java.util.List;

public interface EventService {

    void asyncInsertEvent(String payload);

    List<EventData> getEvents();

}
