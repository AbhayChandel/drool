package com.hexlindia.drool.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetaFieldValueFormatter {

    private MetaFieldValueFormatter() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCompactFormat(Integer number) {
        if (number == null) {
            return "";
        }
        StringBuilder compactNumber = new StringBuilder();
        StringBuilder compactSymbol = new StringBuilder();
        if (number <= 999) {
            compactNumber.append(number);
        } else if (number > 999 && number <= 999999) {
            compactSymbol.append("k");
            compactNumber.append(number / 1000);
            int hundredPlaceNumber = (number % 1000) / 100;
            if (hundredPlaceNumber != 0) {
                compactNumber.append("." + hundredPlaceNumber);
            }
        } else {
            compactSymbol.append("M");
            compactNumber.append(number / 1000000);
            int hunderedThousandNumber = (number % 1000000) / 100000;
            if (hunderedThousandNumber != 0) {
                compactNumber.append("." + hunderedThousandNumber);
            }
        }
        return compactNumber.append(compactSymbol).toString();
    }

    public static String getDateTimeInDayMonCommaYear(LocalDateTime dateTime) {
        String dateFormat = "d MMM, yyyy h:mm a";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return dateTimeFormatter.format(dateTime);


    }

    public static String getDateInDayMonCommaYear(LocalDateTime date) {
        String dateFormat = "d MMM, yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return dateFormatter.format(date);
    }

    public static String getDateInDayMonCommaYear(LocalDate date) {
        String dateFormat = "d MMM, yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return dateFormatter.format(date);
    }
}
