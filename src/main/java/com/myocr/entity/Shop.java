package com.myocr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "shop_city",
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
    private Set<City> cities;

    public Shop(String name, Set<City> cities) {
        this.name = name;
        this.cities = cities;
    }

    public Shop(String name) {
        this.name = name;
    }

    Shop() {
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

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
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
