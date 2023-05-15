package com.heidi.nostrpoc.dao;

import java.util.concurrent.CompletableFuture;

public interface EventDao {
    CompletableFuture<Integer> asyncInsertEvent(String payload);
}
