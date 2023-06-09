package animalShelter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JDBC db = new JDBC();
    private Connection connection;

    public Login() {
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        setBounds(100, 100, 800, 550);
        setupPanels();
    }

    public void setupPanels()  {
        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);

        JLabel username = new JLabel("Username: ");
        username.setHorizontalAlignment(SwingConstants.TRAILING);
        username.setFont(new Font("Lava Kannada", Font.PLAIN, 15));

        JLabel login = new JLabel("Admin Login");
        login.setHorizontalAlignment(SwingConstants.CENTER);
        login.setForeground(new Color(90, 157, 155));
        login.setFont(new Font("Lava Kannada", Font.PLAIN, 40));

        JTextField usernameInput = new JTextField();
        username.setLabelFor(usernameInput);
        usernameInput.setColumns(10);

        JButton home = new JButton("Homepage");

        JLabel pswd = new JLabel("Password: ");
        pswd.setHorizontalAlignment(SwingConstants.TRAILING);
        pswd.setFont(new Font("Lava Kannada", Font.PLAIN, 15));

        JPasswordField pswdInput = new JPasswordField();
        pswd.setLabelFor(pswdInput);
        pswdInput.setColumns(10);

        JButton loginButton = new JButton("Login");

        JLabel messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Lava Kannada", Font.PLAIN, 13));
        GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
        gl_mainPanel.setHorizontalGroup(
                gl_mainPanel.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(gl_mainPanel.createSequentialGroup()
                                .addContainerGap(291, Short.MAX_VALUE)
                                .addComponent(login)
                                .addGap(267))
                        .addGroup(gl_mainPanel.createSequentialGroup()
                                .addGap(241)
                                .addGroup(gl_mainPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(gl_mainPanel.createSequentialGroup()
                                                .addComponent(username, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(gl_mainPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(home)
                                                        .addComponent(usernameInput, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(gl_mainPanel.createSequentialGroup()
                                                .addComponent(pswd, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pswdInput, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(246, Short.MAX_VALUE))
                        .addGroup(gl_mainPanel.createSequentialGroup()
                                .addGap(352)
                                .addComponent(loginButton)
                                .addContainerGap(359, Short.MAX_VALUE))
                        .addGroup(gl_mainPanel.createSequentialGroup()
                                .addContainerGap(255, Short.MAX_VALUE)
                                .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
                                .addGap(208))
        );
        gl_mainPanel.setVerticalGroup(
                gl_mainPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_mainPanel.createSequentialGroup()
                                .addGap(12)
                                .addComponent(login)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(home)
                                .addGap(33)
                                .addGroup(gl_mainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(username, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usernameInput, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(gl_mainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(pswd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pswdInput, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addGap(12)
                                .addComponent(loginButton)
                                .addContainerGap(198, Short.MAX_VALUE))
        );
        mainPanel.setLayout(gl_mainPanel);

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Main().setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameStr = usernameInput.getText();
                String pswdStr = pswdInput.getText();

                String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
                try {
                    connection = db.getCon();
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, usernameStr);
                    pst.setString(2, pswdStr);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()){
                        //login succeeded, redirect to next page
                        messageLabel.setForeground(Color.GREEN);
                        messageLabel.setText("Login succeeded!");
                        new AdminPage().setVisible(true);
                        dispose();
                    } else {
                        //login failed
                        messageLabel.setForeground(Color.RED);
                        messageLabel.setText("Login failed. Please check your username and password.");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
