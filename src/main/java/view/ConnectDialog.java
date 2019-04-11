package view;

import database.ConnectionInfo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ConnectDialog extends JDialog {
    private JTextField tfHost;
    private JTextField tfPort;
    private JTextField tfDBName;
    private JTextField tfUser;
    private JPasswordField pfPassword;

    private boolean accepted;
    private ConnectionInfo connectionInfo;

    public ConnectDialog(Frame parent) {
        super(parent, "Подключение", true);

        this.accepted = false;
        this.connectionInfo = null;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new LineBorder(Color.GRAY));
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbHost = new JLabel("Хост: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(lbHost, constraints);

        this.tfHost = new JTextField(20);
        this.tfHost.setText("localhost");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(tfHost, constraints);

        JLabel lbPort = new JLabel("Порт: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(lbPort, constraints);

        this.tfPort = new JTextField(20);
        this.tfPort.setText("5432");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(tfPort, constraints);

        JLabel lbDBName = new JLabel("БД: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(lbDBName, constraints);

        this.tfDBName = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(tfDBName, constraints);

        JLabel lbUser = new JLabel("Пользователь: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(lbUser, constraints);

        this.tfUser = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(tfUser, constraints);

        JLabel lbPassword = new JLabel("Пароль: ");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(lbPassword, constraints);

        this.pfPassword = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(pfPassword, constraints);

        JButton btnLogin = new JButton("Подключится");
        btnLogin.addActionListener(event -> {
            this.accepted = true;
            this.connectionInfo = new ConnectionInfo(
                    this.tfHost.getText(),
                    this.tfPort.getText(),
                    this.tfDBName.getText(),
                    this.tfUser.getText(),
                    new String(this.pfPassword.getPassword())
            );
            dispose();
        });
        JButton btnCancel = new JButton("Отмена");
        btnCancel.addActionListener(event -> dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnLogin);
        btnPanel.add(btnCancel);

        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.getContentPane().add(btnPanel, BorderLayout.PAGE_END);

        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(parent);
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }
}

