package com.hexlindia.drool.common.util;

import java.sql.Timestamp;

public class DateTimeUtil {

    private DateTimeUtil() {
        throw new IllegalStateException();
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
