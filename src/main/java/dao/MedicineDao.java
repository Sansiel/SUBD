package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class MedicineDao implements DAO<Medicine>{

    private Session s;

    @Override
    public void setSession(Session s) {
        this.s = s;
    }

    @Override
    public Medicine findById(long id) {
        return s.get(Medicine.class, id);
    }

    @Override
    public List<Medicine> findAll() {
        List<Medicine> medicines = (List<Medicine>) s.createQuery("From Medicine").list();
        return medicines;
    }

    @Override
    public void save(Medicine medicine) {
        Transaction t = s.beginTransaction();
        s.save(medicine);
        t.commit();
    }

    @Override
    public void update(Medicine medicine) {
        Transaction t = s.beginTransaction();
        s.update(medicine);
        t.commit();
    }

    @Override
    public void delete(Medicine medicine) {
        Transaction t = s.beginTransaction();
        s.delete(medicine);
        t.commit();
    }
}