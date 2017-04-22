package com.myocr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class City {
    @Id
    @GeneratedValue
    private long id;

    private String name;

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
}
