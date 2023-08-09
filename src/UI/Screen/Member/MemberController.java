package UI.Screen.Member;

import Core.Manager.UserManager;
import Core.Model.User;

public class MemberController {
    private UserManager userManager;

    public MemberController(UserManager userManager) {
        this.userManager = userManager;
    }

    public User findUserByName(String name) {
        return userManager.findUserByName(name);
    }
    public UserManager getUserManager() {
        return userManager;
    }

}
