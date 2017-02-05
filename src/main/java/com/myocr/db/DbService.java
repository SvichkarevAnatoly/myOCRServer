package com.myocr.db;

import com.myocr.db.dao.*;
import com.myocr.model.pojo.CityShop;
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

    public CityShop getCityShop(long cityId, long shopId) {
        Session session = sessionFactory.openSession();
        final CityShopDAO dao = new CityShopDAO(session);
        final CityShop cityShop = dao.getCityShop(cityId, shopId);
        session.close();
        return cityShop;
    }

    public List<String> getReceiptItems(long shopId) {
        Session session = sessionFactory.openSession();
        final CityShopDAO cityShopDAO = new CityShopDAO(session);
        final List<Long> cityShopIds = cityShopDAO.getCityShop(shopId);

        final PriceDAO priceDAO = new PriceDAO(session);
        final List<Long> receiptItemIds = priceDAO.getReceiptItems(cityShopIds);

        final ReceiptItemDAO receiptItemDAO = new ReceiptItemDAO(session);
        final List<String> receiptItems = receiptItemDAO.getReceiptItems(receiptItemIds);
        session.close();
        return receiptItems;
    }
}
