package com.myocr.util;

import org.apache.commons.lang3.StringUtils;

public class ReceiptItemUtil {

    private static final int MAX_LENGTH = 100;

    /**
     * Replace all whitespaces to one space delimiter
     *
     * @param receiptItem original receipt item
     * @return prepared to save to db receipt item
     */
    public static String prepareToSave(String receiptItem) {
        if (receiptItem == null) {
            throw new IllegalArgumentException("Null receipt item trying to save");
        }
        return receiptItem.trim()
                .replaceAll("\\s", " ")
                .replaceAll(" +", " ");
    }

    public static boolean isValid(String receiptItem) {
        if (receiptItem == null) {
            return false;
        }
        return !StringUtils.isEmpty(prepareToSave(receiptItem))
                && (receiptItem.length() < MAX_LENGTH);
    }


}
