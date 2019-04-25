package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class SportsmanDao implements DAO<Sportsman>{

    private Session s;

    @Override
    public void setSession(Session s) {
        this.s = s;
    }

    @Override
    public Sportsman findById(long id) {
        return s.get(Sportsman.class, id);
    }

    @Override
    public List<Sportsman> findAll() {
        List<Sportsman> sportsmans = (List<Sportsman>) s.createQuery("From Sportsman").list();
        return sportsmans;
    }

    @Override
    public void save(Sportsman sportsman) {
        Transaction t = s.beginTransaction();
        s.save(sportsman);
        t.commit();
    }

    @Override
    public void update(Sportsman sportsman) {
        Transaction t = s.beginTransaction();
        s.update(sportsman);
        t.commit();
    }

    @Override
    public void delete(Sportsman sportsman) {
        Transaction t = s.beginTransaction();
        s.delete(sportsman);
        t.commit();
    }
}
