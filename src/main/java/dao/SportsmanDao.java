package dao;

import model.*;
import java.util.*;

public class SportsmanDao {
    private List<Sportsman> sportsmans = new ArrayList<>();

    public SportsmanDao() { }

    public Optional<Sportsman> get(long id) {
        return Optional.ofNullable(sportsmans.get((int) id));
    }

    public List<Sportsman> getAll() {
        return sportsmans;
    }

    public void save(Sportsman sportsman) {
        sportsmans.add(sportsman);
    }

    public void update(Sportsman sportsman, String[] params) {
        sportsman.setFname(Objects.requireNonNull(
                params[0], "First Name cannot be null"));
        sportsman.setMname(Objects.requireNonNull(
                params[1], "Mid Name cannot be null"));
        sportsman.setLname(Objects.requireNonNull(
                params[2], "Last Name cannot be null"));
        sportsman.setAge(Integer.parseInt(Objects.requireNonNull(
                params[3], "Age cannot be null")));
        sportsman.setWeight(Integer.parseInt(Objects.requireNonNull(
                params[4], "Weight cannot be null")));
        sportsmans.add(sportsman);
    }

    public void delete(Sportsman sportsman) {
        sportsmans.remove(sportsman);
    }
}
