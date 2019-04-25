package view;

import dao.DAO;
import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class CountryView extends JFrame {
    private DAO<Country> dao;
    private Country c;

    public CountryView(DAO<Country> dao) {
        this.dao = dao;
        initialize();
    }

    public CountryView(DAO<Country> dao, Country c) {
        this.dao = dao;
        this.c = c;
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

            if (c == null) {
                c = new Country(textName.getText());
                dao.save(c);
            } else {
                c.setName(textName.getText());
                dao.update(c);
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