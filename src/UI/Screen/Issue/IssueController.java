package UI.Screen.Issue;

import Core.Manager.IssueManager;
import Core.Manager.ProjectManager;
import Core.Model.Issue;
import Core.Model.Project;

public class IssueController {
    private IssueManager issueManager;

    public IssueController(IssueManager issueManager) {
        this.issueManager = issueManager;
    }

    public Issue findProjectByTitle(String title) {
        return issueManager.getIssueByTitle(title);
    }


    public IssueManager getIssueManager() {
        return issueManager;
    }


}
