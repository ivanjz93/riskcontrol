package org.dan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
    public static String getDateTime() {
        return df.format(new Date());
    }
    public static String getDate() {return df2.format(new Date());}
}
