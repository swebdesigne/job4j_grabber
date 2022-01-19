package ru.job4j.grabber.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

public class SqlRuDateTimeParser implements DateTimeParser {
     private static final String FORMAT = "dd MM yy";
     static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
     private static final Map<String, String> MONTHS = Map.ofEntries(
             Map.entry("янв", "01"),
             Map.entry("фев", "02"),
             Map.entry("мар", "03"),
             Map.entry("апр", "04"),
             Map.entry("май", "05"),
             Map.entry("июн", "06"),
             Map.entry("июл", "07"),
             Map.entry("авг", "08"),
             Map.entry("сен", "09"),
             Map.entry("окт", "10"),
             Map.entry("ноя", "11"),
             Map.entry("дек", "12"),
             Map.entry("вчера", new SimpleDateFormat(FORMAT).format(
                     new java.sql.Date(new java.util.Date().getTime() - MILLIS_IN_A_DAY))
             ),
             Map.entry("сегодня", new SimpleDateFormat(FORMAT).format(Calendar.getInstance().getTime()))
    );

    @Override
    public LocalDateTime parse(String parse) {
        String dateInString = parse.replace(",", "");
        Scanner scanner = new Scanner(dateInString).useDelimiter(" ");
        while (scanner.hasNext()) {
            String tmp = scanner.next();
            if (MONTHS.containsKey(tmp)) {
                dateInString = dateInString.replace(tmp, MONTHS.get(tmp));
            }
        }
        String[] data = dateInString.split(" ");
        String[] time = data[3].split(":");
        return LocalDateTime.of(
                Integer.parseInt(data[2]),
                Integer.parseInt(data[1]),
                Integer.parseInt(data[0]),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1])
        );
    }
}
