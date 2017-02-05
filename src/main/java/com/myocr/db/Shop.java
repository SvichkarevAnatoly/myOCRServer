package com.myocr.db;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Shop")
public class Shop implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @SerializedName("name")
    @Column(name = "name", unique = false, updatable = false)
    private String name;


    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public Shop() {
    }

    public Shop(String name) {
        setId(-1);
        this.name = name;
    }

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
}
