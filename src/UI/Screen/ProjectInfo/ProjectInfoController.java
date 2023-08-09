package UI.Screen.ProjectInfo;

import Core.Manager.ProjectAssignments;

public class ProjectInfoController {
    private ProjectAssignments projectAssignments;

    public ProjectInfoController(ProjectAssignments projectAssignments) {

        this.projectAssignments = projectAssignments;
    }

    public ProjectAssignments getProjectAssignments() {
        return projectAssignments;
    }
}
