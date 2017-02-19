package com.myocr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {
    private long id;

    private String name;

    @JsonIgnore
    private Set<CityShop> cityShops = new HashSet<>();

    public City(String name) {
        this.name = name;
    }

    City() {
    } // jpa only

    @Id
    @GeneratedValue
    public Long getId() {
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

    @OneToMany(mappedBy = "cityShopId.city",
            cascade = CascadeType.ALL)
    public Set<CityShop> getCityShops() {
        return cityShops;
    }

    public void setCityShops(Set<CityShop> cityShops) {
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
