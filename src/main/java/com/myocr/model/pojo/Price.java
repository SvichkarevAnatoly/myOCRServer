package com.myocr.model.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Price")
public class Price implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @Column(name = "id_city_shop")
    private long idCityShop;

    @Column(name = "id_receipt_item")
    private long idReceiptItem;

    @Column(name = "value", unique = true, updatable = false)
    private String value;

    // TODO: date field

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public Price() {
    }

    public Price(long idCityShop, long idReceiptItem, String value) {
        this.idCityShop = idCityShop;
        this.idReceiptItem = idReceiptItem;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCityShop() {
        return idCityShop;
    }

    public void setIdCityShop(long idCityShop) {
        this.idCityShop = idCityShop;
    }

    public long getIdReceiptItem() {
        return idReceiptItem;
    }

    public void setIdReceiptItem(long idReceiptItem) {
        this.idReceiptItem = idReceiptItem;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", idCityShop=" + idCityShop +
                ", idReceiptItem=" + idReceiptItem +
                ", value='" + value + '\'' +
                '}';
    }
}
