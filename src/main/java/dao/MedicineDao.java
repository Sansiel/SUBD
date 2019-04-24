package dao;

import model.*;
import java.util.*;

public class MedicineDao {
    private List<Medicine> medicines = new ArrayList<>();

    public MedicineDao() { }

    public Optional<Medicine> get(long id) {
        return Optional.ofNullable(medicines.get((int) id));
    }

    public List<Medicine> getAll() {
        return medicines;
    }

    public void save(Medicine medicine) {
        medicines.add(medicine);
    }

    public void update(Medicine medicine, String[] params) {
        medicine.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        medicines.add(medicine);
    }

    public void delete(Medicine medicine) {
        medicines.remove(medicine);
    }
}