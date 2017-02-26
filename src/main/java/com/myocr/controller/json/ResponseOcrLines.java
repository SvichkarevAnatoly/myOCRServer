package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class ResponseOcrLines {
    private List<String> ocrLines = new ArrayList<>();

    public ResponseOcrLines() {
    }

    public ResponseOcrLines(List<String> ocrLines) {
        this.ocrLines = ocrLines;
    }

    public List<String> getOcrLines() {
        return ocrLines;
    }

    public void setOcrLines(List<String> ocrLines) {
        this.ocrLines = ocrLines;
    }
}
