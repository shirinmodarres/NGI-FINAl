package UI.Screen.LogIn;

import Core.Model.User;
import UI.Component.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginView extends JPanel {

    private RoundedButton loginButton;
    private CustomTextField emailField;
    private JPasswordField passwordField;

    private Font title = new Font("Calibri", Font.PLAIN, 40);
    private Font btnFilter = new Font("Calibri", Font.PLAIN, 24);
    private Font font = new Font("Calibri", Font.PLAIN, 20);
    CustomBorder btnBorder = new CustomBorder(new Color(96, 150, 180, 38), 2);

    public LoginView(LoginController loginController,LoginEventListener loginEventListener) {
        setBounds(15, 15, 770, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(true);

        ImageIcon imageIcon = new ImageIcon("img/Asset 1.png");
        JLabel label = new JLabel(imageIcon);
        label.setBounds(197, 5, 419, 250);
        add(label);
        CustomLabel emailLabel = new CustomLabel("Email:", font, 250, 300, 149, 23);

        emailField = new CustomTextField(" ", 232, 323, 337, 50);

        CustomLabel passwordLabel = new CustomLabel("Password:", font, 250, 393, 149, 23);
        passwordField = new JPasswordField("");
        passwordField.setBounds(232, 416, 337, 50);
        passwordField.setFont(font);
        passwordField.setForeground(Color.GRAY);
        passwordField.setBorder(new EmptyBorder(4, 6, 4, 6));
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin(loginController);

                }
            }
        });

        loginButton = new RoundedButton("Login", 340, 487, 121, 38, new Color(96, 150, 180));
        emailField.setBorder(btnBorder);


        loginButton.setForeground(Color.BLACK);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (performLogin(loginController)!=null) {
                    loginEventListener.onLogin();
                    System.out.println(performLogin(loginController));
                }

            }
        });


        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        setLayout(null);
        setVisible(true);
    }


    public User performLogin(LoginController loginController) {
//        String email = emailField.getText();
//        String password = passwordField.getText();
        String email = "shirinmodarres24@gmail.com";
        String password = "shirin44";

        boolean isPasswordValid = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$", password);
        boolean isEmailValid = Pattern.matches("^^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.)+[A-Za-z]{2,}$", email);


        if (isEmailValid) {
            if (isPasswordValid) {
                if (loginController.isValid(email, password)==null) {
                    showMessageDialog(null, "user doesn't exist");
                    return null;

                } else {
                    return loginController.isValid(email, password) ;
                }
            } else {
                showMessageDialog(null, "password isn't valid");
                return null;
            }

        } else {
            showMessageDialog(null, "email is not valid");
            return null;
        }

        //TODO is validUser ==> userExist
    }

    public static void resizeImage(File originalFile, File resizedFile, int newWidth, int newHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(originalFile);
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        BufferedImage resizedBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedBufferedImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();
        ImageIO.write(resizedBufferedImage, "png", resizedFile);
    }
    public interface LoginEventListener {
        void onLogin();
    }
}
