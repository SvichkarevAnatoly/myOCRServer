package com.myocr.model.pojo;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class ProductDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @SerializedName("name")
    @Column(name = "name", unique = false, updatable = false)
    private String name;

    @SerializedName("price")
    @Column(name = "price", unique = false, updatable = false)
    private String price;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public ProductDataSet() {
    }

    public ProductDataSet(String name, String price) {
        setId(-1);
        setName(name);
        setPrice(price);
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
