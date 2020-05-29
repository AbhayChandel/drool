package com.hexlindia.drool.common.dto.mapper;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatsFieldMapper {

    @StatNumberToCompactString
    public String toCompactString(int number) {
        return MetaFieldValueFormatter.getCompactFormat(number);
    }
}
