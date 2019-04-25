package service;

import java.util.List;

import dao.CountryDao;
import model.Country;

public class CountryService {

    private static CountryDao countryDao;

    public CountryService() {
        countryDao = new CountryDao();
    }


    public CountryDao countryDao() {
        return countryDao;
    }
}