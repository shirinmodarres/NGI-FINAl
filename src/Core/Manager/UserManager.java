package Core.Manager;

import Core.DataBase.UserDatabase;
import Core.Model.User;
import Core.Model.Role;

import java.util.ArrayList;

public class UserManager {
    private static UserManager instance; // Singleton instance
    private ArrayList<User> users;
    private UserDatabase userDatabase;

    private UserManager(UserDatabase userDatabase) {
        this.users = new ArrayList<>();
        this.userDatabase = userDatabase;
    }

    public static UserManager getInstance(UserDatabase userDatabase) {
        if (instance == null) {
            instance = new UserManager(userDatabase);
        }
        return instance;
    }

    public ArrayList<User> addUser(String nickname, String email, String password, Role role) {
        User newUser = new User(-1, nickname, email, password, role);
//        orm.getRepository("User").insert(newUser);
        users.add(newUser);
        userDatabase.addUser(newUser);
        return users;
    }

    public void updateUser(String nickname, String email, String password, Role role) {
        User userToUpdate = findUserByEmail(email);

        System.out.println("user manager");
        if (userToUpdate != null) {
            userToUpdate.setName(nickname);
            userToUpdate.setEmail(email);
            userToUpdate.setPassword(password);
            userToUpdate.setRole(role);
//            orm.getRepository("User").update(userToUpdate);

        }
        userDatabase.editUser(userToUpdate);
        System.out.println(userToUpdate);
    }

//    public void removeUser(int userName) {
//        users.removeIf(user -> user.getName().equals(userName));
//        userDatabase.removeUser(userName);
//    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(getUserDatabase().getUsers()) ;
    }

    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null; // User not found
    }

    public User findUserByName(String email) {

        for (User user : userDatabase.getUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null; // User not found

    }

    public User isValidUser(String email, String password) {
        User user = userDatabase.findUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user; // Valid user
        }

        return null; // Invalid user
    }

    public void removeUser(User user) {
        userDatabase.removeUser(user.getEmail());
    }


    public UserDatabase getUserDatabase() {
        return userDatabase;
    }


}

