package UI.Screen.AddProject;

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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static UI.Component.Colors.getRandomColor;


public class AddProjectView extends JPanel {
    AddProjectController addProjectController;

    public AddProjectView(UserManager userManager ,ProjectManager projectManager, AddProjectViewEventListener addProjectViewEventListener) {

        addProjectController = new AddProjectController(projectManager); // Provide userManager to AddMemberController
        //Setting
        setLayout(null);
        setBounds(85, 15, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(false);

        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Add Project", titleFont, 20, 32, 390, 40);
        add(title);

        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel name = new CustomLabel("Title:", font, 25, 80, 110, 23);
        add(name);

        CustomTextField nameField = new CustomTextField("Write Your project's title", 20, 103, 260, 40);
        add(nameField);

        CustomLabel description = new CustomLabel("Description:", font, 25, 160, 110, 23);
        add(description);

        CustomTextArea descriptionField = new CustomTextArea("Description...", 20, 185, 600, 50, new Color(147, 191, 207));
        add(descriptionField);



        CustomLabel addToUserLabel = new CustomLabel("Assign to project:", font, 25, 263, 250, 23);
        add(addToUserLabel);

        CustomLabel members = new CustomLabel("Member:", font, 385, 263, 110, 23);
        add(members);

        java.util.List<String> userNames = new ArrayList<>();
        for (User u : userManager.getUserDatabase().getAllUsers()) {
            userNames.add(u.getName());
        }

        DropdownField userDropDown = new DropdownField(userNames, 20, 286, 260, 40);
        add(userDropDown);

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
                        listArray[i] = userDropDown.getSelectedItem().toString();
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
                    // Extract data from input fields
                    String name = nameField.getText();
                    String description = descriptionField.getText();

                    // Call the controller method to add the user
                    projectManager.addProject(name,description);
                    Project projectDb= ProjectDatabase.getInstance().getProjectByName(name);
                    String selectedUserName = (String) userDropDown.getSelectedItem();
                    User selectedUser = userManager.findUserByName(selectedUserName);
                    if (selectedUser != null) {
                        // Assign the selected project to the added user
                        ProjectAssignments.getInstance().assignProjectToUser(selectedUser, projectDb);
                    }


                // Assuming the first item is a default selection
                nameField.setText("");
                descriptionField.setText("");
                userDropDown.setSelectedIndex(0);
                addProjectViewEventListener.onPageClosed();

            }
        });
        add(saveBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 515, 510, 100, 40, new Color(214, 64, 69));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                descriptionField.setText("");
                userDropDown.setSelectedIndex(0);
                addProjectViewEventListener.onPageClosed();
            }
        });
        add(cancelBtn);

    }

    public interface AddProjectViewEventListener {
        void onPageClosed();
    }
}
