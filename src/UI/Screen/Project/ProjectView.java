package UI.Screen.Project;

import Core.Manager.ProjectManager;
import Core.Model.Project;
import UI.Component.*;

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

    public ProjectView(ProjectManager projectManager, ActionListener addEvent, ProjectViewEventListener projectViewEventListener) {
        this.projectViewEventListener = projectViewEventListener;
        projectController = new ProjectController(projectManager);
        setLayout(null);
        setVisible(false);
        setBounds(85, 15, 700, 570);
        setBackground(new Color(251, 246, 230));

        CustomLabel title = new CustomLabel("Projects", titleFont, 20, 32, 390, 40);
        ImageButton searchProjectIcon = new ImageButton("img/search.png", 500, 32, 44, 44);
        CustomTextField searchField = new CustomTextField("Search..", 554, 44, 130, 30);

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

        JPanel addProjectPlace = createAddProjectPanel(addEvent);
        generateProjectPlacePanels();

        add(addProjectPlace);
        add(searchField);
        add(title);
        add(searchProjectIcon);

        setVisible(true);
    }

    private JPanel createAddProjectPanel(ActionListener addEvent) {
        JPanel addProjectPlace = new JPanel();
        addProjectPlace.setBorder(new RoundedBorder(new Color(147, 191, 207), 6, 3));
        addProjectPlace.setLayout(null);
        addProjectPlace.setVisible(true);
        addProjectPlace.setBackground(new Color(255, 255, 255, 25));
        addProjectPlace.setBounds(20, 103, 260, 215);

        ImageButton addProjectIcon = new ImageButton("img/add.png", 105, 82, 44, 44);
        addProjectIcon.addActionListener(addEvent);
        addProjectPlace.add(addProjectIcon);

        return addProjectPlace;
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

            JPanel projectPlace = new JPanel();
            projectPlace.setBorder(new RoundedBorder(color, 6, 3));
            projectPlace.setLayout(null);
            projectPlace.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 60));
            projectPlace.setBounds(currentX, currentY, 260, 215);
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

            CustomLabel date = new CustomLabel(project.getFormattedDate().toString(), subTextFont, 15, 15, 100, 25);
            CustomLabel title = new CustomLabel(project.getTitle(), textFont, 80, 50, 110, 25);
            title.setHorizontalAlignment(JLabel.CENTER);

            projectPlace.add(title);
            projectPlace.add(date);

            add(projectPlace);
        }

        int rowCount = (projectController.getProjectManager().getAllProjects().size() + 2) / 3;
        int totalHeight = rowCount * (212 + padding) + y;
        setPreferredSize(new Dimension(containerWidth, totalHeight));
    }

    public interface ProjectViewEventListener {
        void onProjectClick(Project project);
    }
}
