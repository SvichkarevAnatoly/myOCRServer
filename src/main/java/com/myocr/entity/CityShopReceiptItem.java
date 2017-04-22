package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "city_shop_receipt_item")
public class CityShopReceiptItem {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "receipt_item_id")
    private ReceiptItem receiptItem;

    @ManyToOne
    @JoinColumn(name = "city_shop_id")
    private CityShop cityShop;

    public CityShopReceiptItem() {
    }

    public CityShopReceiptItem(ReceiptItem receiptItem, CityShop cityShop) {
        this.receiptItem = receiptItem;
        this.cityShop = cityShop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReceiptItem getReceiptItem() {
        return receiptItem;
    }

    public void setReceiptItem(ReceiptItem receiptItem) {
        this.receiptItem = receiptItem;
    }

    public CityShop getCityShop() {
        return cityShop;
    }

    public void setCityShop(CityShop cityShop) {
        this.cityShop = cityShop;
    }
}
