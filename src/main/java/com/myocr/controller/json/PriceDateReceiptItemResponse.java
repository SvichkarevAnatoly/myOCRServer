package com.myocr.controller.json;

public class PriceDateReceiptItemResponse {
    private String item;
    private String price;
    private String date;

    public PriceDateReceiptItemResponse(String item, String price, String date) {
        this.item = item;
        this.price = price;
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
