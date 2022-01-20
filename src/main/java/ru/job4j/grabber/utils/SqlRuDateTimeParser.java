package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
     private final String TODAY = "сегодня";
     private final String YESTERDAY = "вчера";
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
             Map.entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String parse) {
        LocalDate today = LocalDate.now();
        String[] data = parse.split(", ");
        String[] date = data[0].replace(",", "").split(" ");
        String[] time = data[1].split(":");
        LocalDateTime localDateTime;
        switch (date[0]) {
            case TODAY : localDateTime = LocalDateTime.of(
                    today,
                    LocalTime.of(
                            Integer.parseInt(time[0]),
                            Integer.parseInt(time[1])
                    )
            );
            break;
            case YESTERDAY : localDateTime = LocalDateTime.of(
                    LocalDate.now().getYear(),
                    today.minusDays(1).getMonth(),
                    LocalDate.now().getDayOfMonth() - 1,
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1])
            );
            break;
            default : localDateTime = LocalDateTime.of(
                    Integer.parseInt(date[2]),
                    Integer.parseInt(MONTHS.get(date[1])),
                    Integer.parseInt(date[0]),
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1])
            );
            break;
        };
        return localDateTime;
    }
}
