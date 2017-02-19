package com.myocr.entity;

import java.util.HashSet;
import java.util.Set;

// @Entity
public class ReceiptItem {
    // @Id
    // @GeneratedValue
    private long id;

    private String name;

    private Set<Price> prices = new HashSet<>();

    public ReceiptItem() {
    } // jpa only

    public ReceiptItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*@OneToMany(mappedBy = "priceId.receiptItem",
            cascade = CascadeType.ALL)
    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }*/
}
