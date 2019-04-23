package dao;

import model.*;
import java.util.*;

public class ResultDao implements DAO<Result>{
    private List<Result> results = new ArrayList<>();

    public ResultDao() { }

    @Override
    public Optional<Result> get(long id) {
        return Optional.ofNullable(results.get((int) id));
    }

    @Override
    public List<Result> getAll() {
        return results;
    }

    @Override
    public void save(Result result) {
        results.add(result);
    }

    @Override
    public void update(Result result, String[] params) {
        result.setPlace(Integer.parseInt(Objects.requireNonNull(
                params[0], "Place cannot be null")));
        result.setYear(Integer.parseInt(Objects.requireNonNull(
                params[1], "Year cannot be null")));
        result.setRecord(Integer.parseInt(Objects.requireNonNull(
                params[2], "Record cannot be null")));
        results.add(result);
    }

    @Override
    public void delete(Result result) {
        results.remove(result);
    }
}
