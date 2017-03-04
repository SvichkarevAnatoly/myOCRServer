package com.myocr.model.align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class DataBaseFinder {
    private final static int MATCH_NUMBER = 3;

    private final List<String> receiptItems;

    public DataBaseFinder(List<String> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public List<Match> find(String ocrReceiptItem) {
        final Aligner aligner = new SimpleAligner();
        return find(ocrReceiptItem, aligner);
    }

    public List<Match> find(String ocrReceiptItem, Aligner aligner) {
        final TreeSet<Match> bestMatches = new TreeSet<>(
                (m1, m2) -> Double.compare(m1.getScore(), m2.getScore() - 0.5d));

        final int min = Math.min(MATCH_NUMBER, receiptItems.size());
        for (int i = 0; i < min; i++) {
            final String receiptItem = receiptItems.get(i);
            int alignScore = aligner.align(ocrReceiptItem, receiptItem);
            bestMatches.add(new Match(receiptItem, alignScore));
        }

        if (receiptItems.size() > MATCH_NUMBER) {
            for (int i = 3; i < receiptItems.size(); i++) {
                final String receiptItem = receiptItems.get(i);
                int alignScore = aligner.align(ocrReceiptItem, receiptItem);

                final Match smallest = bestMatches.first();
                if (smallest.getScore() < alignScore) {
                    bestMatches.pollFirst();
                    bestMatches.add(new Match(receiptItem, alignScore));
                }
            }
        }

        final List<Match> result = new ArrayList<>(bestMatches);
        Collections.reverse(result);
        return result;
    }

    public List<List<Match>> findAll(List<String> ocrReceiptItems) {
        final List<List<Match>> bestScoreProducts = new ArrayList<>(ocrReceiptItems.size());
        for (String ocrProduct : ocrReceiptItems) {
            final List<Match> matches = find(ocrProduct);
            bestScoreProducts.add(matches);
        }
        return bestScoreProducts;
    }
}
