package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "city_shop")
public class CityShop implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    CityShop() {
    } // jpa only

    public CityShop(City city, Shop shop) {
        this.city = city;
        this.shop = shop;
    }

    public static CityShop link(City city, Shop shop) {
        final CityShop cityShop = new CityShop(city, shop);
        city.addCityShop(cityShop);
        shop.addCityShop(cityShop);
        return cityShop;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
