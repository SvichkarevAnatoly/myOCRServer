package com.myocr.util;

import org.apache.commons.lang3.StringUtils;

public class TextFieldUtil {

    private static final int MAX_LENGTH = 100;

    /**
     * Replace all whitespaces to one space delimiter
     *
     * @param value original receipt item
     * @return prepared to save to db receipt item
     */
    public static String prepare(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Null receipt item trying to save");
        }
        return value.trim()
                .replaceAll("\\s", " ")
                .replaceAll(" +", " ");
    }

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        // contains only one line
        if (value.contains("\n")) {
            return false;
        }
        if (value.length() >= MAX_LENGTH) {
            return false;
        }
        if (StringUtils.isEmpty(prepare(value))) {
            return false;
        }

        return true;
    }
}
