package com.heidi.nostrpoc.service.impl;

import com.heidi.nostrpoc.dao.EventDao;
import com.heidi.nostrpoc.model.EventData;
import com.heidi.nostrpoc.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;
    @Override
    public void asyncInsertEvent(String payload) {
        eventDao.asyncInsertEvent(payload);
    }

    @Override
    public List<EventData> getEvents() {
        return eventDao.getEvents();
    }
}
