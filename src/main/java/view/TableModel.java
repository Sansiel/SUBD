package view;

import model.IModel;
import model.Sportsman;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class TableModel<T> extends DefaultTableModel {
    private ArrayList<T> models;
    private Class<T> type;

    public TableModel(ArrayList<T> models) {
        this.models = models;
        this.type = (Class<T>) models.get(0).getClass();

        String[] columnsNames = new String[0];
        ArrayList<Object[]> rows = new ArrayList<>();

        for (T model : this.models) {
            columnsNames = model.getColumnsNames();

            Object[] columns = model.getColumns();
            Class[] columnsTypes = model.getColumnsTypes();
            for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
                if (Collection.class.isAssignableFrom(columnsTypes[columnIndex])) {
                    columns[columnIndex] = ((Collection<?>) columns[columnIndex]).stream().map(Object::toString).collect(Collectors.joining(", "));
                } else if (Boolean.class.isAssignableFrom(columnsTypes[columnIndex])) {
                    columns[columnIndex] = ((Boolean) columns[columnIndex]) ? "Да" : "Нет";
                }
            }
            rows.add(columns);
        }

        switch (type.getName()) {
            case "model.Sportsman":
                this.setDataVector(rows.toArray(new Object[0][]), new Object[]{"id", "fname"});
                break;
            case "model.Discipline":
                break;
            case "model.Result":
                break;
            case "model.Medicine":
                break;
            case "model.Country":
                break;
        }


    }

    public IModel getModel(int index) {
        return this.models.get(index);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
