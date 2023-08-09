package UI.Screen.AddMember;

import Core.Manager.UserManager;
import Core.Model.Role;

public class AddMemberController {
    private UserManager userManager;

    public AddMemberController(UserManager userManager) {
        this.userManager = userManager;
    }

    // Add a new user
    public void addUser(String nickname, String email, String password, Role roll) {
        userManager.addUser(nickname, email, password, roll); // Sending data to UserManager
    }

    // Edit an existing user


    // Remove a user
//    public void removeUser(int userId) {
//        userManager.removeUser(userId); // Sending data to UserManager
//    }
}
