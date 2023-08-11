package Core.Manager;

import Core.DataBase.IssueDatabase;
import Core.Model.Issue;
import Core.Model.Status;
import Core.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueManager {
    private static IssueManager instance; // Singleton instance
    private IssueDatabase issueDatabase;
    private ArrayList<Issue> issues;

    private IssueManager() {
        this.issueDatabase = IssueDatabase.getInstance(); // Utilize the existing singleton instance
        this.issues = new ArrayList<>();
    }

    public static IssueManager getInstance(IssueDatabase instance) {
        if (IssueManager.instance == null) {
            IssueManager.instance = new IssueManager();
        }
        return IssueManager.instance;
    }

    public ArrayList<Issue> getAllIssues() {
        return (ArrayList<Issue>) issueDatabase.getAllIssues();
    }

    public void addIssue(Issue issue,int projectId) {
        issueDatabase.saveIssue(issue,projectId);
    }

    public void editIssue( Issue editedIssue) {
        issueDatabase.updateIssue( editedIssue);
    }

    public void removeIssue(int index) {
        issueDatabase.removeIssue(index);
    }

    public Issue getIssueByTitle(String title) {
        for (Issue issue : issues) {
            if (issue.getTitle().equals(title)) {
                return issue;
            }
        }
        return null;
    }

    public IssueDatabase getIssueDatabase() {
        return issueDatabase;
    }

}
