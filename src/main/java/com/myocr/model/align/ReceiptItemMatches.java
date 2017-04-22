package com.myocr.model.align;

import java.util.ArrayList;
import java.util.List;

public class ReceiptItemMatches {
    private String source;
    private List<Match> matches = new ArrayList<>();

    public ReceiptItemMatches(String source, List<Match> matches) {
        this.source = source;
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public String getSource() {
        return source;
    }
}
