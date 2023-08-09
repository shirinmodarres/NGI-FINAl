package UI.Screen.ProjectInfo;

import Core.DataBase.UserDatabase;
import Core.Model.Project;
import Core.Model.User;
import UI.Component.CustomLabel;
import UI.Component.ImageButton;

import javax.swing.*;
import java.awt.*;

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
//        addIcon.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Show a dialog to select a user
//                List<User> allUsers = userDatabase.getAllUsers(); // Get all users from UserDatabase
//                String[] userNames = new String[allUsers.size()];
//
//                for (int i = 0; i < allUsers.size(); i++) {
//                    userNames[i] = allUsers.get(i).getName();
//                }
//
//                String selectedUserName = (String) JOptionPane.showInputDialog(
//                        null,
//                        "Select a user:",
//                        "Add User to Project",
//                        JOptionPane.PLAIN_MESSAGE,
//                        null,
//                        userNames,
//                        null
//                );
//
//                if (selectedUserName != null) {
//                    // Call projectAssignments.assignProjectToUser(...) with the selected user
//                    projectAssignments.assignProjectToUser(selectedUserName, project.getTitle().toString());
//                    // Redraw the circles to reflect the updated members
//                    repaint();
//                }
//            }
//        });

//        List<User> projectMembers = projectAssignments.getMembersForProject(project.getTitle().toString());
//        for (int i = 0; i < projectMembers.size(); i++) {
//            drawCircle(projectMembers.get(i), i); // Draw circle for each member
//        }
    }

    private void drawCircle(User member, int index) {
        Graphics2D g2d = (Graphics2D) getGraphics();

        int radius = 20; // Adjust the radius as needed
        int padding = 10; // Adjust the padding between circles

        int x = 70 + (radius * 2 + padding) * index; // Calculate X coordinate with padding
        int y = 300; // Replace with the actual Y coordinate

        g2d.setColor(Color.GRAY);
        g2d.fillOval(x, y, radius * 2, radius * 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
