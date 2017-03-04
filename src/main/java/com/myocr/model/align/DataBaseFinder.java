package com.myocr.model.align;

import java.util.ArrayList;
import java.util.List;

public class DataBaseFinder {
    private final List<String> receiptItems;

    public DataBaseFinder(List<String> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public String find(String ocrReceiptItem) {
        final Aligner aligner = new SimpleAligner();
        return find(ocrReceiptItem, aligner);
    }

    public String find(String ocrReceiptItem, Aligner aligner) {
        int bestScore = Integer.MIN_VALUE;
        String bestScoreProduct = "";
        for (String product : receiptItems) {
            int alignScore = aligner.align(ocrReceiptItem, product);
            if (alignScore > bestScore) {
                bestScore = alignScore;
                bestScoreProduct = product;
            }
        }

        return bestScoreProduct;
    }

    public List<String> findAll(List<String> ocrReceiptItems) {
        final ArrayList<String> bestScoreProducts = new ArrayList<>(ocrReceiptItems.size());
        for (String ocrProduct : ocrReceiptItems) {
            final String bestScoreProduct = find(ocrProduct);
            bestScoreProducts.add(bestScoreProduct);
        }
        return bestScoreProducts;
    }
}
