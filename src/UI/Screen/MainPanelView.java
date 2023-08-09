package UI.Screen;


import Core.DataBase.ProjectDatabase;
import Core.DataBase.UserDatabase;
import Core.Manager.ProjectManager;
import Core.Manager.UserManager;
import Core.Model.Project;
import UI.Component.MenuTabBarView;
import UI.Screen.AddMember.AddMemberView;
import UI.Screen.AddProject.AddProjectView;
import UI.Screen.LogIn.LoginController;
import UI.Screen.LogIn.LoginView;
import UI.Screen.Member.MemberView;
import UI.Screen.Project.ProjectView;
import UI.Screen.ProjectPanel.ProjectPanelView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanelView extends JPanel {


    // Database and Repository
    ProjectDatabase projectDatabase = ProjectDatabase.getInstance();
    UserDatabase userDatabase = UserDatabase.getInstance();
    UserManager userManager = UserManager.getInstance(userDatabase);

    // All Manager
    ProjectManager projectManager = new ProjectManager(projectDatabase);

    //Login page
    LoginController loginController = new LoginController(userManager);
    LoginView loginView = new LoginView(loginController, new LoginView.LoginEventListener() {
        @Override
        public void onLogin() {
            loginView.setVisible(false);
            setVisible(true);
            loginView.performLogin(loginController);
        }
    });

    // Menus
    MenuTabBarView menuTabBarView = new MenuTabBarView(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Show the project view and hide other panels
            projectView.setVisible(true);
            memberView.setVisible(false);
            addMemberView.setVisible(false);
        }
    }, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Show the member view and hide other panels
            projectView.setVisible(false);
            memberView.setVisible(true);
            addProjectView.setVisible(false);
        }
    }, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Show the login view
            loginView.setVisible(true);
        }
    });

    MemberView memberView = new MemberView(userManager, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hide member view and show add member view
            memberView.setVisible(false);
            addMemberView.setVisible(true);
            projectView.setVisible(false);
        }
    });

    AddMemberView addMemberView = new AddMemberView(userManager, projectManager, new AddMemberView.AddMemberViewEventListener() {
        @Override
        public void onPageClosed() {
            // Hide add member view, show member view, and update its content
            addMemberView.setVisible(false);
            memberView.setVisible(true);
            memberView.generateMemberPlacePanels();
        }
    }, new AddMemberView.ProjectSelectedListener() {
        @Override
        public void onProjectSelected(Project project) {
            // Show project info view, hide other panels
            projectPanelView = new ProjectPanelView(project);
            projectPanelView.setVisible(true);
            addMemberView.setVisible(false);
            memberView.setVisible(false);
            projectView.setVisible(false);
        }
    }

    );
    ProjectPanelView projectPanelView;
    ProjectView projectView = new ProjectView(projectManager, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            projectView.setVisible(false);
            addProjectView.setVisible(true);
        }
    }, new ProjectView.ProjectViewEventListener() {
        @Override
        public void onProjectClick(Project project) {
            projectPanelView = new ProjectPanelView(project);
            projectPanelView.setVisible(true);
            addMemberView.setVisible(false);
            memberView.setVisible(false);
            projectView.setVisible(false);
            add(projectPanelView);

        }
    });
    AddProjectView addProjectView = new AddProjectView(userManager,projectManager, new AddProjectView.AddProjectViewEventListener() {
        @Override
        public void onPageClosed() {
            addProjectView.setVisible(false);
            projectView.setVisible(true);
            projectView.generateProjectPlacePanels();
        }
    });


    MainPanelView() {
        //Setting
        setBounds(0, 0, 800, 600);
        setBackground(new Color(0, 0, 0, 0));
        setVisible(true);
        setLayout(null);
        add(loginView);
        //Add all panels
        add(memberView);
        add(addMemberView);
//        add(editMemberView,0);


        add(projectView);
        add(addProjectView);

        add(menuTabBarView);

    }
}
