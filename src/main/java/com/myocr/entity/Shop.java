package com.myocr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "shop_city",
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false))
    private Set<City> cities;

    public Shop(String name) {
        this.name = name;
        cities = new HashSet<>();
    }

    Shop() {
    } // jpa only

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean contains(City city) {
        return cities.contains(city);
    }

    public Shop addCity(City city) {
        cities.add(city);
        if (!city.contains(this)) {
            city.addShop(this);
        }
        return this;
    }

    @Override
    public String toString() {
        String result = String.format(
                "Shop [id=%d, name='%s']%n",
                id, name);
        if (cities != null) {
            for (City city : cities) {
                result += String.format(
                        "City [id=%d, name='%s']%n",
                        city.getId(), city.getName());
            }
        }

        return result;
    }
}
