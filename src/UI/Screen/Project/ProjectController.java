package UI.Screen.Project;

import Core.Manager.ProjectManager;
import Core.Model.Project;

public class ProjectController {
    private ProjectManager projectManager;

    public ProjectController(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public Project findProjectByTitle(String title) {
        return projectManager.getProjectByTitle(title);
    }

    public void addProject(String title, String description) {
        projectManager.addProject(title, description);
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }
}
