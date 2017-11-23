package com.wysengine.fishing.export.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by chenzhifu on 2017/8/14.
 */
public class JodaTimeUtil {
    public static final String yyyyMMPattern = "yyyy-MM";
    public static final String yyyyMMDDPattern = "yyyy-MM-dd";
    public static final String yyyyMMDDHHMMSSPattern = "yyyy-MM-dd HH:mm:ss";
    private static final String timeZone = "Asia/Shanghai";

    public static DateTime now() {
        return DateTime.now(DateTimeZone.forID(timeZone));
    }

    public static DateTime parseTime(String dateTime,String pattern) {
        return DateTimeFormat.forPattern(pattern).withZone(DateTimeZone.forID(timeZone)).parseDateTime(dateTime);
    }

    public static DateTime parseTime(long timestamp) {
        return new DateTime(timestamp, DateTimeZone.forID(timeZone));
    }

}
