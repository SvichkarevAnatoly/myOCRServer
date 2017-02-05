package com.myocr.model.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "City_Shop")
public class CityShop implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "id_city")
    private long cityId;

    @Column(name = "id_shop")
    private long shopId;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public CityShop() {
    }
}