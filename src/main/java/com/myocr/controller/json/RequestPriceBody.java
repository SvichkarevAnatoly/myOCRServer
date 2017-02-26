package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class RequestPriceBody {
    private String cityName;
    private String shopName;

    private List<RequestReceiptPriceItem> items = new ArrayList<>();

    public RequestPriceBody() {
    }

    public RequestPriceBody(String cityName, String shopName) {
        this.cityName = cityName;
        this.shopName = shopName;
    }

    public RequestPriceBody(String cityName, String shopName, List<RequestReceiptPriceItem> items) {
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

    public List<RequestReceiptPriceItem> getItems() {
        return items;
    }

    public void setItems(List<RequestReceiptPriceItem> items) {
        this.items = items;
    }
}
