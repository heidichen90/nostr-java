package com.heidi.nostrpoc.service.impl;

import com.heidi.nostrpoc.dao.EventDao;
import com.heidi.nostrpoc.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;
    @Override
    public void asyncInsertEvent(String payload) {
        eventDao.asyncInsertEvent(payload);
    }
}
