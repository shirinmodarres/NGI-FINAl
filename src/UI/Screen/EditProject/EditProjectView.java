package UI.Screen.EditProject;

import Core.Manager.ProjectManager;
import Core.Model.Project;
import UI.Component.CustomLabel;
import UI.Component.CustomTextArea;
import UI.Component.CustomTextField;
import UI.Component.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProjectView extends JPanel {
    Project initialProject;

    public EditProjectView(Project project, ProjectManager projectManager, EditProjectViewEventListener addProjectViewEventListener) {
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

        CustomLabel description = new CustomLabel("Description:", font, 25, 320, 110, 23);
        add(description);

        CustomTextArea descriptionField = new CustomTextArea(project.getDescription(), 20, 350, 390, 100, new Color(147, 191, 207));
        add(descriptionField);

        CustomLabel members = new CustomLabel("Member:", font, 25, 160, 110, 23);
        add(members);

        RoundedButton saveBtn = new RoundedButton("Save", 400, 510, 100, 40, new Color(96, 150, 180));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Extract data from input fields
                String projectName = nameField.getText();
                String description = descriptionField.getText();

                Project updateProject = new Project(project.getId(),projectName , description);
                projectManager.getProjectDatabase().updateProject(updateProject);
                nameField.setText("");
                descriptionField.setText("");
                addProjectViewEventListener.onPageClosed(updateProject);
            }
        });
        add(saveBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 515, 510, 100, 40, new Color(214, 64, 69));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                descriptionField.setText("");
                addProjectViewEventListener.onPageClosed(initialProject);
            }
        });
        add(cancelBtn);
    }

    public interface EditProjectViewEventListener {
        void onPageClosed(Project project);
    }

}
