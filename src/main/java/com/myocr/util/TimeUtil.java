package com.myocr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.US);
    private final static SimpleDateFormat FILE_PREFIX_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss_", Locale.US);

    public static Date parse(String time) throws ParseException {
        return DATE_FORMAT.parse(time);
    }

    public static String parse(Date time) {
        return DATE_FORMAT.format(time);
    }

    public static String getFileTimePrefix(Date fileTime) {
        return FILE_PREFIX_DATE_FORMAT.format(fileTime);
    }
}
