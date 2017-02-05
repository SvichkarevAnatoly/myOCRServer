package com.myocr.db;

import com.myocr.db.dao.ProductDAO;
import com.myocr.db.dao.ShopDAO;
import com.myocr.model.pojo.City;
import com.myocr.model.pojo.CityShop;
import com.myocr.model.pojo.ProductDataSet;
import com.myocr.model.pojo.Shop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DbService {
    private static final String amazon_url = "myocrdb.ckt8l133kbsa.us-west-2.rds.amazonaws.com";
    private static final String localhost_url = "localhost";

    private static final String host_url = localhost_url;

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    private static final String hibernate_username = "ocruser";
    private static final String hibernate_password = "passpass";

    private final SessionFactory sessionFactory;

    public DbService() {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
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

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ProductDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://" + host_url + "/ocr");
        configuration.setProperty("hibernate.connection.username", hibernate_username);
        configuration.setProperty("hibernate.connection.password", hibernate_password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        // for utf-8
        configuration.setProperty("hibernate.connection.CharSet", "utf8");
        configuration.setProperty("hibernate.connection.characterEncoding", "utf8");
        configuration.setProperty("hibernate.connection.useUnicode", "true");

        configuration.addAnnotatedClass(Shop.class);
        configuration.addAnnotatedClass(City.class);
        configuration.addAnnotatedClass(CityShop.class);

        return configuration;
    }
}
