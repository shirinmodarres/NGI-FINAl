package UI.Screen.Issue;

import Core.DataBase.IssueDatabase;
import Core.DataBase.UserDatabase;
import Core.Manager.IssueManager;
import Core.Manager.UserManager;
import Core.Model.Issue;
import Core.Model.Project;
import Core.Model.User;
import UI.Component.CustomLabel;
import UI.Component.CustomTextField;
import UI.Component.ImageButton;
import UI.Component.RoundedBorder;
import UI.Screen.AddIssue.AddIssueController;
import UI.Screen.AddIssue.AddIssueView;
import UI.Screen.EditIssue.EditIssueView;
import UI.Screen.EditMember.EditMemberView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static UI.Component.Colors.getRandomColor;

public class IssueView extends JPanel {
    private final Font textFont = new Font("Calibri", Font.PLAIN, 20);
    private final Font subTextFont = new Font("Calibri", Font.PLAIN, 14);
    private final Font titleFont = new Font("Calibri", Font.PLAIN, 32);
    IssueController issueController;
    JPanel issuePanel = new JPanel();
    IssueViewEventListener issueViewEventListener; // Listener for issue click event
EditIssueView editIssueView;
    public IssueView(Project project, IssueManager issueManager, ActionListener addIssueEvent, IssueViewEventListener issueViewEventListener) {
        this.issueViewEventListener = issueViewEventListener;
        issueController = new IssueController(issueManager);

        //Setting
        setLayout(null);
        setVisible(false);
        setBounds(0, 0, 700, 570);
        setBackground(new Color(251, 246, 230));

        //Header
        CustomLabel title = new CustomLabel("Issues", titleFont, 20, 32, 390, 40);
        add(title);

        ImageButton searchIssueIcon = new ImageButton("img/search.png", 500, 32, 44, 44);
        add(searchIssueIcon);
        CustomTextField searchField = new CustomTextField("Search..", 554, 44, 130, 30);
        add(searchField);

//        searchIssueIcon.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    String searchText = searchField.getText().trim();
//                    if (!searchText.isEmpty()) {
//                        issueManager.getIssueDatabase().(searchText);
//                    }
//                }
//            }
//        });


        JPanel addIssuePlace = createAddIssuePanel(addIssueEvent);
        generateIssuePlacePanels(project);

        add(addIssuePlace);

        setVisible(true);
    }

    private JPanel createAddIssuePanel(ActionListener addIssueEvent) {

        //Setting
        JPanel addIssuePlace = new JPanel();
        addIssuePlace.setBorder(new RoundedBorder(new Color(147, 191, 207), 6, 3));
        addIssuePlace.setLayout(null);
        addIssuePlace.setVisible(true);
        addIssuePlace.setBackground(new Color(255, 255, 255, 25));
        addIssuePlace.setBounds(20, 103, 170, 107);

        ImageButton addIssueIcon = new ImageButton("img/add25.png", 65, 42, 25, 25);
        addIssueIcon.addActionListener(addIssueEvent);
        addIssuePlace.add(addIssueIcon);

        return addIssuePlace;
    }

    public void generateIssuePlacePanels(Project project) {
        int x = 20;
        int y = 103;
        int padding = 40;
        int containerWidth = 700;
        int currentX = x;
        int currentY = y;


        for (Issue issue : issueController.getIssueManager().getIssueDatabase().getAllIssues()) {
            Color color = getRandomColor();
            currentX += 170 + padding;
            if (currentX + 170 + padding > containerWidth) {
                currentX = x;
                currentY += 107 + padding;
            }
            generateIssue(project,issue, currentX, currentY, padding, containerWidth, color);
        }

    }

    public void generateIssue(Project project,Issue issue, int x, int y, int padding, int containerWidth, Color color) {

        //setUp
        JPanel issuePlace = new JPanel();
        issuePlace.setBorder(new RoundedBorder(color, 6, 3));
        issuePlace.setLayout(null);
        issuePlace.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 60));
        issuePlace.setBounds(x, y, 170, 107);
        issuePlace.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Check for single-click
                    if (issueViewEventListener != null) {
                        issueViewEventListener.onIssueClick(issue);
                        System.out.println(issue.getTitle());

                    }
                }
            }
        });
        add(issuePlace);

        ImageButton removeBtn = new ImageButton("img/remove15.png", 110, 5, 15, 15);
        removeBtn.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove this issue?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION
            );
            if (dialogResult == JOptionPane.YES_OPTION) {
                JPanel memberselectedPlace = (JPanel) removeBtn.getParent();
                issueController.getIssueManager().getIssueDatabase().removeIssue(issue.getId());
                issuePanel.remove(memberselectedPlace);
                issuePanel.remove(memberselectedPlace.getParent());
                issuePlace.setVisible(false);
//                    memberPanel.repaint();
//                    generateMemberPlacePanels();
//                    memberPanel.getParent().repaint();
//                    memberPanel.getParent().revalidate();
            }
        });
        issuePlace.add(removeBtn);
        ImageButton editBtn = new ImageButton("img/edit15.png", 145, 5, 15, 15);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editIssueView = new EditIssueView(project,issue, IssueManager.getInstance(IssueDatabase.getInstance()), new EditIssueView.EditIssueViewEventListener() {
                    @Override
                    public void PageClosed(Issue updatedIssue) {
                        issuePlace.removeAll();
                        generateIssue(project,updatedIssue, x, y, padding, containerWidth,color);
                        editIssueView.setVisible(false);
                    }
                });
                add(editIssueView, 0);
                revalidate();
                repaint();
            }
        });
        issuePlace.add(editBtn);

        CustomLabel date = new CustomLabel(issue.getFormattedDate().toString(), subTextFont, 5, 5, 100, 25);
        issuePlace.add(date);

        CustomLabel title = new CustomLabel(issue.getTitle(), textFont, 5, 20, 160, 25);
        title.setHorizontalAlignment(JLabel.CENTER);
        issuePlace.add(title);

        CustomLabel status = new CustomLabel(issue.getStatus().toString(), subTextFont, 5, 70, 160, 25);
        status.setHorizontalAlignment(JLabel.CENTER);
        issuePlace.add(status);

        int rowCount = (issueController.getIssueManager().getIssueDatabase().getAllIssues().size() + 2) / 3;
        int totalHeight = rowCount * (107 + padding) + y;

        setPreferredSize(new Dimension(containerWidth, totalHeight));

    }

    public interface IssueViewEventListener {
        void onIssueClick(Issue issue);
    }


}


