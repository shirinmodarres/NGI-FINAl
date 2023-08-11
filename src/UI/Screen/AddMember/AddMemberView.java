package UI.Screen.AddMember;

import Core.DataBase.UserDatabase;
import Core.Manager.ProjectAssignments;
import Core.Manager.ProjectManager;
import Core.Manager.UserManager;
import Core.Model.Project;
import Core.Model.Role;
import Core.Model.User;
import UI.Component.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class AddMemberView extends JPanel {

    AddMemberController addMemberController;

    public AddMemberView(UserManager userManager, ProjectManager projectManager, AddMemberViewEventListener addMemberViewEventListener, ProjectSelectedListener projectSelectedListener) {

        addMemberController = new AddMemberController(userManager); // Provide userManager to AddMemberController
        //Setting
        setLayout(null);
        setBounds(85, 15, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(false);

        //Header
        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Add Members", titleFont, 20, 32, 390, 40);
        add(title);

        //setUp component
        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel name = new CustomLabel("Name:", font, 25, 80, 110, 23);
        add(name);

        CustomTextField nameField = new CustomTextField("Write Your Name", 20, 103, 260, 40);
        add(nameField);

        CustomLabel email = new CustomLabel("Email:", font, 385, 80, 110, 23);
        add(email);

        CustomTextField emailField = new CustomTextField("Write Your Email", 380, 103, 260, 40);
        add(emailField);

        CustomLabel password = new CustomLabel("Password:", font, 25, 160, 110, 23);
        add(password);

        CustomTextField passwordField = new CustomTextField("You Should write 8 Character ", 20, 183, 260, 40);
        add(passwordField);

        CustomLabel role = new CustomLabel("Role:", font, 385, 160, 110, 23);
        add(role);

        ArrayList<String> roleName = new ArrayList<>();
        for (Role r : Role.values()) {
            switch (r) {
                case PO:
                    roleName.add("PO");
                    break;
                case TESTER:
                    roleName.add("TESTER");
                    break;
                case DEVELOPER:
                    roleName.add("DEVELOPER");
                    break;
            }
        }
        DropdownField roleDropDown = new DropdownField(roleName, 380, 183, 260, 40);
        add(roleDropDown);

        CustomLabel addToProjrctLabel = new CustomLabel("Assign to project:", font, 25, 263, 250, 23);
        add(addToProjrctLabel);

        CustomLabel project = new CustomLabel("Project:", font, 385, 263, 110, 23);
        add(project);

        List<String> projectNames = new ArrayList<>();
        for (Project p : projectManager.getProjectDatabase().getAllProjects()) {
            projectNames.add(p.getTitle()); // Assuming Project class has a method to get the project name
        }

        DropdownField projectDropDown = new DropdownField(projectNames, 20, 286, 260, 40);
        add(projectDropDown);

        String[] listArray = new String[20];
        JList list = new JList<>(listArray);
        list.setFixedCellHeight(20);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setPreferredSize(new Dimension(c.getPreferredSize().width, 35)); // Adjust the preferred height here
                return c;
            }
        });
        list.setSize(260, 400);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(380, 286, 260, 200);
        add(scrollPane);
        ImageButton set = new ImageButton("img/next.png", 300, 300, 35, 35);
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 20; i++) {
                    if (listArray[i] == null) {
                        listArray[i] = projectDropDown.getSelectedItem().toString();
                        list.repaint();
                        break;
                    }
                }
            }
        });
        add(set);

        ImageButton remove = new ImageButton("img/pervious.png", 300, 360, 35, 35);
        add(remove);
        remove.addActionListener(e -> {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex != -1) { // Check if an item is selected
                    listArray[selectedIndex] = null; // Remove the selected item from the array
                    list.repaint();
                }


        });



        RoundedButton saveBtn = new RoundedButton("Save", 400, 510, 100, 40, new Color(96, 150, 180));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userManager.isEmailValid(emailField.getText()) && userManager.isPasswordValid(passwordField.getText())) {
                    // Extract data from input fields
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String password = passwordField.getText();
                    String selectedRole = (String) roleDropDown.getSelectedItem();
                    Role role = Role.valueOf(selectedRole.toUpperCase());

                    // Call the controller method to add the user
                    userManager.addUser(name, email, password, role);
                    User userDb=UserDatabase.getInstance().findUserByEmail(email);
                    String selectedProjectName = (String) projectDropDown.getSelectedItem();
                    Project selectedProject = projectManager.getProjectByTitle(selectedProjectName);
                    if (selectedProject != null) {
                        // Assign the selected project to the added user
                        ProjectAssignments.getInstance().assignProjectToUser(userDb, selectedProject);
                    }
                } else {
                    CustomLabel errorLabel = new CustomLabel("Invalid Input", font, 20, 520, 150, 25);
                    errorLabel.setForeground(new Color(214, 64, 69));
                    add(errorLabel);
                    errorLabel.setVisible(true);
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            errorLabel.setVisible(false);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }

                // Assuming the first item is a default selection
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                roleDropDown.setSelectedIndex(0);
                addMemberViewEventListener.onPageClosed();

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
                addMemberViewEventListener.onPageClosed();
            }
        });
        add(cancelBtn);

    }

    public interface AddMemberViewEventListener {
        void onPageClosed();

    }

    public interface ProjectSelectedListener {
        void onProjectSelected(Project project);
    }


}

