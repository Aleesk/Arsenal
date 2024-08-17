package dev.aleesk.arsenal.utilities;


import org.apache.commons.lang.time.DurationFormatUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class JavaUtil {
    private JavaUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Integer tryParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static Double tryParseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static String formatDurationInt(int input) {
        return DurationFormatUtils.formatDurationWords(input * 1000L, true, true);
    }

    public static String formatDurationLong(long input) {
        return DurationFormatUtils.formatDurationWords(input, true, true);
    }

    public static String formatLongMin(long time) {
        long totalSecs = time / 1000L;
        return String.format("%02d:%02d", new Object[] { Long.valueOf(totalSecs / 60L), Long.valueOf(totalSecs % 60L) });
    }

    public static String formatLongHour(long time) {
        long totalSecs = time / 1000L;
        long seconds = totalSecs % 60L;
        long minutes = totalSecs % 3600L / 60L;
        long hours = totalSecs / 3600L;
        return String.format("%02d:%02d:%02d", new Object[] {hours, minutes, seconds});
    }

    public static long formatLong(String input) {
        if (input == null || input.isEmpty())
            return -1L;
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convertLong(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static long convertLong(int value, char unit) {
        switch (unit) {
            case 'y':
                return value * TimeUnit.DAYS.toMillis(365L);
            case 'M':
                return value * TimeUnit.DAYS.toMillis(30L);
            case 'd':
                return value * TimeUnit.DAYS.toMillis(1L);
            case 'h':
                return value * TimeUnit.HOURS.toMillis(1L);
            case 'm':
                return value * TimeUnit.MINUTES.toMillis(1L);
            case 's':
                return value * TimeUnit.SECONDS.toMillis(1L);
        }
        return -1L;
    }

    public static int formatInt(String input) {
        if (input == null || input.isEmpty())
            return -1;
        int result = 0;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convertInt(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static int convertInt(int value, char unit) {
        switch (unit) {
            case 'd':
                return value * 60 * 60 * 24;
            case 'h':
                return value * 60 * 60;
            case 'm':
                return value * 60;
            case 's':
                return value;
        }
        return -1;
    }

    public static int randomExcluding(int start, int end, int... exclude) {
        int random = start + ThreadLocalRandom.current().nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex)
                break;
            random++;
        }
        return random;
    }

    public static boolean getChance(double minimalChance) {
        return (ThreadLocalRandom.current().nextDouble(99.0D) + 1.0D >= 100.0D - minimalChance);
    }
}
