package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.*;

public class DisciplineDao implements DAO<Discipline>{

    private SessionFactory sf;

    @Override
    public void setSessionFactory(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Discipline findById(long id) {
        return sf.openSession().get(Discipline.class, id);
    }

    @Override
    public List<Discipline> findAll() {
        List<Discipline> disciplines = (List<Discipline>) sf.openSession().createQuery("From Discipline").list();
        return disciplines;
    }

    @Override
    public void save(Discipline discipline) {
        Session session = sf.openSession();
        Transaction t = session.beginTransaction();
        session.save(discipline);
        t.commit();
        session.close();
    }

    @Override
    public void update(Discipline discipline) {
        Session session = sf.openSession();
        Transaction t = session.beginTransaction();
        session.update(discipline);
        t.commit();
        session.close();
    }

    @Override
    public void delete(Discipline discipline) {
        Session session = sf.openSession();
        Transaction t = session.beginTransaction();
        session.delete(discipline);
        t.commit();
        session.close();
    }
}
