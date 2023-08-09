package Core.Manager;

import Core.DataBase.ProjectDatabase;
import Core.Model.Project;

import java.util.ArrayList;

public class ProjectManager {
    private ArrayList<Project> projects;
    private ProjectDatabase projectDatabase;

    public ProjectManager(ProjectDatabase projectDatabase) {
        this.projects = new ArrayList<>();
        this.projectDatabase = projectDatabase;
    }

    public void addProject(String title, String description) {
        Project newProject = new Project( title, description);
        projects.add(newProject);
        projectDatabase.saveProject(title,description);
    }

    public void updateProject(String title, String description) {
        Project projectToUpdate = getProjectByTitle(title);
        if (projectToUpdate != null) {
            projectToUpdate.setTitle(title);
            projectToUpdate.setDescription(description);
            projectDatabase.updateProject(projectToUpdate);
        }
    }

//    public void removeProject(String title) {
//        projects.removeIf(project -> project.getTitle().equals(title));
//        projectDatabase.removeProject(title);
//    }

    public ArrayList<Project> getAllProjects() {
        return new ArrayList<>(projects);
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
