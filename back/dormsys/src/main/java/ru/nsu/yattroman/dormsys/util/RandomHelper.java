package ru.nsu.yattroman.dormsys.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RandomHelper {

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end-start));
    }

    public static long createRandomLongBetween(long start, long end) {
        return start + (long) Math.round(Math.random() * (end-start));
    }

    public static Date createRandomDate(int startYear, int endYear) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        var localDate = LocalDate.of(year, month, day);
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

}
