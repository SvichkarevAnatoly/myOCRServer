package com.myocr.controller.json;

import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.model.ocr.ParsedPrice;

import java.util.List;

public class OcrReceiptResponse {
    private List<ReceiptItemMatches> itemMatches;
    private List<ParsedPrice> prices;

    public OcrReceiptResponse(List<ReceiptItemMatches> itemMatches, List<ParsedPrice> prices) {
        this.itemMatches = itemMatches;
        this.prices = prices;
    }

    public List<ReceiptItemMatches> getItemMatches() {
        return itemMatches;
    }

    public List<ParsedPrice> getPrices() {
        return prices;
    }
}
