package view;

import model.IModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.*;

public class EntityDialog extends JDialog {
    private boolean accepted;
    private IModel model;
    private Object[] objects;

    public EntityDialog(Frame parent, IModel model, Session session) {
        super(parent, model.getModelName(), true);

        this.accepted = false;
        this.model = model;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new LineBorder(Color.GRAY));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        String[] columnsNames = this.model.getColumnsNames();
        this.objects = this.model.getColumns();
        Class[] columnsTypes = this.model.getColumnsTypes();
        Class[] subColumnsTypes = this.model.getSubColumnsTypes();
        Class[] subColumnsTargetTypes = this.model.getSubColumnsTargetTypes();

        for (int columnIndex = 0; columnIndex < this.objects.length; columnIndex++) {
            JLabel label = new JLabel(columnsNames[columnIndex]);
            constraints.gridx = 0;
            constraints.gridy = columnIndex;
            constraints.gridwidth = 1;
            panel.add(label, constraints);

            int finalColumnIndex = columnIndex;
            if (IModel.class.isAssignableFrom(columnsTypes[columnIndex])) {
                Vector<IModel> models = new Vector<>();
                models.add(null);
                final Query query = session.createQuery(String.format("FROM %s ORDER BY id", columnsTypes[columnIndex].getName()));
                for (Object object : query.list()) {
                    models.add((IModel) object);
                }

                JComboBox<IModel> comboBox = new JComboBox<>(models);
                if (this.objects[columnIndex] != null) {
                    comboBox.setSelectedItem(this.objects[columnIndex]);
                }
                comboBox.addActionListener(event -> this.objects[finalColumnIndex] = comboBox.getSelectedItem());
                constraints.gridx = 1;
                constraints.gridy = columnIndex;
                constraints.gridwidth = 2;
                panel.add(comboBox, constraints);
            } else if (Collection.class.isAssignableFrom(columnsTypes[columnIndex])) {
                JPanel listPanel = new JPanel();
                listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

                JList list = new JList(new Vector<>(((Collection<Object>) this.objects[columnIndex])));
                list.setLayoutOrientation(JList.VERTICAL);
                listPanel.add(new JScrollPane(list));

                JPanel buttonsListPanel = new JPanel();
                buttonsListPanel.setLayout(new BoxLayout(buttonsListPanel, BoxLayout.X_AXIS));

                JButton addButton = new JButton("Добавить");
                addButton.setAlignmentY(Component.CENTER_ALIGNMENT);
                addButton.addActionListener(event -> {
                    IModel subModel = null;
                    try {
                        subModel = (IModel) subColumnsTypes[finalColumnIndex].newInstance();
                    } catch (Exception ex) {
                        return;
                    }

                    SubEntityDialog subEntityDialog = new SubEntityDialog(parent, subModel, session);
                    subEntityDialog.setVisible(true);
                    if (subEntityDialog.isAccepted()) {
                        subModel = subEntityDialog.getModel();

                        IModel targetSubModel = null;
                        try {
                            targetSubModel = (IModel) subColumnsTargetTypes[finalColumnIndex].newInstance();
                        } catch (Exception ex) {
                            return;
                        }

                        targetSubModel.setColumns(new Object[]{ model, subModel, false });

                        ((Collection<Object>) this.objects[finalColumnIndex]).add(targetSubModel);
                        list.setListData(new Vector<>(((Collection<Object>) this.objects[finalColumnIndex])));
                    }
                });
                buttonsListPanel.add(addButton);

                JButton deleteButton = new JButton("Удалить");
                deleteButton.setAlignmentY(Component.CENTER_ALIGNMENT);
                deleteButton.addActionListener(event -> {
                    Object object = list.getSelectedValue();
                    if (object != null) {
                        ((Collection<Object>) this.objects[finalColumnIndex]).remove(object);
                        list.setListData(new Vector<>(((Collection<Object>) this.objects[finalColumnIndex])));
                    }
                });
                buttonsListPanel.add(deleteButton);

                listPanel.add(buttonsListPanel);

                constraints.gridx = 1;
                constraints.gridy = columnIndex;
                constraints.gridwidth = 5;
                panel.add(listPanel, constraints);
            } else if (Boolean.class.isAssignableFrom(columnsTypes[columnIndex])) {
                JCheckBox checkBox = new JCheckBox();
                if (this.objects[columnIndex] != null) {
                    checkBox.setSelected((Boolean) this.objects[columnIndex]);
                }
                checkBox.addActionListener(event -> this.objects[finalColumnIndex] = ((AbstractButton) event.getSource()).getModel().isSelected());
                constraints.gridx = 1;
                constraints.gridy = columnIndex;
                constraints.gridwidth = 2;
                panel.add(checkBox, constraints);
            } else {
                JTextField textField = new JTextField(30);
                if (this.objects[columnIndex] != null) {
                    textField.setText(String.valueOf(this.objects[columnIndex]));
                }
                textField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent documentEvent) {
                        update();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent documentEvent) {
                        update();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent documentEvent) {
                        update();
                    }

                    private void update() {
                        objects[finalColumnIndex] = textField.getText();
                    }
                });
                constraints.gridx = 1;
                constraints.gridy = columnIndex;
                constraints.gridwidth = 2;
                panel.add(textField, constraints);
            }
        }

        JPanel btnPanel = new JPanel();

        JButton btnLogin = new JButton("Ок");
        btnLogin.addActionListener(event -> {
            for (int objectIndex = 0; objectIndex < this.objects.length; objectIndex++) {
                if (this.objects[objectIndex] == null) {
                    JOptionPane.showMessageDialog(this, "Не все поля заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Integer.class.isAssignableFrom(columnsTypes[objectIndex])) {
                    try {
                        Integer.parseInt(this.objects[objectIndex].toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Введено не число", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            this.accepted = true;
            this.model.setColumns(this.objects);
            dispose();
        });
        btnPanel.add(btnLogin);

        JButton btnCancel = new JButton("Отмена");
        btnCancel.addActionListener(event -> dispose());
        btnPanel.add(btnCancel);

        this.getContentPane().add(panel);
        this.getContentPane().add(btnPanel, BorderLayout.PAGE_END);

        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(parent);
    }

    public boolean isAccepted() {
        return accepted;
    }

    public IModel getModel() {
        return model;
    }
}