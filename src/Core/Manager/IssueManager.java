package Core.Manager;

import Core.DataBase.IssueDatabase;
import Core.Model.Issue;

import java.util.ArrayList;

public class IssueManager {
    private static IssueManager instance; // Singleton instance
    private IssueDatabase issueDatabase;
    private ArrayList<Issue> issues;

    private IssueManager() {
        this.issueDatabase = IssueDatabase.getInstance(); // Utilize the existing singleton instance
        this.issues = new ArrayList<>();
    }

    public static IssueManager getInstance() {
        if (instance == null) {
            instance = new IssueManager();
        }
        return instance;
    }

    public ArrayList<Issue> getAllIssues() {
        return (ArrayList<Issue>) issueDatabase.getAllIssues();
    }

    public void addIssue(Issue issue,int projrctId) {
        issueDatabase.saveIssue(issue,projrctId);
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
