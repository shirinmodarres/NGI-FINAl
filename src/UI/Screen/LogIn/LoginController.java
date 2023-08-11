package UI.Screen.LogIn;

import Core.Manager.UserManager;
import Core.Model.User;

import java.util.regex.Pattern;

public class LoginController {
    private UserManager userManager;

    public LoginController(UserManager userManager) {
        this.userManager = userManager;
    }

    public User isValid(String email, String password) {
        if (userManager.isValidUser(email, password) == null) {
            return null;
        } else {
            return userManager.isValidUser(email, password);
        }
    }

    public boolean isPasswordValid(String password) {
        return userManager.isPasswordValid(password);
    }

    public boolean isEmailValid(String email) {
        return userManager.isEmailValid(email);
    }

}

