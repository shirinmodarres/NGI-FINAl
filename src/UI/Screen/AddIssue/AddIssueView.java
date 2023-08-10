package UI.Screen.AddIssue;

import Core.DataBase.UserDatabase;
import Core.Manager.IssueManager;
import Core.Manager.ProjectManager;
import Core.Manager.UserManager;
import Core.Model.*;
import UI.Component.*;
import UI.Screen.AddMember.AddMemberView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddIssueView extends JFrame {
    private AddIssueController addIssueController;

    public AddIssueView(Project project, IssueManager issueManager) {
        addIssueController = new AddIssueController();
        //Setting
        setLayout(null);
        setSize(650, 550);
        setBackground(new Color(251, 246, 230));
        setVisible(true);


        // Set up the panel components
        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Add Issue", titleFont, 20, 10, 390, 35);
        add(title);

        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel name = new CustomLabel("Title:", font, 25, 55, 90, 23);
        add(name);

        CustomTextField nameField = new CustomTextField("Write Your issue's title", 20, 78, 150, 40);
        add(nameField);

        CustomLabel description = new CustomLabel("Description:", font, 330, 55, 100, 23);
        add(description);

        CustomTextArea descriptionField = new CustomTextArea("Description...", 325, 78, 250, 120, new Color(147, 191, 207));
        add(descriptionField);

        CustomLabel priority = new CustomLabel("Priority:", font, 25, 135, 90, 23);
        add(priority);


        List<String> priorityList = new ArrayList<>();
        for (Priority priorities : Priority.values()) {
            priorityList.add(priorities.toString()); // Convert enum value to string
        }
        RadioButtonDrawer radioButtonDrawerPriority = new RadioButtonDrawer(priorityList, 20, 150, 250, 50);
        add(radioButtonDrawerPriority);

        CustomLabel type = new CustomLabel("Types:", font, 325, 210, 90, 23);
        add(type);

        List<String> typeList = new ArrayList<>();
        for (Types types : Types.values()) {
            typeList.add(types.toString()); // Convert enum value to string
        }
        RadioButtonDrawer radioButtonDrawerType = new RadioButtonDrawer(typeList, 320, 225, 250, 40);
        add(radioButtonDrawerType);

        CustomLabel userLabel = new CustomLabel("Members: ", font, 25, 270, 90, 23);
        add(userLabel);

        List<String> userList = new ArrayList<>();
        for (User user : UserManager.getInstance(UserDatabase.getInstance()).getUserDatabase().getAllUsers()) {
            userList.add(user.getName());
        }
        DropdownField users = new DropdownField(userList, 20, 290, 150, 40);
        add(users);

        CustomLabel tag = new CustomLabel("Tags:", font, 325, 270, 90, 23);
        add(tag);

        CustomTextArea tagArea = new CustomTextArea("Tags..", 320, 290, 250, 120, new Color(147, 191, 207));
        add(tagArea);
        // Create a "Save" button to save the issue
        RoundedButton saveButton = new RoundedButton("Save", 150, 430, 100, 30, new Color(96, 150, 180));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an Issue object with entered data
                String issueTitle = nameField.getText();
                String issueDescription = descriptionField.getText();
                String selectedPriority = radioButtonDrawerPriority.getSelectedOption();
                String selectedType = radioButtonDrawerType.getSelectedOption();
                String selectedUser = (String) users.getSelectedItem();
                User updatedUser = UserManager.getInstance(UserDatabase.getInstance()).findUserByName(selectedUser);
                // Assuming you have a method to convert enum strings to enum values
                Priority priority = Priority.valueOf(selectedPriority);
                Types type = Types.valueOf(selectedType);

                String descriptionText = tagArea.getText();

                // Split the text using comma as a delimiter
                String[] words = descriptionText.split(",");

                // Create an ArrayList to hold the words
                ArrayList<String> wordList = new ArrayList<>();

                // Trim each word and add it to the ArrayList
                for (String word : words) {
                    wordList.add(word.trim());
                }

                addIssueController.addIssue(issueTitle, issueDescription, Status.TODO, type, priority, wordList, project.getId(),updatedUser.getId());

                dispose();

            }
        });
        add(saveButton);

        // Create a "Cancel" button to cancel adding an issue
        RoundedButton cancelButton = new RoundedButton("Cancel", 260, 430, 100, 30, new Color(214, 64, 69));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the "Add Issue" view without saving
                dispose();

            }
        });
        add(cancelButton);

    }

//    public interface AddIssueViewEventListener {
//        void onPageClosed();
//
//    }
}



