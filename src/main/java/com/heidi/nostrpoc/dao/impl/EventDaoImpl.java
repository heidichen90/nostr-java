package com.heidi.nostrpoc.dao.impl;

import com.heidi.nostrpoc.dao.EventDao;
import com.heidi.nostrpoc.model.EventData;
import com.heidi.nostrpoc.rowmapper.EventRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class EventDaoImpl implements EventDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Async
    public CompletableFuture<Integer> asyncInsertEvent(String context) {

        String sql = "INSERT INTO event (context, created_at) " +
                "VALUES (:context, :createdAt)";

        Map<String, Object> map = new HashMap<>();

        map.put("context", context);

        Date now = new Date();
        map.put("createdAt", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return CompletableFuture.completedFuture(keyHolder.getKey().intValue());
    }

    @Override
    public List<EventData> getEvents() {
        String sql = "SELECT context, created_at FROM event ORDER BY created_at DESC LIMIT 10 ";
        Map<String, Object> map = new HashMap<>();


        List<EventData> eventList = namedParameterJdbcTemplate.query(sql,map ,new EventRowMapper());

        return eventList;
    }


}
