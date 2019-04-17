package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;

public class DisciplineView implements EntityView<Discipline> {
    private Session session;
    private Discipline model;
    private JFrame frame;

    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public Discipline getModel() {
        return model;
    }

    @Override
    public void setModel(Discipline model) {
        this.model = model;
    }

    public void invoke(){
        frame = new JFrame();
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
            if (model == null) {
                model = new Discipline(textName.getText());
            } else {
                model.setName(textName.getText());
            }
            session.saveOrUpdate(model);
            tx1.commit();
            frame.dispose();
        });
        btnOk.setBounds(111, 47, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> frame.dispose());
        btnCancel.setBounds(220, 47, 97, 25);
        frame.getContentPane().add(btnCancel);
        frame.setVisible(true);
    }


}
