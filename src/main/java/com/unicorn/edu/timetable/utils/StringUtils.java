package com.unicorn.edu.timetable.utils;

public class StringUtils {

    public static String alignCenter(String val) {
        int length = val.length();
        int formatLength = 5;
        for (int i = 1; i <= length; i++) {
            if (i == length)
                break;
            if (i % 2 == 0)
                formatLength++;
        }
        return String.format("\t\t%" + formatLength + "s\t\t", val);
    }
}
