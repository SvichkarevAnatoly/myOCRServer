package com.myocr.db.dao;

import com.myocr.model.pojo.CityShop;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
}
