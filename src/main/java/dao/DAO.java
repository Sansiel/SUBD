package dao;

import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    void setSessionFactory(SessionFactory sf);

    T findById(long id);

    List<T> findAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}