package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class DisciplineView extends JFrame {
    private Session session;
    private Discipline d;

    public DisciplineView(Session session) {
        this.session = session;
        initialize();
    }

    public DisciplineView(Session session, Discipline d) {
        this.session = session;
        this.d = d;
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
            Transaction tx1 = session.beginTransaction();
            if (d == null) {
                d = new Discipline(textName.getText());
            } else {
                d.setName(textName.getText());
            }
            session.saveOrUpdate(d);
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
