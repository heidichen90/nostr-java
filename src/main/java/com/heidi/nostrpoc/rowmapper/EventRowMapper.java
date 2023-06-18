package com.heidi.nostrpoc.rowmapper;

import com.heidi.nostrpoc.model.EventData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<EventData> {
    @Override
    public EventData mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventData eventData = EventData.builder().context(rs.getString("context")).createdAt(rs.getTimestamp("created_at")).build();
        return eventData;
    }
}
