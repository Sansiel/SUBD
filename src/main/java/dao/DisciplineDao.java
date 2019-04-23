package dao;

import model.*;
import java.util.*;

public class DisciplineDao implements DAO<Discipline>{
    private List<Discipline> disciplines = new ArrayList<>();

    public DisciplineDao() { }

    @Override
    public Optional<Discipline> get(long id) {
        return Optional.ofNullable(disciplines.get((int) id));
    }

    @Override
    public List<Discipline> getAll() {
        return disciplines;
    }

    @Override
    public void save(Discipline discipline) {
        disciplines.add(discipline);
    }

    @Override
    public void update(Discipline discipline, String[] params) {
        discipline.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        disciplines.add(discipline);
    }

    @Override
    public void delete(Discipline discipline) {
        disciplines.remove(discipline);
    }
}
