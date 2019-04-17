package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class ResultView implements EntityView<Result> {
    private Session session;
    private Result model;
    private JFrame frame;

    private JTextField textRecord;
    private JTextField textPlace;
    private JTextField textYear;
    private JSpinner spinner;

    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public Result getModel() {
        return model;
    }

    @Override
    public void setModel(Result model) {
        this.model = model;
    }

    public void invoke() {
        frame = new JFrame();
        frame.setBounds(100, 100, 316, 262);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
            Transaction tx1 = session.beginTransaction();
            try {
                if (model == null) {
                    model = new Result(
                            Integer.parseInt(textPlace.getText()),
                            Integer.parseInt(textYear.getText()),
                            Integer.parseInt(textRecord.getText()),
                            (Discipline) spinner.getValue()
                    );
                } else {
                    model.setPlace(Integer.parseInt(textPlace.getText()));
                    model.setYear(Integer.parseInt(textYear.getText()));
                    model.setRecord(Integer.parseInt(textRecord.getText()));
                    model.setDiscipline((Discipline) spinner.getValue());
                }
                session.saveOrUpdate(model);

                tx1.commit();
                frame.dispose();
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.frame, "В поле Place, Year или Record не число!\n" + ex, "Ошибка!", JOptionPane.ERROR_MESSAGE);
                tx1.commit();
            }
        });
        btnOk.setBounds(80, 177, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> frame.dispose());
        btnCancel.setBounds(189, 177, 97, 25);
        frame.getContentPane().add(btnCancel);

        JLabel lblRecord = new JLabel("record");
        lblRecord.setBounds(12, 83, 56, 16);
        frame.getContentPane().add(lblRecord);

        textRecord = new JTextField();
        textRecord.setBounds(80, 80, 116, 22);
        if (model != null) textRecord.setText("" + model.getRecord());
        frame.getContentPane().add(textRecord);
        textRecord.setColumns(10);

        JLabel lblPlace = new JLabel("place");
        lblPlace.setBounds(12, 13, 56, 16);
        frame.getContentPane().add(lblPlace);

        textPlace = new JTextField();
        textPlace.setBounds(80, 10, 116, 22);
        if (model != null) textPlace.setText("" + model.getPlace());
        frame.getContentPane().add(textPlace);
        textPlace.setColumns(10);

        JLabel lblYear = new JLabel("year");
        lblYear.setBounds(12, 48, 56, 16);
        frame.getContentPane().add(lblYear);

        textYear = new JTextField();
        textYear.setBounds(80, 45, 116, 22);
        if (model != null) textYear.setText("" + model.getYear());
        frame.getContentPane().add(textYear);
        textYear.setColumns(10);

        spinner = new JSpinner();
        spinner.setModel(new SpinnerListModel(this.session.createQuery("FROM model.Discipline ORDER BY id").list()));
        spinner.setBounds(80, 115, 116, 22);
        frame.getContentPane().add(spinner);

        JLabel lblDiscipline = new JLabel("discipline");
        lblDiscipline.setBounds(12, 118, 56, 16);
        frame.getContentPane().add(lblDiscipline);
        frame.setVisible(true);
    }
}
