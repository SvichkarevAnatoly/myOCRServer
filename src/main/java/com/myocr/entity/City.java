package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToMany(mappedBy = "city")
    private List<CityShop> cityShops = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    City() {
    } // jpa only

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CityShop> getCityShops() {
        return cityShops;
    }

    public void setCityShops(List<CityShop> cityShops) {
        this.cityShops = cityShops;
    }

    /*public City addShop(Shop shop) {
        shops.add(shop);
        if (!shop.contains(this)) {
            shop.addCity(this);
        }
        return this;
    }

    public boolean contains(Shop shop) {
        return shops.contains(shop);
    }

    public List<Shop> getShops() {
        return shops;
    }*/

    /*@Override
    public String toString() {
        String result = String.format(
                "City [id=%d, name='%s']%n",
                id, name);
        if (shops != null) {
            for (Shop shop : shops) {
                result += String.format(
                        "Shop[id=%d, name='%s']%n",
                        shop.getId(), shop.getName());
            }
        }

        return result;
    }*/
}
