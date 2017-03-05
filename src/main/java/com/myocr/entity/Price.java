package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Price {
    @Id
    @GeneratedValue
    private long id;

    private int value;
    private Date time;

    @ManyToOne
    @JoinColumn(name = "city_shop_receipt_item_id")
    private CityShopReceiptItem cityShopReceiptItem;

    public Price() {
    }

    public Price(int value, Date time, CityShopReceiptItem cityShopReceiptItem) {
        this.value = value;
        this.time = time;
        this.cityShopReceiptItem = cityShopReceiptItem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public CityShopReceiptItem getCityShopReceiptItem() {
        return cityShopReceiptItem;
    }

    public void setCityShopReceiptItem(CityShopReceiptItem cityShopReceiptItem) {
        this.cityShopReceiptItem = cityShopReceiptItem;
    }
}