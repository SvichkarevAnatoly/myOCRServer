package com.myocr.controller.json;

import java.util.List;

public class ReceiptRequest {
    private String cityName;
    private String shopName;
    private List<String> items;

    public ReceiptRequest() {
    }

    public ReceiptRequest(String cityName, String shopName, List<String> items) {
        this.cityName = cityName;
        this.shopName = shopName;
        this.items = items;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
