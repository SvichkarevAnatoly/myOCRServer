package com.myocr.entity;

import java.io.Serializable;

// @Embeddable
public class PriceId implements Serializable {
    /*private CityShopId cityShopId;
    private ReceiptItem receiptItem;

    public CityShopId getCityShopId() {
        return cityShopId;
    }

    public void setCityShopId(CityShopId cityShopId) {
        this.cityShopId = cityShopId;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public ReceiptItem getReceiptItem() {
        return receiptItem;
    }

    public void setReceiptItem(ReceiptItem receiptItem) {
        this.receiptItem = receiptItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceId priceId = (PriceId) o;

        if (cityShopId != null ? !cityShopId.equals(priceId.cityShopId) : priceId.cityShopId != null) return false;
        return receiptItem != null ? receiptItem.equals(priceId.receiptItem) : priceId.receiptItem == null;
    }

    @Override
    public int hashCode() {
        int result = cityShopId != null ? cityShopId.hashCode() : 0;
        result = 31 * result + (receiptItem != null ? receiptItem.hashCode() : 0);
        return result;
    }*/
}
