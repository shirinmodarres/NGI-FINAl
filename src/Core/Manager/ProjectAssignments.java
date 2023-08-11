package Core.Manager;

import Core.DataBase.AssignUserProjectRepository;
import Core.Model.Project;
import Core.Model.User;


import java.util.*;

public class ProjectAssignments {
    private static ProjectAssignments instance;

    private ProjectAssignments() {
    }

    public static ProjectAssignments getInstance() {
        if (instance == null) {
            instance = new ProjectAssignments();
        }
        return instance;
    }

    public void assignProjectToUser(User user, Project project) {
        AssignUserProjectRepository.getInstance().assignProjectToUser(user, project);
    }

    public void unassignProjectFromUser(User user, Project project) {
        AssignUserProjectRepository.getInstance().unassignProjectFromUser(user, project);

    }

    public List<Project> getAssignedProjectsForUser(User user) {
        return AssignUserProjectRepository.getInstance().getAssignedProjectsForUser(user);
    }

    public List<User> getMembersForProject(Project project) {
        return AssignUserProjectRepository.getInstance().getMembersForProject(project);

    }


}
