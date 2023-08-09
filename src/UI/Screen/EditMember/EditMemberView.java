package UI.Screen.EditMember;

import Core.Manager.UserManager;
import Core.Model.Role;
import Core.Model.User;
import UI.Component.CustomLabel;
import UI.Component.CustomTextField;
import UI.Component.DropdownField;
import UI.Component.RoundedButton;
import UI.Screen.Member.MemberView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditMemberView extends JPanel {
    User initialUser;
    public EditMemberView(User user,UserManager userManager, EditMemberViewEventListener editMemberViewEventListener) {
        initialUser = user;
        //setting
        setVisible(true);
        setLayout(null);
        setBounds(0, 0, 700, 570);
        setBackground(new Color(251, 246, 230));

        //setup component
        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Edit Members", titleFont, 20, 32, 390, 40);
        add(title);

        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel name = new CustomLabel("Name:", font, 25, 80, 110, 23);
        add(name);
        CustomTextField nameField = new CustomTextField(user.getName(), 20, 103, 260, 40);
        add(nameField);

        CustomLabel email = new CustomLabel("Email:", font, 385, 80, 110, 23);
        add(email);
        CustomTextField emailField = new CustomTextField(user.getEmail(), 380, 103, 260, 40);
        add(emailField);

        CustomLabel password = new CustomLabel("Password:", font, 25, 160, 110, 23);
        add(password);

        CustomTextField passwordField = new CustomTextField(user.getPassword(), 20, 183, 260, 40);
        add(passwordField);

        CustomLabel role = new CustomLabel("Role:", font, 385, 160, 110, 23);
        add(role);

        ArrayList<String> roleName = new ArrayList<>();
        for (Role r : Role.values()) {
            switch (r) {
                case PO -> roleName.add("PO");
                case TESTER -> roleName.add("TESTER");
                case DEVELOPER -> roleName.add("DEVELOPER");
            }
        }
        DropdownField roleDropDown = new DropdownField(roleName.toArray(new String[0]), 380, 183, 260, 40);
        add(roleDropDown);

        CustomLabel project = new CustomLabel("Project:", font, 25, 240, 110, 23);
        add(project);

        RoundedButton saveBtn = new RoundedButton("Save", 400, 510, 100, 40, new Color(96, 150, 180));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Extract data from input fields
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String selectedRole = (String) roleDropDown.getSelectedItem();
                Role role = Role.valueOf(selectedRole.toUpperCase());

                User updatedUser=new User(user.getId(), name, email, password, role);
                userManager.getUserDatabase().editUser(updatedUser);
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                roleDropDown.setSelectedIndex(0); // Assuming the first item is a default selection
                editMemberViewEventListener.PageClosed(updatedUser);
            }
        });
        add(saveBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 515, 510, 100, 40, new Color(214, 64, 69));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                roleDropDown.setSelectedIndex(0); // Assuming the first item is a default selection
                editMemberViewEventListener.PageClosed(initialUser);
            }
        });
        add(cancelBtn);
    }

    public interface EditMemberViewEventListener {
        void PageClosed(User user);
    }

}
