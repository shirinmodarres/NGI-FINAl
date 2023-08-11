package Core.DataBase;

import Core.Model.Project;
import Core.Model.Role;
import Core.Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignUserProjectRepository {
    private static AssignUserProjectRepository instance;

    private final Database dataBase = Database.getInstance();

    private AssignUserProjectRepository() {
    }

    public static AssignUserProjectRepository getInstance() {
        if (instance == null) {
            instance = new AssignUserProjectRepository();
        }
        return instance;
    }

    private Role convertToRole(int roleInt) {
        switch (roleInt) {
            case 0:
                return Role.SUPER_ADMIN;
            case 1:
                return Role.PO;
            case 2:
                return Role.DEVELOPER;
            case 3:
                return Role.TESTER;
            default:
                throw new IllegalArgumentException("Invalid role value: " + roleInt);
        }
    }

    private Project retrieveProjectById(int projectId) {
        try {
            String query = "SELECT * FROM project WHERE id = ?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String projectName = resultSet.getString("title");
                String description = resultSet.getString("description");
                return new Project(projectId, projectName, description); // Create a Project object using retrieved data
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private User retrieveUserById(int userId) {
        try {
            String query = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int roleInt = resultSet.getInt("role");
                Role role = convertToRole(roleInt); // Convert the integer value to the corresponding Role enum

                return new User(userId, name, email, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getUsersByProject(int id) {
        try {
            String query = "SELECT user.* FROM project_user LEFT JOIN user ON user.id = project_user.user_id WHERE project_user.project_id = ?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("User with email '" + id + "' not found in the database.");
            } else {
                System.out.println("User with email '" + id + "' removed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Insert a new assignment
    public void assignProjectToUser(User user, Project project) {
        try {
            String query = "INSERT INTO user_project (user_id, project_id) VALUES (?, ?)";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, project.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an assignment
    public void unassignProjectFromUser(User user, Project project) {
        try {
            String query = "DELETE FROM user_project WHERE user_id = ? AND project_id = ?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, project.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Assignment not found.");
            } else {
                System.out.println("Assignment removed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get assigned projects for a user
    public List<Project> getAssignedProjectsForUser(User user) {
        List<Project> assignedProjects = new ArrayList<>();

        try {
            String query = "SELECT project_id FROM user_project WHERE user_id = ?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int projectId = resultSet.getInt("project_id");
                Project project = retrieveProjectById(projectId);
                if (project != null) {
                    assignedProjects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignedProjects;
    }

    // Get members for a project
    public List<User> getMembersForProject(Project project) {
        List<User> members = new ArrayList<>();

        try {
            String query = "SELECT user_id FROM user_project WHERE project_id = ?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setInt(1, project.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                User user = retrieveUserById(userId);
                if (user != null) {
                    members.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }
}
