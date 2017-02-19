package com.myocr.entity;

/*@Entity
@AssociationOverrides({
        @AssociationOverride(name = "priceId.cityShopId.city",
                joinColumns = @JoinColumn(name = "city_id")),
        @AssociationOverride(name = "priceId.cityShopId.shop",
                joinColumns = @JoinColumn(name = "shop_id")),
        @AssociationOverride(name = "priceId.receiptItem",
                joinColumns = @JoinColumn(name = "receipt_item_id"))})*/
public class Price {
    /*@EmbeddedId
    private PriceId priceId;

    public Price() {
    }

    public PriceId getPriceId() {
        return priceId;
    }

    public void setPriceId(PriceId priceId) {
        this.priceId = priceId;
    }*/
}
