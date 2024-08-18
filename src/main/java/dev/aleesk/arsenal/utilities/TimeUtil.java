package dev.aleesk.arsenal.utilities;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getTimeFormatted(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        StringBuilder formattedTime = new StringBuilder();
        if (days > 0) {
            formattedTime.append(days).append("d ");
        }
        if (hours > 0) {
            formattedTime.append(hours).append("h ");
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append("m ");
        }
        if (seconds > 0) {
            formattedTime.append(seconds).append("s");
        }
        return formattedTime.toString().trim();
    }
    public static String getTimeFormatted(int durationInSeconds) {
        int days = durationInSeconds / (24 * 3600);
        durationInSeconds %= (24 * 3600);
        int hours = durationInSeconds / 3600;
        durationInSeconds %= 3600;
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;

        StringBuilder formattedTime = new StringBuilder();
        if (days > 0) {
            formattedTime.append(days).append("d ");
        }
        if (hours > 0) {
            formattedTime.append(hours).append("h ");
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append("m ");
        }
        if (seconds > 0) {
            formattedTime.append(seconds).append("s");
        }
        return formattedTime.toString().trim();
    }
}
