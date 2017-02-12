package com.myocr.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "city")
    private Set<Shop> shops = new HashSet<>();

    public City(String name) {
        this.name = name;
    }

    City() {
    } // jpa only

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
