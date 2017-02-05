package com.myocr.db.DAO;

import com.myocr.db.Shop;
import org.hibernate.Session;

import java.util.List;

public class ShopDAO extends BaseDAO<ShopDAO> {

    public ShopDAO(Session session) {
        super(session);
    }

    @Override
    public Class getModelClass() {
        return Shop.class;
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
