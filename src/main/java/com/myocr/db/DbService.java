package com.myocr.db;

import com.myocr.db.dao.ProductDAO;
import com.myocr.db.dao.ShopDAO;
import com.myocr.model.pojo.ProductDataSet;
import com.myocr.model.pojo.Shop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DbService {
    private final SessionFactory sessionFactory;

    public DbService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long insertProductDataSet(List<ProductDataSet> productDataSetList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ProductDAO dao = new ProductDAO(session);
        long lastId = -1;
        for (ProductDataSet productDataSet : productDataSetList) {
            lastId = dao.insertProductDataSet(productDataSet);
        }
        transaction.commit();
        session.close();
        return lastId;
    }

    public List<String> getAllProducts() {
        Session session = sessionFactory.openSession();
        ProductDAO dao = new ProductDAO(session);
        final List<String> products = dao.getAllProductDataSets();
        session.close();
        return products;
    }

    public List<Shop> getShopsByCity(long id) {
        Session session = sessionFactory.openSession();
        final ShopDAO dao = new ShopDAO(session);
        final List<Shop> shops = dao.getByCity(id);
        session.close();
        return shops;
    }
}
