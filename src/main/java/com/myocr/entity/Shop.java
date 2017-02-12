package com.myocr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @JsonIgnore
    @ManyToOne
    private City city;

    public Shop(String name, City city) {
        this.name = name;
        this.city = city;
    }

    Shop() {
    } // jpa only

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }
}
