package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class OcrLinesResponse {
    private List<String> ocrLines = new ArrayList<>();

    public OcrLinesResponse() {
    }

    public OcrLinesResponse(List<String> ocrLines) {
        this.ocrLines = ocrLines;
    }

    public List<String> getOcrLines() {
        return ocrLines;
    }

    public void setOcrLines(List<String> ocrLines) {
        this.ocrLines = ocrLines;
    }
}
