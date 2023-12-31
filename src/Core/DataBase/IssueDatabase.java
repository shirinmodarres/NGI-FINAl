package Core.DataBase;

import Core.Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class IssueDatabase {
    private static IssueDatabase instance;
    private Database database = Database.getInstance();

    private IssueDatabase() {
    }

    public static IssueDatabase getInstance() {
        if (instance == null) {
            instance = new IssueDatabase();
        }
        return instance;
    }

    public void saveIssue(Issue issue, int projectId) {
        String query = "INSERT INTO `issues` (`title`, `description`, `status`, `createDate`, `updateDate`, `type`, `priority`, `tags`, `project_id`,`user_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, issue.getTitle());
            preparedStatement.setString(2, issue.getDescription());
            preparedStatement.setInt(3, Status.TODO.ordinal()); // Set status as ordinal value of TODO enum
            preparedStatement.setTimestamp(4, new Timestamp(issue.getCreateDate().getTime()));
            preparedStatement.setTimestamp(5, issue.getUpdateDate() != null ? new Timestamp(issue.getUpdateDate().getTime()) : null);
            preparedStatement.setInt(6, issue.getType().ordinal()); // Set type as ordinal value of enum
            preparedStatement.setInt(7, issue.getPriority().ordinal()); // Set priority as ordinal value of enum
            preparedStatement.setString(8, String.join(",", issue.getTags()));
            preparedStatement.setInt(9, issue.getProjectId());
            preparedStatement.setInt(10,issue.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting issue: " + e.getMessage());
        }
    }

    public void removeIssue(int issueId) {
        try {
            String query = "DELETE FROM `issues` WHERE `id` = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setInt(1, issueId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Issue with ID " + issueId + " not found in the database.");
            } else {
                System.out.println("Issue with ID " + issueId + " removed successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error removing issue: " + e.getMessage());
        }
    }

    public void updateIssue(Issue issue) {
        try {
            String query = "UPDATE `issues` SET `title` = ?, `description` = ?,  `createDate` = ?, `updateDate` = ?, `type` = ?, `priority` = ?, `tags` = ?, `project_id` = ? WHERE `id` = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, issue.getTitle());
            statement.setString(2, issue.getDescription());
            statement.setInt(3, issue.getStatus().ordinal());
            statement.setTimestamp(4, new java.sql.Timestamp(issue.getCreateDate().getTime()));
            if (issue.getUpdateDate() != null) {
                statement.setTimestamp(5, new java.sql.Timestamp(issue.getUpdateDate().getTime()));
            } else {
                statement.setTimestamp(5, null);
            }
            statement.setInt(6, issue.getType().ordinal());
            statement.setInt(7, issue.getPriority().ordinal());
            statement.setString(8, String.join(",", issue.getTags()));
            if (issue.getProjectId() != 0) {
                statement.setInt(9, issue.getProjectId());
            } else {
                statement.setNull(9, java.sql.Types.INTEGER);
            }
            statement.setInt(10, issue.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Issue with ID " + issue.getId() + " not found in the database.");
            } else {
                System.out.println("Issue with ID " + issue.getId() + " updated successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating issue: " + e.getMessage());
        }
    }


    public List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();

        try {
            String query = "SELECT i.*, p.* FROM issues i " +
                    "LEFT JOIN project p ON i.project_id = p.`id`" +
                    "LEFT JOIN user u ON i.user_id = u.`id`"; // Join issues with projects table
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int issueId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int statusOrdinal = resultSet.getInt("status");
                Date createDate = resultSet.getDate("createDate");
                Date updateDate = resultSet.getDate("updateDate");
                int typeOrdinal = resultSet.getInt("type");
                int priorityOrdinal = resultSet.getInt("priority");
                String tagsStr = resultSet.getString("tags");
                String[] tags = tagsStr.split(",");

                int userId = resultSet.getInt("user_id");
                int projectId = resultSet.getInt("project_id");
                // Create the Project object for the issue
                Issue issue = new Issue(issueId, title, description, Status.values()[statusOrdinal],
                        Types.values()[typeOrdinal], Priority.values()[priorityOrdinal], new ArrayList<>(Arrays.asList(tags)), projectId,userId);
                issue.setId(issueId);
                issue.setCreateDate(createDate != null ? new Date(createDate.getTime()) : null);
                issue.setUpdateDate(updateDate != null ? new Date(updateDate.getTime()) : null);


                // Set other project fields if needed
                issue.setUserId(userId);
                issue.setProjectId(projectId); // Set the project for the issue
                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or use logger to log the exception
        }

        return issues;
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
    public Map<User, Map<Status, List<Issue>>> groupIssuesByUserAndStatus() {
        Map<User, Map<Status, List<Issue>>> groupedIssues = new HashMap<>();

        try {
            String query = "SELECT i.*, p.*, u.* FROM issues i " +
                    "LEFT JOIN project p ON i.project_id = p.`id`" +
                    "LEFT JOIN user u ON i.user_id = u.`id`"; // Join issues with projects and user tables
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int issueId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int statusOrdinal = resultSet.getInt("status");
                Date createDate = resultSet.getDate("createDate");
                Date updateDate = resultSet.getDate("updateDate");
                int typeOrdinal = resultSet.getInt("type");
                int priorityOrdinal = resultSet.getInt("priority");
                String tagsStr = resultSet.getString("tags");
                String[] tags = tagsStr.split(",");

                int userId = resultSet.getInt("user_id");
                int projectId = resultSet.getInt("project_id");

                User user = new User(userId,
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        convertToRole(resultSet.getInt("role")));

                Issue issue = new Issue(issueId, title, description, Status.values()[statusOrdinal],
                        Types.values()[typeOrdinal], Priority.values()[priorityOrdinal],
                        new ArrayList<>(Arrays.asList(tags)), projectId, userId);
                issue.setId(issueId);
                issue.setCreateDate(createDate != null ? new Date(createDate.getTime()) : null);
                issue.setUpdateDate(updateDate != null ? new Date(updateDate.getTime()) : null);

                // Add the issue to the appropriate user's map
                groupedIssues.computeIfAbsent(user, k -> new HashMap<>())
                        .computeIfAbsent(Status.values()[statusOrdinal], k -> new ArrayList<>())
                        .add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }

        return groupedIssues;
    }



}