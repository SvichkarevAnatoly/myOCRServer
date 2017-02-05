package com.myocr.db.DAO;

import com.myocr.db.ProductDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ProductDAO {
    private Session session;

    public ProductDAO(Session session) {
        this.session = session;
    }

    public long insertProductDataSet(ProductDataSet productDataSet) throws HibernateException {
        return (Long) session.save(productDataSet);
    }

    public List<String> getAllProductDataSets() throws HibernateException {
        Criteria criteria = session.createCriteria(ProductDataSet.class);
        criteria.setProjection(Projections.property("name"));
        return (List<String>) criteria.list();
    }
}
