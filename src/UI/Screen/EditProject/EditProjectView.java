package UI.Screen.EditProject;

import Core.DataBase.AssignUserProjectRepository;
import Core.DataBase.ProjectDatabase;
import Core.DataBase.UserDatabase;
import Core.Manager.ProjectAssignments;
import Core.Manager.ProjectManager;
import Core.Manager.UserManager;
import Core.Model.Project;
import Core.Model.Role;
import Core.Model.User;
import UI.Component.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditProjectView extends JPanel {
    Project initialProject;

    public EditProjectView(Project project, ProjectManager projectManager, EditProjectViewEventListener editProjectViewEventListener) {
        initialProject = project;

        //setting
        setLayout(null);
        setBounds(0, 0, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(true);

        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Edit Project", titleFont, 20, 32, 390, 40);
        add(title);

        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel name = new CustomLabel("title:", font, 25, 80, 110, 23);
        add(name);

        CustomTextField nameField = new CustomTextField(project.getTitle(), 20, 103, 260, 40);
        add(nameField);

        CustomLabel description = new CustomLabel("Description:", font, 25, 160, 23, 23);
        add(description);

        CustomTextArea descriptionField = new CustomTextArea(project.getDescription(), 20, 185, 600, 50, new Color(147, 191, 207));
        add(descriptionField);

        CustomLabel addToUSerLabel = new CustomLabel("Assign to project:", font, 25, 263, 250, 23);
        add(addToUSerLabel);

        CustomLabel members = new CustomLabel("Member:", font, 385, 263, 110, 23);
        add(members);

        java.util.List<String> userNames = new ArrayList<>();
        for (User u : UserManager.getInstance(UserDatabase.getInstance()).getUserDatabase().getAllUsers()) {
            userNames.add(u.getName());
        }

        DropdownField userDropDown = new DropdownField(userNames, 20, 286, 260, 40);
        add(userDropDown);
        java.util.List<User> assignedUser = ProjectAssignments.getInstance().getMembersForProject(project); // Replace 'user' with the actual user object
        int numAssignedUser = assignedUser.size();

        String[] listArray = new String[numAssignedUser];
        int index = 0;

        for (User u : assignedUser) {
            listArray[index++] = u.getName();
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
                String selectedUser = userDropDown.getSelectedItem().toString();

                // Check if the project is already in the list
                boolean projectExists = false;
                for (int i = 0; i < listArray.length; i++) {
                    if (selectedUser.equals(listArray[i])) {
                        projectExists = true;
                        break;
                    }
                }

                // If the project is not in the list, find an empty slot and add it
                if (!projectExists) {
                    for (int i = 0; i < listArray.length; i++) {
                        if (listArray[i] == null) {
                            listArray[i] = selectedUser;
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
                User selectedUser = UserManager.getInstance(UserDatabase.getInstance()).findUserByName(selectedProjectName);

                if (selectedUser != null) {
                    // Unassign the selected project from the user
                    AssignUserProjectRepository.getInstance().unassignProjectFromUser(selectedUser, project);
                }
            }


        });


        RoundedButton saveBtn = new RoundedButton("Save", 400, 510, 100, 40, new Color(96, 150, 180));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Extract data from input fields
                String name = nameField.getText();
                String description = descriptionField.getText();

                Project updatedProject = new Project(project.getId(), name, description);
                // Call the controller method to add the project
                ProjectDatabase.getInstance().updateProject(updatedProject);
                String selectedUserName = (String) userDropDown.getSelectedItem();
                User selectedUser = UserManager.getInstance(UserDatabase.getInstance()).findUserByName(selectedUserName);

                if (selectedUser != null) {
                    // Assign the selected project to the added user
                    ProjectAssignments.getInstance().assignProjectToUser(selectedUser, updatedProject);
                }


                // Assuming the first item is a default selection
                nameField.setText("");
                descriptionField.setText("");
                editProjectViewEventListener.onPageClosed(initialProject);
            }

        });

        add(saveBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 515, 510, 100, 40, new Color(214, 64, 69));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                descriptionField.setText("");
                editProjectViewEventListener.onPageClosed(initialProject);
            }
        });

        add(cancelBtn);
    }


    public interface EditProjectViewEventListener {
        void onPageClosed(Project project);
    }
}


