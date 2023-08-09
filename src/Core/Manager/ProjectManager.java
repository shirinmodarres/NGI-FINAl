package Core.Manager;

import Core.DataBase.ProjectDatabase;
import Core.Model.Project;
import Core.Model.User;

import java.util.ArrayList;

public class ProjectManager {
    private static ProjectManager instance;
    private static ProjectDatabase projectDatabase;

    public ProjectManager(ProjectDatabase projectDatabase) {
        this.projectDatabase = projectDatabase;
    }
    public static ProjectManager getInstance(ProjectDatabase projectDatabase) {
        if (instance == null) {
            instance = new ProjectManager(projectDatabase);
        }
        return instance;
    }

    public void addProject(String title, String description) {
        Project newProject = new Project(-1, title, description);
        projectDatabase.saveProject(title,description);
    }
//
//    public void updateProject(String title, String description) {
//        Project projectToUpdate = getProjectByTitle(title);
//        if (projectToUpdate != null) {
//            projectToUpdate.setTitle(title);
//            projectToUpdate.setDescription(description);
//            projectDatabase.updateProject(projectToUpdate);
//        }
//    }

    public void removeProject(Project project) {
        projectDatabase.removeProject(project.getId());
    }

    public ArrayList<Project> getAllProjects() {
        return new ArrayList<>(getProjectDatabase().getAllProjects());
    }

    public Project getProjectByTitle(String title) {
        for (Project project : ProjectDatabase.getInstance().getAllProjects()) {
            if (project.getTitle().equals(title)) {
                return project;
            }
        }
        return null;
    }

    public ProjectDatabase getProjectDatabase() {
        return projectDatabase;
    }
}
