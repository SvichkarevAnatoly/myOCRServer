package com.myocr.controller.json;

import com.myocr.model.align.ReceiptItemMatches;

import java.util.List;

public class OcrReceiptResponse {
    private List<ReceiptItemMatches> receiptItemMatches;
    private List<String> prices;

    public OcrReceiptResponse(List<ReceiptItemMatches> receiptItemMatches, List<String> prices) {
        this.receiptItemMatches = receiptItemMatches;
        this.prices = prices;
    }

    public List<ReceiptItemMatches> getReceiptItemMatches() {
        return receiptItemMatches;
    }

    public List<String> getPrices() {
        return prices;
    }
}
