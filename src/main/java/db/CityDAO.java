package db;

import org.hibernate.Session;

public class CityDAO extends BaseDAO<CityDAO> {

    public CityDAO(Session session) {
        super(session);
    }

    @Override
    public Class getModelClass() {
        return City.class;
    }


}
