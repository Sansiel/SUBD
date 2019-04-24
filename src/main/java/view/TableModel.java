package view;

import model.*;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableModel<T> extends DefaultTableModel {
    private List<T> entities;
    private Class<T> type;

    public TableModel(List<T> entities) {
        this.entities = entities;
        this.type = (Class<T>) entities.get(0).getClass();

        String[] columnsNames = new String[0];
        ArrayList<Object[]> rows = new ArrayList<>();

        switch (type.getName()) {
            case "model.Sportsman":
                rows = (ArrayList<Object[]>) ((ArrayList<Sportsman>) this.entities).stream()
                        .map(e -> new Object[]{
                                "" + e.getId(),
                                e.getFname(),
                                e.getLname(),
                                e.getMname(),
                                "" + e.getAge(),
                                "" + e.getWeight(),
                                "" + e.getResult().getPlace(),
                                e.getCountry().getName(),
                                e.getMedicine().getName()
                        })
                        .collect(Collectors.toList());
                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "fname", "lname", "mname", "age", "weight", "ResultPlace", "CountryName", "MedicineName"});
                break;
            case "model.Discipline":
                rows = (ArrayList<Object[]>) ((ArrayList<Discipline>) this.entities).stream()
                        .map(e -> new Object[]{
                                "" + e.getId(),
                                e.getName()
                        })
                        .collect(Collectors.toList());
                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "name"});
                break;
            case "model.Result":
                rows = (ArrayList<Object[]>) ((ArrayList<Result>) this.entities).stream()
                        .map(e -> new Object[]{
                                "" + e.getId(),
                                "" + e.getPlace(),
                                "" + e.getYear(),
                                "" + e.getRecord(),
                                e.getDiscipline().getName()
                        })
                        .collect(Collectors.toList());
                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "place", "year", "record", "DisciplineName"});
                break;
            case "model.Medicine":
                rows = (ArrayList<Object[]>) ((ArrayList<Medicine>) this.entities).stream()
                        .map(e -> new Object[]{
                                "" + e.getId(),
                                e.getName()
                        })
                        .collect(Collectors.toList());
                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "name"});
                break;
            case "model.Country":
                rows = (ArrayList<Object[]>) ((ArrayList<Country>) this.entities).stream()
                        .map(e -> new Object[]{
                                "" + e.getId(),
                                e.getName()
                        })
                        .collect(Collectors.toList());
                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "name"});
                break;
        }

//        for (T model : this.entities) {
//            columnsNames = model.getColumnsNames();
//
//            Object[] columns = model.getColumns();
//            Class[] columnsTypes = model.getColumnsTypes();
//            for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
//                if (Collection.class.isAssignableFrom(columnsTypes[columnIndex])) {
//                    columns[columnIndex] = ((Collection<?>) columns[columnIndex]).stream().map(Object::toString).collect(Collectors.joining(", "));
//                } else if (Boolean.class.isAssignableFrom(columnsTypes[columnIndex])) {
//                    columns[columnIndex] = ((Boolean) columns[columnIndex]) ? "Да" : "Нет";
//                }
//            }
//            rows.add(columns);
//        }

//        switch (type.getName()) {
//            case "model.Sportsman":
//                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "fname", "lname", "mname", "age", "weight", "ResultPlace", "CountryName", "MedicineName"});
//                break;
//            case "model.Discipline":
//                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "name"});
//                break;
//            case "model.Result":
//                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"place", "year", "record", "DisciplineName"});
//                break;
//            case "model.Medicine":
//                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "name"});
//                break;
//            case "model.Country":
//                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "name"});
//                break;
//        }


    }

    public T getEntity(int index) {
        return this.entities.get(index);
    }

//    public IModel getEntity(int index) {
//        return new IModel() {
//            @Override
//            public String getModelName() {
//                return null;
//            }
//
//            @Override
//            public String getModelPluralName() {
//                return null;
//            }
//
//            @Override
//            public String[] getColumnsNames() {
//                return new String[0];
//            }
//
//            @Override
//            public Object[] getColumns() {
//                return new Object[0];
//            }
//
//            @Override
//            public Class[] getColumnsTypes() {
//                return new Class[0];
//            }
//
//            @Override
//            public Class[] getSubColumnsTypes() {
//                return new Class[0];
//            }
//
//            @Override
//            public Class[] getSubColumnsTargetTypes() {
//                return new Class[0];
//            }
//
//            @Override
//            public void setColumns(Object[] objects) {
//
//            }
//
//            @Override
//            public void setId(String id) {
//
//            }
//
//            @Override
//            public void setParentId(String id) {
//
//            }
//
//            @Override
//            public void setDeleted(boolean deleted) {
//
//            }
//        };
//    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
