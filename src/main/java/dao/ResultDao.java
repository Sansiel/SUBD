package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class ResultDao implements DAO<Result>{

    private Session s;

    @Override
    public void setSession(Session s) {
        this.s = s;
    }

    @Override
    public Result findById(long id) {
        return s.get(Result.class, id);
    }

    @Override
    public List<Result> findAll() {
        List<Result> results = (List<Result>) s.createQuery("From Result").list();
        return results;
    }

    @Override
    public void save(Result result) {
        Transaction t = s.beginTransaction();
        s.save(result);
        t.commit();
    }

    @Override
    public void update(Result result) {
        Transaction t = s.beginTransaction();
        s.update(result);
        t.commit();
    }

    @Override
    public void delete(Result result) {
        Transaction t = s.beginTransaction();
        s.delete(result);
        t.commit();
    }
}
