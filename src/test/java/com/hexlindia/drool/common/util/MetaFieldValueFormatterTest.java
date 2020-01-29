package com.hexlindia.drool.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetaFieldValueFormatterTest {

    @Test
    void getCompactForamt_NumberLessThan999() {
        assertEquals("432", MetaFieldValueFormatter.getCompactFormat(432));
    }

    @Test
    void getCompactForamt_NumberEqualTo999() {
        assertEquals("999", MetaFieldValueFormatter.getCompactFormat(999));
    }

    @Test
    void getCompactForamt_ThousandWithoutZeroHundered() {
        assertEquals("8.4k", MetaFieldValueFormatter.getCompactFormat(8456));
    }

    @Test
    void getCompactForamt_ThousandWithZeroHundered() {
        assertEquals("5k", MetaFieldValueFormatter.getCompactFormat(5065));
    }

    @Test
    void getCompactForamt_TensThousandWithoutZeroHundered() {
        assertEquals("42.3k", MetaFieldValueFormatter.getCompactFormat(42345));
    }

    @Test
    void getCompactForamt_HundredThousandWithoutZeroHundered() {
        assertEquals("134.5k", MetaFieldValueFormatter.getCompactFormat(134534));
    }

    @Test
    void getCompactForamt_HundredThousandWithZeroHundered() {
        assertEquals("900k", MetaFieldValueFormatter.getCompactFormat(900099));
    }

    @Test
    void getCompactForamt_MillionWithoutZeroHunderedThousand() {
        assertEquals("1.4M", MetaFieldValueFormatter.getCompactFormat(1456456));
    }

    @Test
    void getCompactForamt_MillionWithZeroHunderedThousand() {
        assertEquals("3M", MetaFieldValueFormatter.getCompactFormat(3086878));
    }

    @Test
    void getDateTimeInDayMonCommaYear_SingleCharDateHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertEquals("5 Feb, 2017 4:14 PM", MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(LocalDateTime.parse("2017-02-05 16:14:32", formatter)));
    }

    @Test
    void getDateTimeInDayMonCommaYear_DoubleCharDateHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertEquals("15 Feb, 2017 11:14 AM", MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(LocalDateTime.parse("2017-02-15 11:14:32", formatter)));
    }

    @Test
    void getDateInDayMonCommaYear_SingleCharDateHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertEquals("5 Feb, 2017", MetaFieldValueFormatter.getDateInDayMonCommaYear(LocalDate.parse("2017-02-05", formatter)));
    }

    @Test
    void getDateInDayMonCommaYear_DoubleCharDateHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertEquals("15 Feb, 2017", MetaFieldValueFormatter.getDateInDayMonCommaYear(LocalDate.parse("2017-02-15", formatter)));
    }
}