package com.myocr.model.ocr;

public class ParsedPrice {
    private String stringValue;
    private Integer intValue;

    public ParsedPrice(String stringValue) {
        this.stringValue = stringValue;
    }

    public ParsedPrice(String stringValue, int intValue) {
        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Integer getIntValue() {
        return intValue;
    }
}
