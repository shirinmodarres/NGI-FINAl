package UI.Screen.ProjectInfo;

import Core.DataBase.AssignUserProjectRepository;
import Core.DataBase.UserDatabase;
import Core.Model.Project;
import Core.Model.User;
import UI.Component.CircleList;
import UI.Component.CustomLabel;
import UI.Component.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProjectInfoView extends JPanel {
    private final Font textFont = new Font("Calibri", Font.PLAIN, 20);
    private final Font subTextFont = new Font("Calibri", Font.PLAIN, 14);
    private final Font titleFont = new Font("Calibri", Font.PLAIN, 32);

    private Project project;
//    private ProjectAssignments projectAssignments;
    private UserDatabase userDatabase;

    public ProjectInfoView(Project project) {
        this.project = project;
//        this.projectAssignments = projectAssignments;

        setBackground(new Color(251, 246, 230));
        setLayout(null);
        setVisible(false);
        setBounds(50, 80, 500, 400);

        CustomLabel title = new CustomLabel(project.getTitle(), titleFont, 70, 30, 120, 45);
        add(title);

        CustomLabel description = new CustomLabel("Description: " + project.getDescription(), textFont, 70, 25, 300, 200);
        add(description);

        CustomLabel membersLabel = new CustomLabel("Members:", textFont, 70, 220, 120, 45);
        add(membersLabel);

        ImageButton addIcon = new ImageButton("img/add25.png", 645, 235, 25, 25);
        add(addIcon);


        ArrayList<User> allMembers = (ArrayList<User>) AssignUserProjectRepository.getInstance().getMembersForProject(project); // Retrieve all available users

        ArrayList<String> memberNames = new ArrayList<>();
        for (User member : allMembers) {
            memberNames.add(member.getName());

        }

        CircleList circleListPanel = new CircleList(memberNames, 50, 10, 10);
        circleListPanel.setBounds(70, 250, 500, 100);
        add(circleListPanel);
        JScrollPane usersCircleScrollPane = circleListPanel.getScrollPane();

        usersCircleScrollPane.setBounds(70, 250, 500, 100);
        add(usersCircleScrollPane);



    }

 }
