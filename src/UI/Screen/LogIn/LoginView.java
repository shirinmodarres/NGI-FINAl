package UI.Screen.LogIn;

import Core.Model.User;
import UI.Component.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginView extends JPanel {

    private CustomTextField emailField;
    private final JPasswordField passwordField;
    private Font font = new Font("Calibri", Font.PLAIN, 20);

    public LoginView(LoginController loginController, LoginEventListener loginEventListener) {
        //setting
        setBounds(15, 15, 770, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(true);
        setLayout(null);


        //all components
        ImageIcon imageIcon = new ImageIcon("img/Asset 1.png");
        JLabel label = new JLabel(imageIcon);
        label.setBounds(197, 5, 419, 250);
        add(label);

        CustomLabel emailLabel = new CustomLabel("Email:", font, 250, 300, 149, 23);
        add(emailLabel);
        emailField = new CustomTextField(" ", 232, 323, 337, 50);
        add(emailField);

        CustomLabel passwordLabel = new CustomLabel("Password:", font, 250, 393, 149, 23);
        add(passwordLabel);
        passwordField = new JPasswordField("");
        passwordField.setBounds(232, 416, 337, 50);
        passwordField.setFont(font);
        passwordField.setForeground(Color.GRAY);
        passwordField.setBorder(new EmptyBorder(4, 6, 4, 6));
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (performLogin(loginController) != null) {
                        loginEventListener.onLogin();
                    }
                }
            }
        });
        add(passwordField);

        RoundedButton loginButton = new RoundedButton("Login", 340, 487, 121, 38, new Color(96, 150, 180));
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (performLogin(loginController) != null) {
                    loginEventListener.onLogin();
                }
            }
        });
        add(loginButton);
    }

    public User performLogin(LoginController loginController) {
        String email = emailField.getText();
        String password = passwordField.getText();



        if (loginController.isEmailValid(email)) {
            if (loginController.isPasswordValid(password)) {
                if (loginController.isValid(email, password) == null) {
                    showMessageDialog(null, "user doesn't exist");
                    return null;

                } else {
                    return loginController.isValid(email, password);
                }
            } else {
                showMessageDialog(null, "password isn't valid");
                return null;
            }

        } else {
            showMessageDialog(null, "email is not valid");
            return null;
        }
    }

    public interface LoginEventListener {
        void onLogin();
    }
}
