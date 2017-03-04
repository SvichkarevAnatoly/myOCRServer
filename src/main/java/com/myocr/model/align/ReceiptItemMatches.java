package com.myocr.model.align;

import java.util.ArrayList;
import java.util.List;

public class ReceiptItemMatches {
    private List<Match> matches = new ArrayList<>();

    public ReceiptItemMatches() {
    }

    public ReceiptItemMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
