package com.myocr.model.ocr;

public class ParsedPrice {
    private String stringValue;
    private int intValue;
    private boolean parsed;

    public ParsedPrice(String stringValue) {
        this.stringValue = stringValue;
    }

    public ParsedPrice(String stringValue, int intValue) {
        this.stringValue = stringValue;
        this.intValue = intValue;
        parsed = true;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public boolean isParsed() {
        return parsed;
    }
}
