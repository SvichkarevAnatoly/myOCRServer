package com.myocr.db.dao;

import com.myocr.model.pojo.Price;
import com.myocr.model.pojo.Shop;
import org.hibernate.Session;

import java.util.List;

public class PriceDAO extends BaseDAO<PriceDAO> {

    public PriceDAO(Session session) {
        super(session);
    }

    @Override
    public Class getModelClass() {
        return Price.class;
    }

    public List<Shop> getByCity(long cityId) {
        final List shops = session.createQuery("FROM Shop s " +
                "WHERE s.id IN (SELECT idShop " +
                "FROM CityShop cs " +
                "WHERE idCity = :cityId)")
                .setParameter("cityId", cityId).list();
        return (List<Shop>) shops;
    }
}
