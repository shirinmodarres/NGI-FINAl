package UI.Screen.Project;

import Core.DataBase.ProjectDatabase;
import Core.Manager.ProjectAssignments;
import Core.Manager.ProjectManager;
import Core.Model.Project;

import UI.Component.*;
import UI.Screen.EditProject.EditProjectView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static UI.Component.Colors.getRandomColor;

public class ProjectView extends JPanel {
    private final Font textFont = new Font("Calibri", Font.PLAIN, 20);
    private final Font subTextFont = new Font("Calibri", Font.PLAIN, 14);
    private final Font titleFont = new Font("Calibri", Font.PLAIN, 32);

    ProjectController projectController;
    JPanel projectPanel = new JPanel();
    ProjectViewEventListener projectViewEventListener; // Listener for project click event
    EditProjectView editProjectView;

    public ProjectView(ProjectManager projectManager, ActionListener addEvent, ProjectViewEventListener projectViewEventListener) {
        this.projectViewEventListener = projectViewEventListener;

        projectController = new ProjectController(projectManager);

        //Setting
        setLayout(null);
        setVisible(false);
        setBounds(85, 15, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(true);

        //Header
        CustomLabel title = new CustomLabel("Projects", titleFont, 20, 32, 390, 40);
        add(title);

        CustomTextField searchField = new CustomTextField("Search..", 554, 44, 130, 30);
        add(searchField);

        ImageButton searchProjectIcon = new ImageButton("img/search.png", 500, 32, 44, 44);
        searchProjectIcon.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = searchField.getText().trim();
                    if (!searchText.isEmpty()) {
                        projectController.findProjectByTitle(searchText);
                    }
                }
            }
        });
        add(searchProjectIcon);

        //Add project card
        JPanel addProjectPlace = createAddProjectPanel(addEvent);
        add(addProjectPlace);

        generateProjectPlacePanels();
    }

    private JPanel createAddProjectPanel(ActionListener addProjectEvent) {

        //Setting
        JPanel addProjectPlace = new JPanel();
        addProjectPlace.setBorder(new RoundedBorder(new Color(147, 191, 207), 6, 3));
        addProjectPlace.setLayout(null);
        addProjectPlace.setVisible(true);
        addProjectPlace.setBackground(new Color(255, 255, 255, 25));
        addProjectPlace.setBounds(20, 103, 260, 215);

        ImageButton addProjectIcon = new ImageButton("img/add.png", 105, 82, 44, 44);
        addProjectIcon.addActionListener(addProjectEvent);
        addProjectPlace.add(addProjectIcon);

        return addProjectPlace;
    }

    private void removeProject(Project project) {
        projectController.getProjectManager().removeProject(project);
        generateProjectPlacePanels();// Call the removeUser method in your manager
        revalidate();
        repaint();
    }

    public void generateProjectPlacePanels() {
        int x = 20;
        int y = 103;
        int padding = 70;
        int containerWidth = 700;
        int currentX = x;
        int currentY = y;
        Color color = getRandomColor();

        for (Project project : projectController.getProjectManager().getProjectDatabase().getAllProjects()) {
            currentX += 260 + padding;
            if (currentX + 260 + padding > containerWidth) {
                currentX = x;
                currentY += 215 + padding;
            }
            generateProject(project,currentX,currentY,padding,containerWidth,color);

        }

    }
    public void generateProject(Project project,int x, int y,int padding,int containerWidth,Color color){
        JPanel projectPlace = new JPanel();
        projectPlace.setBorder(new RoundedBorder(color, 6, 3));
        projectPlace.setLayout(null);
        projectPlace.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 60));
        projectPlace.setBounds(x, y, 260, 215);
        projectPlace.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Check for single-click
                    if (projectViewEventListener != null) {
                        projectViewEventListener.onProjectClick(project);
                        System.out.println(project.getTitle());

                    }
                }
            }
        });
        add(projectPlace);

        ImageButton removeBtn = new ImageButton("img/remove.png", 200, 10, 25, 25);
        removeBtn.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove this user?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION
            );
            if (dialogResult == JOptionPane.YES_OPTION) {
                JPanel memberselectedPlace = (JPanel) removeBtn.getParent();
                removeProject(project);
                projectPanel.remove(memberselectedPlace);
                projectPanel.remove(memberselectedPlace.getParent());
                projectPlace.setVisible(false);
//                    memberPanel.repaint();
//                    generateMemberPlacePanels();
//                    memberPanel.getParent().repaint();
//                    memberPanel.getParent().revalidate();
            }
        });
        projectPlace.add(removeBtn);
        ImageButton editBtn = new ImageButton("img/edit.png", 230, 10, 25, 25);
        editBtn.addActionListener(e -> {
            editProjectView = new EditProjectView(project, ProjectManager.getInstance(ProjectDatabase.getInstance()), updatedProject -> {
                projectPlace.removeAll();
                generateProject(updatedProject, x, y, padding, containerWidth,color);
                editProjectView.setVisible(false);
            });
            add(editProjectView, 0);
            revalidate();
            repaint();
        });
        projectPlace.add(editBtn);

        CustomLabel date = new CustomLabel(project.getFormattedDate(), subTextFont, 15, 15, 100, 25);
        projectPlace.add(date);

        CustomLabel title = new CustomLabel(project.getTitle(), textFont, 80, 50, 110, 25);
        projectPlace.add(title);
        title.setHorizontalAlignment(JLabel.CENTER);
        CustomLabel member=new CustomLabel( "All Members: "+ProjectAssignments.getInstance().getMembersForProject(project).size(),subTextFont,20,130,200,25);
        projectPlace.add(member);

        int rowCount = (projectController.getProjectManager().getAllProjects().size() + 2) / 3;
        int totalHeight = rowCount * (212 + padding) + y;
        setPreferredSize(new Dimension(containerWidth, totalHeight));

    }
    public interface ProjectViewEventListener {
        void onProjectClick(Project project);
    }

}


