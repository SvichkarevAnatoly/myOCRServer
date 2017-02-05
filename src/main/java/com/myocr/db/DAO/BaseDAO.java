package com.myocr.db.DAO;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public abstract class BaseDAO<T> {

    protected Session session;

    public BaseDAO(Session session) {
        this.session = session;
    }

    public abstract Class getModelClass();

    public long insert(T t) {
        return (long) session.save(t);
    }

    public void insert(List<T> tList) {
        for (T t : tList) {
            session.save(t);
        }
    }

    public T getById(long id) {
        return (T) session.get(getModelClass(), id);
    }

    public List<T> getAll() throws HibernateException {
        Criteria criteria = session.createCriteria(getModelClass());
        return (List<T>) criteria.list();
    }
}
