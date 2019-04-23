package dao;

import model.*;
import java.util.*;

public class CountryDao implements DAO<Country>{
    private List<Country> countries = new ArrayList<>();

    public CountryDao() { }

    @Override
    public Optional<Country> get(long id) {
        return Optional.ofNullable(countries.get((int) id));
    }

    @Override
    public List<Country> getAll() {
        return countries;
    }

    @Override
    public void save(Country country) {
        countries.add(country);
    }

    @Override
    public void update(Country country, String[] params) {
        country.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        countries.add(country);
    }

    @Override
    public void delete(Country country) {
        countries.remove(country);
    }
}
