package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DbService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    private static final String hibernate_username = "ocruser";
    private static final String hibernate_password = "pass";

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

    public long addNewProduct(ProductDataSet product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ProductDAO dao = new ProductDAO(session);
        long id = dao.insertProduct(product);
        transaction.commit();
        session.close();
        return id;
    }

    public List<String> getAllProducts() {
        Session session = sessionFactory.openSession();
        ProductDAO dao = new ProductDAO(session);
        final List<String> products = dao.getAllProductDataSets();
        session.close();
        return products;
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ProductDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/ocr");
        configuration.setProperty("hibernate.connection.username", hibernate_username);
        configuration.setProperty("hibernate.connection.password", hibernate_password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }
}
