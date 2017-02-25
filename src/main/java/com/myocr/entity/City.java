package com.myocr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @JsonIgnore
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

    public void addCityShop(CityShop cityShop) {
        cityShops.add(cityShop);
    }

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
