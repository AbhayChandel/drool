package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeMapper {

    @StringToLocalDateTimeMapping
    public LocalDateTime stringToLocalDateTimeMapping(String dateTime) {
        return (dateTime != null && !dateTime.equalsIgnoreCase("")) ? MetaFieldValueFormatter.toLocalDateTime(dateTime) : null;
    }
}
