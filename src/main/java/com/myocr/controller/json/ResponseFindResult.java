package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class ResponseFindResult {
    private int bestScore;
    private List<String> matches = new ArrayList<>();

    public ResponseFindResult() {
    }

    public ResponseFindResult(int bestScore, List<String> matches) {
        this.bestScore = bestScore;
        this.matches = matches;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public List<String> getMatches() {
        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }
}
