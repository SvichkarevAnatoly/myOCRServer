package com.myocr.controller.json;

import com.myocr.model.align.ReceiptItemMatches;

import java.util.List;

public class OcrReceiptResponse {
    private List<ReceiptItemMatches> itemMatches;
    private List<String> prices;

    public OcrReceiptResponse(List<ReceiptItemMatches> itemMatches, List<String> prices) {
        this.itemMatches = itemMatches;
        this.prices = prices;
    }

    public List<ReceiptItemMatches> getItemMatches() {
        return itemMatches;
    }

    public List<String> getPrices() {
        return prices;
    }
}
