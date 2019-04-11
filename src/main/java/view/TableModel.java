package view;

import model.IModel;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class TableModel extends DefaultTableModel {
    private final ArrayList<IModel> models;

    public TableModel(ArrayList<IModel> models) {
        this.models = models;

        String[] columnsNames = new String[0];
        ArrayList<Object[]> rows = new ArrayList<>();

        for (IModel model : this.models) {
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

        this.setDataVector(rows.toArray(new Object[0][]), columnsNames);
    }

    public IModel getModel(int index) {
        return this.models.get(index);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
