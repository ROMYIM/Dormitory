package com.john_yim.dormitory.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MSI-PC on 2018/2/17.
 */

public class DateUtil {
    public static String dateToString(String format, Date date) {
        if (date == null || format == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        }
    }

    public static Date distanceToday(int distance) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - distance);
        return calendar.getTime();
    }
}
