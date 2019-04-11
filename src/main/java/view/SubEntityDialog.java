package view;

import model.IModel;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Vector;

public class SubEntityDialog extends JDialog {
    private boolean accepted;
    private IModel model;

    public SubEntityDialog(Frame parent, IModel model, Session session) {
        super(parent, model.getModelName(), true);

        this.accepted = false;
        this.model = null;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new LineBorder(Color.GRAY));

        Vector<IModel> models = new Vector<>();
        models.add(null);
        final Query query = session.createQuery(String.format("FROM %s ORDER BY id", model.getClass().getName()));
        for (Object object : query.list()) {
            models.add((IModel) object);
        }

        JComboBox<IModel> comboBox = new JComboBox<>(models);
        comboBox.addActionListener(event -> this.model = (IModel) comboBox.getSelectedItem());
        panel.add(comboBox);

        JPanel btnPanel = new JPanel();

        JButton btnLogin = new JButton("Ок");
        btnLogin.addActionListener(event -> {
            if (this.model == null) {
                JOptionPane.showMessageDialog(this, "Не все поля заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.accepted = true;
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