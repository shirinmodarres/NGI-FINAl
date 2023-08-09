package UI.Screen.AddProject;

import Core.Manager.ProjectManager;
import Core.Model.Project;
import Core.Model.User;


public class AddProjectController {

    private final ProjectManager projectManager;

    public AddProjectController(ProjectManager projectManager) {
        this.projectManager = projectManager;

    }

    public void addProject(String title, String description) {
        projectManager.addProject(title, description);
    }



}


