package com.myocr.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "cityShopId.city",
                joinColumns = @JoinColumn(name = "city_id")),
        @AssociationOverride(name = "cityShopId.shop",
                joinColumns = @JoinColumn(name = "shop_id"))})
public class CityShop {
    private CityShopId cityShopId = new CityShopId();

    CityShop() {
    } // jpa only

    public CityShop(City city, Shop shop) {
        setCity(city);
        setShop(shop);
    }

    public static CityShop link(City city, Shop shop) {
        final CityShop cityShop = new CityShop(city, shop);
        city.addCityShop(cityShop);
        shop.addCityShop(cityShop);
        return cityShop;
    }

    @EmbeddedId
    public CityShopId getCityShopId() {
        return cityShopId;
    }

    public void setCityShopId(CityShopId cityShopId) {
        this.cityShopId = cityShopId;
    }

    @Transient
    public City getCity() {
        return cityShopId.getCity();
    }

    public void setCity(City city) {
        cityShopId.setCity(city);
    }

    @Transient
    public Shop getShop() {
        return cityShopId.getShop();
    }

    public void setShop(Shop shop) {
        cityShopId.setShop(shop);
    }
}
