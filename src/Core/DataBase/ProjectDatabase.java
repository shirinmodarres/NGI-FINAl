package Core.DataBase;


import Core.Model.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDatabase {
    private static ProjectDatabase instance;
private Database database=Database.getInstance();
    private ProjectDatabase() {
    }

    public static ProjectDatabase getInstance() {
        if (instance == null) {
            instance = new ProjectDatabase();
        }
        return instance;
    }

    public void saveProject(String title, String description) {
        String query = "INSERT INTO `project` (`title`, `description`) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
            System.out.println("Project added successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error adding project: " + e.getMessage());
        }
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();

        try {
            String query = "SELECT * FROM project";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int projectId = resultSet.getInt("id-project");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                Project project = new Project(title, description);
                project.setId(projectId);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or use logger to log the exception
        }

        return projects;
    }

    public Project getProjectByName(String projectName) {
        try {
            String query = "SELECT * FROM project WHERE title = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int projectId = resultSet.getInt("id-project");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                Project project = new Project(title, description);
                project.setId(projectId);
                return project;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or use logger to log the exception
        }

        return null; // Project not found with the given name
    }
    public void updateProject(Project project) {
        try {
            String query = "UPDATE `ngi-final`.`project` SET `title` = ?, `description` = ? WHERE `id-project` = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, project.getTitle());
            statement.setString(2, project.getDescription());
            statement.setInt(3, project.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Project with ID " + project.getId() + " not found in the database.");
            } else {
                System.out.println("Project with ID " + project.getId() + " updated successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating project: " + e.getMessage());
        }
    }

    public void assignUserToProject(int userId, int projectId) {
        try {
            String query = "INSERT INTO `ngi-final`.`user_project` (`user_id`, `project_id`) VALUES (?, ?);";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, projectId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error assigning user to project: " + e.getMessage());
        }
    }
}
