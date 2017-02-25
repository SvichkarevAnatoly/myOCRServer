package com.myocr.controller.json;

public class RequestPriceBody {
    private String cityName;
    private String shopName;

    private String receiptItemName;
    private String priceValue;

    public RequestPriceBody() {
    }

    public RequestPriceBody(String cityName, String shopName,
                            String receiptItemName, String priceValue) {
        this.cityName = cityName;
        this.shopName = shopName;
        this.receiptItemName = receiptItemName;
        this.priceValue = priceValue;
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

    public String getReceiptItemName() {
        return receiptItemName;
    }

    public void setReceiptItemName(String receiptItemName) {
        this.receiptItemName = receiptItemName;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }
}
