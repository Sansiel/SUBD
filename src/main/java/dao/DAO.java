package dao;

import org.hibernate.Session;

import java.util.List;

public interface DAO<T> {

    void setSession(Session s);

    T findById(long id);

    List<T> findAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}