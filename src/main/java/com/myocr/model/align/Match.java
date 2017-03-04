package com.myocr.model.align;

public class Match {
    private String match;
    private int score;

    public Match() {
    }

    public Match(String match, int score) {
        this.match = match;
        this.score = score;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
