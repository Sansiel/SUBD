package dao;

import model.*;
import java.util.*;

public class MedicineDao implements DAO<Medicine>{
    private List<Medicine> medicines = new ArrayList<>();

    public MedicineDao() { }

    @Override
    public Optional<Medicine> get(long id) {
        return Optional.ofNullable(medicines.get((int) id));
    }

    @Override
    public List<Medicine> getAll() {
        return medicines;
    }

    @Override
    public void save(Medicine medicine) {
        medicines.add(medicine);
    }

    @Override
    public void update(Medicine medicine, String[] params) {
        medicine.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        medicines.add(medicine);
    }

    @Override
    public void delete(Medicine medicine) {
        medicines.remove(medicine);
    }
}