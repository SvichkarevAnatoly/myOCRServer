package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class ResponseFindResult {
    private List<ResponseMatch> matches = new ArrayList<>();

    public ResponseFindResult() {
    }

    public ResponseFindResult(List<ResponseMatch> matches) {
        this.matches = matches;
    }

    public List<ResponseMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<ResponseMatch> matches) {
        this.matches = matches;
    }
}
