package com.myocr.db.dao;

import com.myocr.model.pojo.CityShop;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CityShopDAO extends BaseDAO<CityShopDAO> {

    public CityShopDAO(Session session) {
        super(session);
    }

    @Override
    public Class getModelClass() {
        return CityShop.class;
    }

    public CityShop getCityShop(long cityId, long shopId) {
        Criteria criteria = session.createCriteria(getModelClass());
        criteria.add(Restrictions.eq("cityId", cityId));
        criteria.add(Restrictions.eq("shopId", shopId));

        return (CityShop) criteria.uniqueResult();
    }

    public List<Long> getCityShop(long shopId) {
        Criteria criteria = session.createCriteria(getModelClass());
        criteria.add(Restrictions.eq("shopId", shopId));
        criteria.setProjection(Projections.property("id"));

        return (List<Long>) criteria.list();
    }
}
