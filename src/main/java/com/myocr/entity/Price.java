package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Price {
    @Id
    @GeneratedValue
    private long id;

    private String value;
    // private Date date;

    @ManyToOne
    @JoinColumn(name = "city_shop_id")
    private CityShop cityShop;

    @ManyToOne
    @JoinColumn(name = "receipt_item_id")
    private ReceiptItem receiptItem;

    public Price() {
    }

    public Price(String value, ReceiptItem receiptItem, CityShop cityShop) {
        this.value = value;
        this.receiptItem = receiptItem;
        this.cityShop = cityShop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CityShop getCityShop() {
        return cityShop;
    }

    public void setCityShop(CityShop cityShop) {
        this.cityShop = cityShop;
    }

    public ReceiptItem getReceiptItem() {
        return receiptItem;
    }

    public void setReceiptItem(ReceiptItem receiptItem) {
        this.receiptItem = receiptItem;
    }
}