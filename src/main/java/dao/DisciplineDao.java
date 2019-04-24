package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.*;

public class DisciplineDao implements DAO<Discipline>{

    private Session s;

    @Override
    public void setSession(Session s) {
        this.s = s;
    }

    @Override
    public Discipline findById(long id) {
        return s.get(Discipline.class, id);
    }

    @Override
    public List<Discipline> findAll() {
        List<Discipline> disciplines = (List<Discipline>) s.createQuery("From Discipline").list();
        return disciplines;
    }

    @Override
    public void save(Discipline discipline) {
        Transaction t = s.beginTransaction();
        s.save(discipline);
        t.commit();
    }

    @Override
    public void update(Discipline discipline) {
        Transaction t = s.beginTransaction();
        s.update(discipline);
        t.commit();
    }

    @Override
    public void delete(Discipline discipline) {
        Transaction t = s.beginTransaction();
        s.delete(discipline);
        t.commit();
    }
}
