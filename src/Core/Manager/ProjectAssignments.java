package Core.Manager;

import Core.Model.Project;
import Core.Model.User;


import java.util.*;

public class ProjectAssignments {

    private Map<User, List<Project>> userToProjectsMap;

    public ProjectAssignments() {
        userToProjectsMap = new HashMap<>();
    }

    public void assignProjectToUser(User user, Project project) {
        userToProjectsMap.computeIfAbsent(user, k -> new ArrayList<>()).add(project);
    }

    public void unassignProjectFromUser(User user, Project project) {
        List<Project> projects = userToProjectsMap.get(user);
        if (projects != null) {
            projects.remove(project);
            if (projects.isEmpty()) {
                userToProjectsMap.remove(user);
            }
        }
    }

    public List<Project> getAssignedProjectsForUser(User user) {
        return userToProjectsMap.getOrDefault(user, new ArrayList<>());
    }

    public List<User> getMembersForProject(Project project) {
        List<User> members = new ArrayList<>();
        for (Map.Entry<User, List<Project>> entry : userToProjectsMap.entrySet()) {
            if (entry.getValue().contains(project)) {
                members.add(entry.getKey());
            }
        }
        return members;
    }

    public Map<User, List<Project>> getAllAssignments() {
        return userToProjectsMap;
    }

    // Conversion methods


}
