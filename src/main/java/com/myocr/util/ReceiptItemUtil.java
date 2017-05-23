package com.myocr.util;

public class ReceiptItemUtil {
    /**
     * Replace all whitespaces to one space delimiter
     *
     * @param receiptItem original receipt item
     * @return prepared to save to db receipt item
     */
    public static String prepareToSave(String receiptItem) {
        return receiptItem.trim()
                .replaceAll("\\s", " ")
                .replaceAll(" +", " ");
    }
}
