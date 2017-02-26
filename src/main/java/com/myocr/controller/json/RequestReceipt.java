package com.myocr.controller.json;

import java.util.List;

public class RequestReceipt {
    private String cityName;
    private String shopName;
    private List<String> items;

    public RequestReceipt() {
    }

    public RequestReceipt(String cityName, String shopName, List<String> items) {
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
