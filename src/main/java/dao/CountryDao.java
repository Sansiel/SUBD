package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Country;

public class CountryDao implements DaoInterface<Country, String> {

    private Session currentSession;

    private Transaction currentTransaction;

    public CountryDao() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(Country entity) {
        getCurrentSession().save(entity);
    }

    public void update(Country entity) {
        getCurrentSession().update(entity);
    }

    public Country findById(String id) {
        Country country = (Country) getCurrentSession().get(Country.class, id);
        return country;
    }

    public void delete(Country entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Country> findAll() {
        List<Country> countries = (List<Country>) getCurrentSession().createQuery("from Country").list();
        return countries;
    }

    public void deleteAll() {
        List<Country> entityList = findAll();
        for (Country entity : entityList) {
            delete(entity);
        }
    }
}