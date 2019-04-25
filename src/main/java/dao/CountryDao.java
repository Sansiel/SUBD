package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class CountryDao implements DAO<Country>{

    private Session s;

    @Override
    public void setSession(Session s) {
        this.s = s;
    }

    @Override
    public Country findById(long id) {
        return s.get(Country.class, id);
    }

    @Override
    public List<Country> findAll() {
        List<Country> counties = (List<Country>) s.createQuery("From Country").list();
        return counties;
    }

    @Override
    public void save(Country country) {
        Transaction t = s.beginTransaction();
        s.save(country);
        t.commit();
    }

    @Override
    public void update(Country country) {
        Transaction t = s.beginTransaction();
        s.update(country);
        t.commit();
    }

    @Override
    public void delete(Country country) {
        Transaction t = s.beginTransaction();
        s.delete(country);
        t.commit();
    }
}
