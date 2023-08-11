package UI.Screen.KanbanBoard;

import Core.Model.Issue;
import Core.Model.Status;
import Core.Model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class KanbanBoard extends JPanel {

    private Map<User, Map<Status, List<Issue>>> kanbanData;

    public KanbanBoard(Map<User, Map<Status, List<Issue>>> kanbanData) {
        this.kanbanData = kanbanData;
        setLayout(new GridLayout(3, kanbanData.size()));
        setVisible(true);
        setBounds(0, 0, 700, 570);
        for (User user : kanbanData.keySet()) {
            add(createUserKanbanPanel(user, kanbanData.get(user)));
        }
    }

    private JPanel createUserKanbanPanel(User user, Map<Status, List<Issue>> statusMap) {
        JPanel userPanel = new JPanel(new GridLayout(statusMap.size(), 1));
        userPanel.setBorder(BorderFactory.createTitledBorder(user.getName()));

        for (Status status : statusMap.keySet()) {
            JPanel statusPanel = createStatusPanel(status, statusMap.get(status));
            userPanel.add(statusPanel);
        }

        return userPanel;
    }

    private JPanel createStatusPanel(Status status, List<Issue> issues) {
        JPanel statusPanel = new JPanel(new GridLayout(issues.size(), 1));
        statusPanel.setBorder(BorderFactory.createTitledBorder(status.toString()));

        for (Issue issue : issues) {
            JLabel issueLabel = new JLabel(issue.getTitle());
            statusPanel.add(issueLabel);
        }

        return statusPanel;
    }


}


