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
    @JoinColumn(name = "city_shop_id")
    private CityShop cityShop;

    @ManyToOne
    @JoinColumn(name = "receipt_item_id")
    private ReceiptItem receiptItem;

    public Price() {
    }

    public Price(String value, Date time, ReceiptItem receiptItem, CityShop cityShop) {
        this.value = value;
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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