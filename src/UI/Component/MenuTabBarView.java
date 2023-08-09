package UI.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTabBarView extends JPanel {

    public MenuTabBarView(ActionListener projectShow,ActionListener memberShow,ActionListener logOutEvent) {

        //Setting
        setBounds(15, 15, 60, 570);
        setBackground(new Color(251, 246, 230));
        setLayout(null);
        setVisible(true);

        ImageButton profileIcon = new ImageButton("img/profile.png", 5, 30, 44, 44);
        add(profileIcon);

        ImageButton memberIcon = new ImageButton("img/users.png", 5, 90, 44, 44);
        memberIcon.addActionListener(memberShow);
        add(memberIcon);


        ImageButton projectIcon = new ImageButton("img/projects.png", 5, 150, 44, 44);
        projectIcon.addActionListener(projectShow);
        add(projectIcon);

        ImageButton logOutIcon = new ImageButton("img/logOut.png", 5, 510, 44, 44);
        logOutIcon.addActionListener(logOutEvent);
        add(logOutIcon);




    }


}
