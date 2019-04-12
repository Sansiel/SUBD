package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;

public class DisciplineView extends JFrame {
    private final Session session;

    public DisciplineView(Session session) {
        this.session = session;
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
            Transaction tx1 = session.beginTransaction();
            session.save(new Discipline(textName.getText()));
            tx1.commit();
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
