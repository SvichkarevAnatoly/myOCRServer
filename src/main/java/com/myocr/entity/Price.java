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

    private String value;
    private Date time;

    @ManyToOne
    @JoinColumn(name = "city_shop_receipt_item_id")
    private CityShopReceiptItem cityShopReceiptItem;

    public Price() {
    }

    public Price(String value, Date time, CityShopReceiptItem cityShopReceiptItem) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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