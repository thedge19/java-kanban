package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {

    public String timeToString(LocalDateTime time) {
        if (time == null) {
            return "null";
        }
        return time.toString();
    }

    public LocalDateTime localDateTimeFromConsole(String date, String time) {
        String[] dateArr = date.split("\\.");
        String[] timeArr = time.split("\\.");

        int day = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int year = Integer.parseInt(dateArr[2]);

        int hour = Integer.parseInt(timeArr[0]);
        int minute = Integer.parseInt(timeArr[1]);

        return LocalDateTime.of(year, month, day, hour, minute);
    }
}
