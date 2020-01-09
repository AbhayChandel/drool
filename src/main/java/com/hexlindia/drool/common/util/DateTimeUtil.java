package com.hexlindia.drool.common.util;

import java.time.LocalDateTime;

public class DateTimeUtil {

    private DateTimeUtil() {
        throw new IllegalStateException();
    }

    public static LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }
}
