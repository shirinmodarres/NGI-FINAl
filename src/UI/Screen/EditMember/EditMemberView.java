package UI.Screen.EditMember;

import Core.DataBase.AssignUserProjectRepository;
import Core.DataBase.UserDatabase;
import Core.Manager.ProjectAssignments;
import Core.Manager.ProjectManager;
import Core.Manager.UserManager;
import Core.Model.Project;
import Core.Model.Role;
import Core.Model.User;
import UI.Component.*;
import UI.Screen.Member.MemberView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditMemberView extends JPanel {
    User initialUser;

    public EditMemberView(User user, ProjectManager projectManager, UserManager userManager, EditMemberViewEventListener editMemberViewEventListener) {
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

        java.util.List<String> projectNames = new ArrayList<>();
        for (Project p : projectManager.getProjectDatabase().getAllProjects()) {
            projectNames.add(p.getTitle()); // Assuming Project class has a method to get the project name
        }

        DropdownField projectDropDown = new DropdownField(projectNames, 20, 286, 260, 40);
        add(projectDropDown);
        java.util.List<Project> assignedProjects = ProjectAssignments.getInstance().getAssignedProjectsForUser(user); // Replace 'user' with the actual user object
        int numAssignedProjects = assignedProjects.size();

        String[] listArray = new String[numAssignedProjects];
        int index = 0;

        for (Project p : assignedProjects) {
            listArray[index++] = p.getTitle();
        }

        JList<String> list = new JList<>(listArray);
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
                String selectedProject = projectDropDown.getSelectedItem().toString();

                // Check if the project is already in the list
                boolean projectExists = false;
                for (int i = 0; i < listArray.length; i++) {
                    if (selectedProject.equals(listArray[i])) {
                        projectExists = true;
                        break;
                    }
                }

                // If the project is not in the list, find an empty slot and add it
                if (!projectExists) {
                    for (int i = 0; i < listArray.length; i++) {
                        if (listArray[i] == null) {
                            listArray[i] = selectedProject;
                            list.repaint();
                            break;
                        }
                    }
                }
            }
        });
        add(set);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex >= 0) {
                    String selectedProject = listArray[selectedIndex];


                    // Update the listArray with the edited project name
                    if (selectedProject != null) {
                        listArray[selectedIndex] = selectedProject;
                        list.repaint();
                    }
                }
            }
        });

        ImageButton remove = new ImageButton("img/pervious.png", 300, 360, 35, 35);
        add(remove);
        remove.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) { // Check if an item is selected
                String selectedProjectName = listArray[selectedIndex];
                listArray[selectedIndex] = null; // Remove the selected item from the array
                list.repaint();

                // Assuming you have a method to get the Project object by its name
                Project selectedProject = projectManager.getProjectByTitle(selectedProjectName);

                if (selectedProject != null) {
                    // Unassign the selected project from the user
                    AssignUserProjectRepository.getInstance().unassignProjectFromUser(user,selectedProject);
                }
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

                    User updatedUser = new User(user.getId(), name, email, password, role);
                    // Call the controller method to add the user
                    UserDatabase.getInstance().editUser(updatedUser);
                    String selectedProjectName = (String) projectDropDown.getSelectedItem();
                    Project selectedProject = projectManager.getProjectByTitle(selectedProjectName);

                    if (selectedProject != null) {
                        // Assign the selected project to the added user
                        ProjectAssignments.getInstance().assignProjectToUser(updatedUser, selectedProject);
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
                editMemberViewEventListener.PageClosed(user);

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
