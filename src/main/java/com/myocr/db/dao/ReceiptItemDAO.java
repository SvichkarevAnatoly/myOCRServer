package com.myocr.db.dao;

import com.myocr.model.pojo.ReceiptItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ReceiptItemDAO extends BaseDAO<ReceiptItemDAO> {

    public ReceiptItemDAO(Session session) {
        super(session);
    }

    @Override
    public Class getModelClass() {
        return ReceiptItem.class;
    }

    public List<String> getReceiptItems(List<Long> receiptItems) {
        Criteria criteria = session.createCriteria(getModelClass());
        criteria.add(Restrictions.in("id", receiptItems));
        criteria.setProjection(Projections.property("name"));

        return (List<String>) criteria.list();
    }
}
