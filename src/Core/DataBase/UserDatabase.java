package Core.DataBase;

import Core.Model.Project;
import Core.Model.Role;
import Core.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static UserDatabase instance;

    private final Database dataBase = Database.getInstance();

    private UserDatabase() {
    }

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }
//TODO why use prepared statement
    public User addUser(User user) {
        try {
            String query = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getRole().ordinal());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public void editUser(User user) {
        try {
            String query = "UPDATE user SET name=?, password=?, role=?, email=? WHERE id=?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().ordinal());
            statement.setString(4, user.getEmail());
            statement.setInt(5,user.getId());


            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("User with email '" + user.getEmail() + "' not found in the database.");
            } else {
                System.out.println("User with email '" + user.getEmail() + "' updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            String query = "SELECT * FROM user";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int roleInt = resultSet.getInt("role");
                Role role = convertToRole(roleInt); // Convert the integer value to the corresponding Role enum

                int userId = resultSet.getInt("id");
                User user = new User(userId,name, email, password, role);
                user.setId(userId);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean authenticate(String email, String password) {
        try {
            String query = "SELECT * FROM user WHERE email=? AND password=?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User findUserByEmail(String email) {
        try {
            String query = "SELECT * FROM user WHERE email=?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                int roleInt = resultSet.getInt("role");
                Role role = convertToRole(roleInt); // Convert the integer value to the corresponding Role enum

                return new User(id, name, email, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public User findUserByName(String name) {
        try {
            String query = "SELECT * FROM user WHERE name=?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int roleInt = resultSet.getInt("role");
                Role role = convertToRole(roleInt); // Convert the integer value to the corresponding Role enum

                return new User(id, name, email, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isDatabaseEmpty() {
        try {
            String query = "SELECT COUNT(*) AS count FROM user";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void removeUser(String email) {
        try {
            String query = "DELETE FROM user WHERE email=?";
            PreparedStatement statement = dataBase.getConnection().prepareStatement(query);
            statement.setString(1, email);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("User with email '" + email + "' not found in the database.");
            } else {
                System.out.println("User with email '" + email + "' removed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
