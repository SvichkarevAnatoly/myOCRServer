package com.myocr.model.pojo;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "City")
public class City implements Serializable {
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
    public City() {
    }

    public City(String name) {
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
