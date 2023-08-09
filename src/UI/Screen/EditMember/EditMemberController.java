package UI.Screen.EditMember;

import Core.Manager.UserManager;
import Core.Model.Role;

public class EditMemberController {
    private UserManager userManager;

    public EditMemberController(UserManager userManager) {
        this.userManager = userManager;
    }

    public void editUser( String nickname, String email, String password, Role roll) {
        userManager.updateUser( nickname, email, password, roll); // Sending data to UserManager

    }
}
