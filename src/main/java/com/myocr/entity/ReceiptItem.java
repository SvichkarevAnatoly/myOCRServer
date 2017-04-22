package com.myocr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receipt_item")
public class ReceiptItem {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "receiptItem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CityShopReceiptItem> cityShopReceiptItems = new ArrayList<>();

    public ReceiptItem() {
    } // jpa only

    public ReceiptItem(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityShopReceiptItem> getCityShopReceiptItems() {
        return cityShopReceiptItems;
    }

    public void setCityShopReceiptItems(List<CityShopReceiptItem> cityShopReceiptItems) {
        this.cityShopReceiptItems = cityShopReceiptItems;
    }

    @Override
    public String toString() {
        return "ReceiptItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}