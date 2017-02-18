package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "city_shop")
public class CityShop implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Id
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

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
