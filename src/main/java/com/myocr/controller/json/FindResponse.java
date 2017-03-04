package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class FindResponse {
    private List<Match> matches = new ArrayList<>();

    public FindResponse() {
    }

    public FindResponse(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
