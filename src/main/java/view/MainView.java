package view;

import database.ConnectionInfo;
import model.*;
import org.hibernate.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainView {
    private JFrame frame;
    private JLabel currentEntityLabel;
    private JTable table;
    private JLabel statusLabel;

    private ConnectionInfo connectionInfo;
    private Session session;
    private Class currentEntity;
    private EntityView currentEntityView;

    public MainView() {
        this.connectionInfo = null;
        this.session = null;
        this.currentEntity = null;

        this.initUI();
        Runtime.getRuntime().addShutdownHook(new Thread(this::disconnect));
    }

    private void initUI() {
        this.frame = new JFrame();
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu connectionMenu = new JMenu("Подключениек БД");
        {
            JMenuItem newConnectMenuItem = new JMenuItem("Новое подключение");
            newConnectMenuItem.addActionListener(event -> this.connect());
            connectionMenu.add(newConnectMenuItem);

            JMenuItem disconnectMenuItem = new JMenuItem("Отключится");
            disconnectMenuItem.addActionListener(event -> this.disconnect());
            connectionMenu.add(disconnectMenuItem);

            JMenuItem exitMenuItem = new JMenuItem("Выход");
            exitMenuItem.addActionListener(event -> System.exit(0));
            connectionMenu.add(exitMenuItem);

            menuBar.add(connectionMenu);
        }

        JMenu tablesMenu = new JMenu("Справочники");
        {
            JMenuItem openSportsmansMenuItem = new JMenuItem("Sportsman");
            openSportsmansMenuItem.addActionListener(event -> this.openTable(Sportsman.class));
            tablesMenu.add(openSportsmansMenuItem);

            JMenuItem openResultsMenuItem = new JMenuItem("Result");
            openResultsMenuItem.addActionListener(event -> this.openTable(Result.class));
            tablesMenu.add(openResultsMenuItem);

            JMenuItem openMedicinesMenuItem = new JMenuItem("Medicine");
            openMedicinesMenuItem.addActionListener(event -> this.openTable(Medicine.class));
            tablesMenu.add(openMedicinesMenuItem);

            JMenuItem openCountriesMenuItem = new JMenuItem("Country");
            openCountriesMenuItem.addActionListener(event -> this.openTable(Country.class));
            tablesMenu.add(openCountriesMenuItem);

            JMenuItem openDisciplinesMenuItem = new JMenuItem("Discipline");
            openDisciplinesMenuItem.addActionListener(event -> this.openTable(Discipline.class));
            tablesMenu.add(openDisciplinesMenuItem);

            menuBar.add(tablesMenu);
        }

        this.frame.setJMenuBar(menuBar);

        this.currentEntityLabel = new JLabel();
        this.frame.getContentPane().add(this.currentEntityLabel, BorderLayout.NORTH);

        this.table = new JTable();
        this.table.setDragEnabled(false);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.frame.getContentPane().add(new JScrollPane(this.table));

        this.statusLabel = new JLabel("Отключено");
        this.frame.getContentPane().add(this.statusLabel, BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel();
        {
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

            JButton addButton = new JButton("Добавить запись");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addButton.addActionListener(event -> {
                try {
                    this.addEntity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            buttonsPanel.add(addButton);

            JButton editButton = new JButton("Изменить запись");
            editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            editButton.addActionListener(event -> {
                try {
                    this.editEntity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            buttonsPanel.add(editButton);

            JButton deleteButton = new JButton("Удалить запись");
            deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            deleteButton.addActionListener(event -> this.deleteEntity(this.currentEntity));
            buttonsPanel.add(deleteButton);

            JButton refreshButton = new JButton("Обновить таблицу");
            refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            refreshButton.addActionListener(event -> this.refreshTable());
            buttonsPanel.add(refreshButton);

        }

        this.frame.getContentPane().add(buttonsPanel, BorderLayout.EAST);
    }

    private void refreshTable() {
        if (this.currentEntity != null) this.openTable(this.currentEntity);
    }

    private void connect() {
        ConnectDialog connectDialog = new ConnectDialog(this.frame);
        connectDialog.setVisible(true);
        if (connectDialog.isAccepted()) {
            this.connectionInfo = connectDialog.getConnectionInfo();
            try {
                SessionFactory sessionFactory = this.connectionInfo.getSessionFactory();
                this.session = sessionFactory.openSession();
                this.statusLabel.setText(String.format("Подключено: %s", this.connectionInfo.toString()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.frame, ex, "Ошибка при подключении", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean tryConnect() {
        if (this.session == null) {
            this.connect();
            if (this.session == null) {
                return false;
            }
        }
        return true;
    }

    private void disconnect() {
        if (this.session != null) {
            try {
                this.session.close();
            } catch (Exception ignored) {

            }
        }
        this.session = null;
        this.statusLabel.setText("Отключено");
    }

    private <T> void openTable(Class<T> entity) {
        if (!this.tryConnect()) {
            return;
        }
//        String.format("FROM %s ORDER BY id", entity.getName())
        List q = this.session.createQuery(String.format("FROM %s ORDER BY id", entity.getName())).list();
        ArrayList<T> models = (ArrayList<T>) q
                .stream()
                .map(e -> entity.cast(e))
                .collect(Collectors.toList());

        this.currentEntity = entity;
        if (!models.isEmpty()) {
            this.table.setModel(new view.TableModel(models));
            this.currentEntityLabel.setText(models.get(0).getClass().getName());
        }
    }

    private void addEntity() throws Exception {
//        if (!this.tryConnect() || this.currentEntity == null) {
//            return;
//        }
//
//        IModel model = null;
//        try {
//            model = (IModel) this.currentEntity.newInstance();
//        } catch (Exception ex) {
//            return;
//        }
//
//        EntityDialog entityDialog = new EntityDialog(this.frame, model, this.session);
//        entityDialog.setVisible(true);
//        if (entityDialog.isAccepted()) {
//            model = entityDialog.getEntity();
//            Transaction transaction = null;
//            try {
//                transaction = session.beginTransaction();
//                String id = UUID.randomUUID().toString();
//                model.setId(id);
//                this.session.saveOrUpdate(model);
//
//                Object[] objects = model.getColumns();
//                Class[] objectsClasses = model.getColumnsTypes();
//                Class[] objectsSubTargetClasses = model.getSubColumnsTargetTypes();
//                for (int objectIndex = 0; objectIndex < objects.length; objectIndex++) {
//                    if (IModel.class.isAssignableFrom(objectsClasses[objectIndex])) {
//                        IModel subModel = (IModel) objects[objectIndex];
//                        subModel.setParentId(id);
//                        this.session.saveOrUpdate(subModel);
//                    } else if (Collection.class.isAssignableFrom(objectsClasses[objectIndex]) && IModel.class.isAssignableFrom(objectsSubTargetClasses[objectIndex])) {
//                        for (Object object : (Collection<?>) objects[objectIndex]) {
//                            IModel subModel = (IModel) object;
//                            subModel.setParentId(id);
//                            this.session.saveOrUpdate(subModel);
//                        }
//                    }
//                }
//
//                transaction.commit();
//            } catch (HibernateException ex) {
//                if (transaction != null) {
//                    transaction.rollback();
//                    JOptionPane.showMessageDialog(this.frame, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        }
//
//        this.openTable(this.currentEntity);
        if (this.currentEntity == null) throw new Exception("no");

        switch (this.currentEntity.getName()) {
            case "model.Sportsman":
                SportsmanView sv = new SportsmanView(this.session);
                sv.setVisible(true);
                break;
            case "model.Discipline":
                DisciplineView dv = new DisciplineView(this.session);
                dv.setVisible(true);
                break;
            case "model.Result":
                ResultView rv = new ResultView(this.session);
                rv.setVisible(true);
                break;
            case "model.Medicine":
                MedicineView mv = new MedicineView(this.session);
                mv.setVisible(true);
                break;
            case "model.Country":
                CountryView cv = new CountryView(this.session);
                cv.setVisible(true);
                break;
        }
    }

    private void editEntity() throws Exception {
//        if (!this.tryConnect() || this.table == null || this.table.getEntity().getRowCount() <= 0 || this.table.getSelectedRow() == -1) {
//            return;
//        }
//
//        view.TableModel tableModel = (view.TableModel) this.table.getEntity();
//        IModel model = tableModel.getEntity(this.table.getSelectedRow());
//        EntityDialog entityDialog = new EntityDialog(this.frame, model, this.session);
//        entityDialog.setVisible(true);
//        if (entityDialog.isAccepted()) {
//            model = entityDialog.getEntity();
//            Transaction transaction = null;
//            try {
//                transaction = session.beginTransaction();
//                this.session.saveOrUpdate(model);
//                transaction.commit();
//            } catch (HibernateException ex) {
//                if (transaction != null) {
//                    transaction.rollback();
//                    JOptionPane.showMessageDialog(this.frame, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        }
//
//        this.openTable(this.currentEntity);
        if (this.currentEntity == null) throw new Exception("no");

        switch (this.currentEntity.getName()) {
            case "model.Sportsman":
                Sportsman s = (Sportsman) ((view.TableModel) this.table.getModel()).getEntity(this.table.getSelectedRow());
                SportsmanView sv = new SportsmanView(this.session, s);
                sv.setVisible(true);
                break;
            case "model.Discipline":
                Discipline d = (Discipline) ((view.TableModel) this.table.getModel()).getEntity(this.table.getSelectedRow());
                DisciplineView dv = new DisciplineView(this.session, d);
                dv.setVisible(true);
                break;
            case "model.Result":
                Result r = (Result) ((view.TableModel) this.table.getModel()).getEntity(this.table.getSelectedRow());
                ResultView rv = new ResultView(this.session, r);
                rv.setVisible(true);
                break;
            case "model.Medicine":
                Medicine m = (Medicine) ((view.TableModel) this.table.getModel()).getEntity(this.table.getSelectedRow());
                MedicineView mv = new MedicineView(this.session, m);
                mv.setVisible(true);
                break;
            case "model.Country":
                Country c = (Country) ((view.TableModel) this.table.getModel()).getEntity(this.table.getSelectedRow());
                CountryView cv = new CountryView(this.session, c);
                cv.setVisible(true);
                break;
        }
    }
//
//    private void setDeletedEntity(boolean deleted) {
//        if (!this.tryConnect() || this.table == null || this.table.getModel().getRowCount() <= 0 || this.table.getSelectedRow() == -1) {
//            return;
//        }
//
//        view.TableModel tableModel = (view.TableModel) this.table.getModel();
//        IModel model = tableModel.getEntity(this.table.getSelectedRow());
//
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            model.setDeleted(deleted);
//            this.session.saveOrUpdate(model);
//            transaction.commit();
//        } catch (HibernateException ex) {
//            if (transaction != null) {
//                transaction.rollback();
//                JOptionPane.showMessageDialog(this.frame, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//
//        this.openTable(this.currentEntity);
//    }

    private <T> void deleteEntity(Class<T> c) {
        if (!this.tryConnect() || this.table == null || this.table.getModel().getRowCount() <= 0 || this.table.getSelectedRow() == -1) {
            return;
        }

        view.TableModel tableModel = (view.TableModel) this.table.getModel();
        T model = c.cast(tableModel.getEntity(this.table.getSelectedRow()));

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            this.session.delete(model);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this.frame, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }

        this.openTable(this.currentEntity);
    }

    public void setVisible() {
        this.frame.setVisible(true);
    }
}
