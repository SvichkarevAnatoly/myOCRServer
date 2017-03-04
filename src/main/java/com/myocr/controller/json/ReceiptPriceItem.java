package com.myocr.controller.json;

public class ReceiptPriceItem {
    private String name;
    private String price;

    public ReceiptPriceItem() {
    }

    public ReceiptPriceItem(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
