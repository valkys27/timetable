package com.unicorn.edu.timetable.utils;

import com.unicorn.edu.timetable.repository.entity.Subject;

import java.util.*;

public class StringUtils {

    public static void appendTimeTableItem(StringBuilder sb, List<Subject> subjects, Subject.TimeTableItem item) {
        Iterator<Subject> it = subjects.iterator();
        boolean next = it.hasNext();
        Subject subject = null;
        for (int i = 8; i < 12 || next; i++) {
            if (next) {
                subject = it.next();
            }
            if (subject != null && subject.getTime().getHour() == i) {
                sb.append(alignCenter(subject.getTimeTableItem(item)));
                next = it.hasNext();
            }
            else {
                sb.append("\t\t\t\t\t");
                next = false;
            }
        }
    }

    private static String alignCenter(String val) {
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
