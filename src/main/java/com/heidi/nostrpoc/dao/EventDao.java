package com.heidi.nostrpoc.dao;

import com.heidi.nostrpoc.model.EventData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EventDao {
    CompletableFuture<Integer> asyncInsertEvent(String payload);

    List<EventData> getEvents();
}
