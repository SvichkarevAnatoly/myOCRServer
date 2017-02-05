package com.myocr.db.dao;

import com.myocr.model.pojo.Price;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PriceDAO extends BaseDAO<PriceDAO> {

    public PriceDAO(Session session) {
        super(session);
    }

    @Override
    public Class getModelClass() {
        return Price.class;
    }

    public List<Long> getReceiptItems(List<Long> cityShopIds) {
        Criteria criteria = session.createCriteria(getModelClass());
        criteria.add(Restrictions.in("idCityShop", cityShopIds));
        criteria.setProjection(Projections.property("idReceiptItem"));

        return (List<Long>) criteria.list();
    }
}
