package view;

import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;

public class SportsmanView extends JFrame {
    private final Session session;
    private JTextField textFName;
    private JTextField textMName;
    private JTextField textLName;
    private JTextField textAge;
    private JTextField textWeight;
    private JSpinner spinnerResult;
    private JSpinner spinnerCountry;
    private JSpinner spinnerMedicine;

    public SportsmanView(Session session) {
        this.session = session;
        JFrame frame = this;
        frame.setBounds(100, 100, 316, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
            Transaction tx1 = session.beginTransaction();
            session.save(new Sportsman(
                    textFName.getText(),
                    textMName.getText(),
                    textLName.getText(),
                    Integer.parseInt(textAge.getText()),
                    Integer.parseInt(textWeight.getText()),
                    (Result) spinnerResult.getValue(),
                    (Country) spinnerCountry.getValue(),
                    (Medicine) spinnerMedicine.getValue()
            ));
            tx1.commit();
            dispose();
        });
        btnOk.setBounds(80, 293, 97, 25);
        frame.getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        btnCancel.setBounds(189, 293, 97, 25);
        frame.getContentPane().add(btnCancel);

        textFName = new JTextField();
        textFName.setBounds(110, 13, 116, 22);
        frame.getContentPane().add(textFName);
        textFName.setColumns(10);

        textMName = new JTextField();
        textMName.setBounds(110, 48, 116, 22);
        frame.getContentPane().add(textMName);
        textMName.setColumns(10);

        textLName = new JTextField();
        textLName.setBounds(110, 83, 116, 22);
        frame.getContentPane().add(textLName);
        textLName.setColumns(10);

        textAge = new JTextField();
        textAge.setBounds(110, 118, 116, 22);
        frame.getContentPane().add(textAge);
        textAge.setColumns(10);

        textWeight = new JTextField();
        textWeight.setBounds(110, 153, 116, 22);
        frame.getContentPane().add(textWeight);
        textWeight.setColumns(10);

        spinnerResult = new JSpinner();
        spinnerResult.setModel(new SpinnerListModel(this.session.createQuery("FROM model.Result ORDER BY id").list()));
        spinnerResult.setBounds(110, 188, 116, 22);
        frame.getContentPane().add(spinnerResult);

        spinnerCountry = new JSpinner();
        spinnerCountry.setModel(new SpinnerListModel(this.session.createQuery("FROM model.Country ORDER BY id").list()));
        spinnerCountry.setBounds(110, 223, 116, 22);
        frame.getContentPane().add(spinnerCountry);

        spinnerMedicine = new JSpinner();
        spinnerMedicine.setModel(new SpinnerListModel(this.session.createQuery("FROM model.Medicine ORDER BY id").list()));
        spinnerMedicine.setBounds(110, 258, 116, 22);
        frame.getContentPane().add(spinnerMedicine);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(12, 86, 71, 16);
        frame.getContentPane().add(lblLastName);

        JLabel lblMidName = new JLabel("Mid Name");
        lblMidName.setBounds(12, 51, 71, 16);
        frame.getContentPane().add(lblMidName);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setBounds(12, 16, 86, 16);
        frame.getContentPane().add(lblFirstName);

        JLabel lblAge = new JLabel("Age");
        lblAge.setBounds(12, 121, 56, 16);
        frame.getContentPane().add(lblAge);

        JLabel lblWeight = new JLabel("Weight");
        lblWeight.setBounds(12, 156, 56, 16);
        frame.getContentPane().add(lblWeight);

        JLabel lblResult = new JLabel("Result");
        lblResult.setBounds(12, 191, 56, 16);
        frame.getContentPane().add(lblResult);

        JLabel lblCountry = new JLabel("Country");
        lblCountry.setBounds(12, 226, 56, 16);
        frame.getContentPane().add(lblCountry);

        JLabel lblMedicine = new JLabel("Medicine");
        lblMedicine.setBounds(12, 261, 56, 16);
        frame.getContentPane().add(lblMedicine);
    }
}
