package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class PriceBodyRequest {
    private String cityName;
    private String shopName;

    private List<ReceiptPriceItem> items = new ArrayList<>();

    public PriceBodyRequest() {
    }

    public PriceBodyRequest(String cityName, String shopName) {
        this.cityName = cityName;
        this.shopName = shopName;
    }

    public PriceBodyRequest(String cityName, String shopName, List<ReceiptPriceItem> items) {
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

    public List<ReceiptPriceItem> getItems() {
        return items;
    }

    public void setItems(List<ReceiptPriceItem> items) {
        this.items = items;
    }

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
}
