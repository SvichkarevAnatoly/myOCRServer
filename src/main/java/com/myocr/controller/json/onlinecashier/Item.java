package com.myocr.controller.json.onlinecashier;

public class Item {

    private Long price;
    private Long sum;
    private Long quantity;
    private String name;
    private String barcode;
    private Long nds10;
    private Long nds18;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getNds10() {
        return nds10;
    }

    public void setNds10(Long nds10) {
        this.nds10 = nds10;
    }

    public Long getNds18() {
        return nds18;
    }

    public void setNds18(Long nds18) {
        this.nds18 = nds18;
    }

}
