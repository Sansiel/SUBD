package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class ResultView extends JFrame {
    private final Session session;
    private Result r;

    private JTextField textRecord;
    private JTextField textPlace;
    private JTextField textYear;
    private JSpinner spinner;

    public ResultView(Session session) {
        this.session = session;
        initialize();
    }

    public ResultView(Session session, Result r) throws HeadlessException {
        this.session = session;
        this.r = r;
        initialize();
    }

    public void initialize() {
        JFrame frame = this;
        frame.setBounds(100, 100, 316, 262);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
            Transaction tx1 = session.beginTransaction();
            if (r == null) {
                r = new Result(
                        Integer.parseInt(textPlace.getText()),
                        Integer.parseInt(textYear.getText()),
                        Integer.parseInt(textRecord.getText()),
                        (Discipline) spinner.getValue()
                        );
            } else {
                r.setPlace(Integer.parseInt(textPlace.getText()));
                r.setYear(Integer.parseInt(textYear.getText()));
                r.setRecord(Integer.parseInt(textRecord.getText()));
                r.setDiscipline((Discipline) spinner.getValue());
            }
            session.saveOrUpdate(r);
            tx1.commit();
            dispose();
        });
        btnOk.setBounds(80, 177, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        btnCancel.setBounds(189, 177, 97, 25);
        frame.getContentPane().add(btnCancel);

        JLabel lblRecord = new JLabel("record");
        lblRecord.setBounds(12, 83, 56, 16);
        frame.getContentPane().add(lblRecord);

        textRecord = new JTextField();
        textRecord.setBounds(80, 80, 116, 22);
        frame.getContentPane().add(textRecord);
        textRecord.setColumns(10);

        JLabel lblPlace = new JLabel("place");
        lblPlace.setBounds(12, 13, 56, 16);
        frame.getContentPane().add(lblPlace);

        textPlace = new JTextField();
        textPlace.setBounds(80, 10, 116, 22);
        frame.getContentPane().add(textPlace);
        textPlace.setColumns(10);

        JLabel lblYear = new JLabel("year");
        lblYear.setBounds(12, 48, 56, 16);
        frame.getContentPane().add(lblYear);

        textYear = new JTextField();
        textYear.setBounds(80, 45, 116, 22);
        frame.getContentPane().add(textYear);
        textYear.setColumns(10);

        spinner = new JSpinner();
        spinner.setModel(new SpinnerListModel(this.session.createQuery("FROM model.Discipline ORDER BY id").list()));
        spinner.setBounds(80, 115, 116, 22);
        frame.getContentPane().add(spinner);

        JLabel lblDiscipline = new JLabel("discipline");
        lblDiscipline.setBounds(12, 118, 56, 16);
        frame.getContentPane().add(lblDiscipline);
    }
}
