package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "cities")
    private Set<Shop> shops = new HashSet<>();

    public City(String name, Set<Shop> shops) {
        this.name = name;
        this.shops = shops;
    }

    public City(String name) {
        this.name = name;
    }

    City() {
    } // jpa only

    public long getId() {
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

    public Set<Shop> getShops() {
        return shops;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    @Override
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
    }
}
