package UI.Screen.Issue;

import Core.Manager.IssueManager;
import Core.Model.Issue;
import Core.Model.Project;
import UI.Component.CustomLabel;
import UI.Component.CustomTextField;
import UI.Component.ImageButton;
import UI.Component.RoundedBorder;
import UI.Screen.AddIssue.AddIssueController;
import UI.Screen.AddIssue.AddIssueView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static UI.Component.Colors.getRandomColor;

public class IssueView extends JPanel {
    private final Font textFont = new Font("Calibri", Font.PLAIN, 20);
    private final Font subTextFont = new Font("Calibri", Font.PLAIN, 14);
    private final Font titleFont = new Font("Calibri", Font.PLAIN, 32);
ActionListener addEvent;
    IssueController issueController;
    JPanel issuePanel = new JPanel();
    IssueViewEventListener issueViewEventListener; // Listener for issue click event

    public IssueView(Project project, IssueManager issueManager, IssueViewEventListener issueViewEventListener) {
        this.issueViewEventListener = issueViewEventListener;
        issueController = new IssueController(issueManager);
        addEvent=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                        AddIssueController addIssueController=new AddIssueController();
                        AddIssueView addIssueView=new AddIssueView(issueManager);
                        add(addIssueView);
                        addIssueView.setVisible(true);
                        revalidate();
                        repaint();
                    }
            };



        setLayout(null);
        setVisible(false);
        setBounds(0, 0, 700, 570);
        setBackground(new Color(251, 246, 230));

        CustomLabel title = new CustomLabel("Issues", titleFont, 20, 32, 390, 40);
        ImageButton searchIssueIcon = new ImageButton("img/search.png", 500, 32, 44, 44);
        CustomTextField searchField = new CustomTextField("Search..", 554, 44, 130, 30);

        searchIssueIcon.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    String searchText = searchField.getText().trim();
//                    if (!searchText.isEmpty()) {
//                        issueController.findIssueByTitle(searchText);
//                    }
                }
            }
        });

        JPanel addIssuePlace = createAddIssuePanel(addEvent);
        generateIssuePlacePanels();

        add(addIssuePlace);
        add(searchField);
        add(title);
        add(searchIssueIcon);

        setVisible(true);
    }

    private JPanel createAddIssuePanel(ActionListener addEvent) {
        JPanel addIssuePlace = new JPanel();
        addIssuePlace.setBorder(new RoundedBorder(new Color(147, 191, 207), 6, 3));
        addIssuePlace.setLayout(null);
        addIssuePlace.setVisible(true);
        addIssuePlace.setBackground(new Color(255, 255, 255, 25));
        addIssuePlace.setBounds(20, 103, 130, 107);

        ImageButton addIssueIcon = new ImageButton("img/add25.png", 43, 32, 44, 44);
        addIssueIcon.addActionListener(addEvent);
        addIssuePlace.add(addIssueIcon);

        return addIssuePlace;
    }

    public void generateIssuePlacePanels() {
        int x = 20;
        int y = 103;
        int padding = 70;
        int containerWidth = 700;
        int currentX = x;
        int currentY = y;
        Color color = getRandomColor();

        List<Issue> issues = issueController.getIssueManager().getIssueDatabase().getAllIssues();
        for (int i = 0; i < issues.size(); i++) {
            currentX += 130 + padding;
            if (currentX + 130 + padding > containerWidth) {
                currentX = x;
                currentY += 107 + padding;
            }

            Issue issue = issues.get(i);
            JPanel issuePlace = new JPanel();
            issuePlace.setBorder(new RoundedBorder(color, 6, 3));
            issuePlace.setLayout(null);
            issuePlace.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 60));
            issuePlace.setBounds(currentX, currentY, 130, 107);
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

            CustomLabel date = new CustomLabel(issue.getFormattedDate().toString(), subTextFont, 5, 5, 100, 25);
            CustomLabel title = new CustomLabel(issue.getTitle(), textFont, 10, 30, 110, 25);
            title.setHorizontalAlignment(JLabel.CENTER);

            issuePlace.add(title);
            issuePlace.add(date);

            add(issuePlace);
        }

        int rowCount = (issues.size() + 2) / 3;
        int totalHeight = rowCount * (107 + padding) + y;
        setPreferredSize(new Dimension(containerWidth, totalHeight));
    }

    public interface IssueViewEventListener {
        void onIssueClick(Issue issue);
    }
}
