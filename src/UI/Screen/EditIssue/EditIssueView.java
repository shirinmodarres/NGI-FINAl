package UI.Screen.EditIssue;

import Core.DataBase.UserDatabase;
import Core.Manager.IssueManager;
import Core.Manager.UserManager;
import Core.Model.*;
import UI.Component.*;
import UI.Screen.EditMember.EditMemberView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditIssueView extends JPanel {
    Issue initialIssue;
    public EditIssueView(Project project ,Issue issue, IssueManager issueManager,EditIssueViewEventListener editIssueViewEventListener) {
       initialIssue=issue;
        //Setting
        setLayout(null);
        setBounds(0, 0, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(false);

        //setup component
        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Add Issue", titleFont, 20, 10, 390, 35);
        add(title);

        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel name = new CustomLabel("Title:", font, 25, 55, 90, 23);
        add(name);

        CustomTextField nameField = new CustomTextField(issue.getTitle(), 20, 78, 150, 40);
        add(nameField);

        CustomLabel description = new CustomLabel("Description:", font, 330, 55, 100, 23);
        add(description);

        CustomTextArea descriptionField = new CustomTextArea(issue.getDescription(), 325, 78, 250, 120, new Color(147, 191, 207));
        add(descriptionField);

        CustomLabel priority = new CustomLabel("Priority:", font, 25, 135, 90, 23);
        add(priority);


        java.util.List<String> priorityList = new ArrayList<>();
        for (Priority priorities : Priority.values()) {
            priorityList.add(priorities.name()); // Convert enum value to string
        }
        RadioButtonDrawer radioButtonDrawerPriority = new RadioButtonDrawer(priorityList, 20, 150, 250, 50);
        add(radioButtonDrawerPriority);

        CustomLabel type = new CustomLabel("Types:", font, 325, 210, 90, 23);
        add(type);

        List<String> typeList = new ArrayList<>();
        for (Types types : Types.values()) {
            typeList.add(types.name()); // Convert enum value to string
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

        CustomTextArea tagArea = new CustomTextArea(issue.getTags().toString(), 320, 290, 250, 120, new Color(147, 191, 207));
        add(tagArea);
        RoundedButton saveBtn = new RoundedButton("Save", 400, 510, 100, 40, new Color(96, 150, 180));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = nameField.getText();
                String description = descriptionField.getText();
                Priority selectedPriority = Priority.valueOf(radioButtonDrawerPriority.getSelectedValue().toUpperCase());
                Types selectedType = Types.valueOf(radioButtonDrawerType.getSelectedValue().toUpperCase());
                ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagArea.getText().split(",")));

                String selectedUser = (String) users.getSelectedItem();
                User updatedUser = UserManager.getInstance(UserDatabase.getInstance()).findUserByName(selectedUser);
                Issue updatedIssue = new Issue(initialIssue.getId(), title, description,Status.TODO, selectedType,selectedPriority,tags,project.getId(),updatedUser.getId());
                issueManager.getIssueDatabase().updateIssue(updatedIssue);

                // Clear input fields
                nameField.setText("");
                descriptionField.setText("");
                tagArea.setText("");

                // Notify the event listener that the page is closed
                editIssueViewEventListener.PageClosed(initialIssue);}
        });
        add(saveBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 515, 510, 100, 40, new Color(214, 64, 69));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editIssueViewEventListener.PageClosed(initialIssue);
            }
        });
        add(cancelBtn);

    }

    public interface EditIssueViewEventListener {
        void PageClosed(Issue issue);
    }
}
