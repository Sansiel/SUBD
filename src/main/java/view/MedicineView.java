package view;

import dao.DAO;
import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class MedicineView extends JFrame {
    private DAO<Medicine> dao;
    private Medicine m;

    public MedicineView(DAO<Medicine> dao) {
        this.dao = dao;
        initialize();
    }

    public MedicineView(DAO<Medicine> dao, Medicine m) {
        this.dao = dao;
        this.m = m;
        initialize();
    }

    private void initialize(){
        JFrame frame = this;
        frame.setBounds(100, 100, 347, 132);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("name");
        lblName.setBounds(12, 13, 56, 16);
        frame.getContentPane().add(lblName);

        JTextField textName = new JTextField();
        textName.setBounds(80, 10, 116, 22);
        frame.getContentPane().add(textName);
        textName.setColumns(10);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
            if (m == null) {
                m = new Medicine(textName.getText());
                dao.save(m);
            } else {
                m.setName(textName.getText());
                dao.update(m);
            }
            dispose();
        });
        btnOk.setBounds(111, 47, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        btnCancel.setBounds(220, 47, 97, 25);
        frame.getContentPane().add(btnCancel);
    }
}