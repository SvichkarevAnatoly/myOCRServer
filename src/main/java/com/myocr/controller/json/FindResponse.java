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

    public static class Match {
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
}
