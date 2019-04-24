package service;

import java.util.List;

import dao.CountryDao;
import model.Country;

public class CountryService {

    private static CountryDao countryDao;

    public CountryService() {
        countryDao = new CountryDao();
    }

    public void persist(Country entity) {
        countryDao.openCurrentSessionwithTransaction();
        countryDao.persist(entity);
        countryDao.closeCurrentSessionwithTransaction();
    }

    public void update(Country entity) {
        countryDao.openCurrentSessionwithTransaction();
        countryDao.update(entity);
        countryDao.closeCurrentSessionwithTransaction();
    }

    public Country findById(String id) {
        countryDao.openCurrentSession();
        Country country = countryDao.findById(id);
        countryDao.closeCurrentSession();
        return country;
    }

    public void delete(String id) {
        countryDao.openCurrentSessionwithTransaction();
        Country country = countryDao.findById(id);
        countryDao.delete(country);
        countryDao.closeCurrentSessionwithTransaction();
    }

    public List<Country> findAll() {
        countryDao.openCurrentSession();
        List<Country> countries = countryDao.findAll();
        countryDao.closeCurrentSession();
        return countries;
    }

    public void deleteAll() {
        countryDao.openCurrentSessionwithTransaction();
        countryDao.deleteAll();
        countryDao.closeCurrentSessionwithTransaction();
    }

    public CountryDao countryDao() {
        return countryDao;
    }
}