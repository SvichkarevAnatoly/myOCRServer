package com.myocr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    public static Date parse(String time) throws ParseException {
        return DATE_FORMAT.parse(time);
    }
}
