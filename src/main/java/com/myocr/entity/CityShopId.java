package com.myocr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CityShopId implements Serializable {
    private City city;
    private Shop shop;

    @ManyToOne(cascade = CascadeType.ALL)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityShopId that = (CityShopId) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return shop != null ? shop.equals(that.shop) : that.shop == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (shop != null ? shop.hashCode() : 0);
        return result;
    }
}
