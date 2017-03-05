package com.myocr.controller.json;

public class PriceDateReceiptItemResponse {
    private String item;
    private int price;
    private String date;

    public PriceDateReceiptItemResponse(String item, int price, String date) {
        this.item = item;
        this.price = price;
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
