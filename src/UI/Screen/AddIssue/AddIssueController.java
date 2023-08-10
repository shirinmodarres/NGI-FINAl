package UI.Screen.AddIssue;

import Core.DataBase.IssueDatabase;
import Core.Manager.IssueManager;
import Core.Model.Issue;
import Core.Model.Priority;
import Core.Model.Status;
import Core.Model.Types;

import java.util.ArrayList;


public class AddIssueController {
    private IssueManager issueManager;

    public AddIssueController() {
        this.issueManager = IssueManager.getInstance(IssueDatabase.getInstance());
    }

    public Issue addIssue(String title, String description, Status status,
                          Types types, Priority priority, ArrayList<String> tags, int projectId,int userId) {
        Issue issue = new Issue(-1,title, description, status, types, priority, tags,projectId,userId);
        issueManager.addIssue(issue,projectId);
        return issue;
    }

}
