package UI.Screen.AddMember;

import Core.Manager.ProjectManager;
import Core.Manager.UserManager;
import Core.Model.Project;
import Core.Model.Role;
import UI.Component.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class AddMemberView extends JPanel {

    AddMemberController addMemberController;
    private ArrayList<JLabel> projectLabels; // New: Store the project labels

    public AddMemberView(UserManager userManager, ProjectManager projectManager, AddMemberViewEventListener addMemberViewEventListener, ProjectSelectedListener projectSelectedListener) {

        addMemberController = new AddMemberController(userManager); // Provide userManager to AddMemberController
        //Setting
        setLayout(null);
        setBounds(85, 15, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(false);

        //Header
        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Add Members", titleFont, 20, 32, 390, 40);
        add(title);

        //setUp component
        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel name = new CustomLabel("Name:", font, 25, 80, 110, 23);
        add(name);

        CustomTextField nameField = new CustomTextField("Write Your Name", 20, 103, 260, 40);
        add(nameField);

        CustomLabel email = new CustomLabel("Email:", font, 385, 80, 110, 23);
        add(email);

        CustomTextField emailField = new CustomTextField("Write Your Email", 380, 103, 260, 40);
        add(emailField);

        CustomLabel password = new CustomLabel("Password:", font, 25, 160, 110, 23);
        add(password);

        CustomTextField passwordField = new CustomTextField("You Should write 8 Character ", 20, 183, 260, 40);
        add(passwordField);

        CustomLabel role = new CustomLabel("Role:", font, 385, 160, 110, 23);
        add(role);

        ArrayList<String> roleName = new ArrayList<>();
        for (Role r : Role.values()) {
            switch (r) {
                case PO -> roleName.add("PO");
                case TESTER -> roleName.add("TESTER");
                case DEVELOPER -> roleName.add("DEVELOPER");
            }
        }
        DropdownField roleDropDown = new DropdownField(roleName.toArray(new String[0]), 380, 183, 260, 40);
        add(roleDropDown);

        CustomLabel project = new CustomLabel("Project:", font, 25, 240, 110, 23);
        add(project);

        projectLabels = new ArrayList<>();

        JPanel contentPanel = new JPanel();
        contentPanel.setVisible(true);
        contentPanel.setBounds(25, 260, 600, 200);
        contentPanel.setBorder(new RoundedBorder(Color.BLUE, 4, 4));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Inside the for-loop to create project labels
        for (Project p : projectManager.getProjectDatabase().getAllProjects()) {
            JLabel projectLabel = new JLabel(p.getTitle());
            projectLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Check for double-click
                        projectSelectedListener.onProjectSelected(p);
                    } else if (e.getClickCount() == 1) { // Check for single-click
                        createCircle(projectLabel);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            projectLabels.add(projectLabel); // Add the label to the list
            contentPanel.add(projectLabel);
        }


        add(contentPanel);
//        JScrollPane scrollPane = new JScrollPane(contentPanel);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        add(scrollPane);


        RoundedButton saveBtn = new RoundedButton("Save", 400, 510, 100, 40, new Color(96, 150, 180));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Extract data from input fields
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String selectedRole = (String) roleDropDown.getSelectedItem();
                Role role = Role.valueOf(selectedRole.toUpperCase());

                // Call the controller method to add the user
                addMemberController.addUser(name, email, password, role);

                // Assuming the first item is a default selection
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                roleDropDown.setSelectedIndex(0);
                addMemberViewEventListener.onPageClosed();

            }
        });
        add(saveBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 515, 510, 100, 40, new Color(214, 64, 69));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                roleDropDown.setSelectedIndex(0); // Assuming the first item is a default selection
                addMemberViewEventListener.onPageClosed();
            }
        });
        add(cancelBtn);

    }

    public interface AddMemberViewEventListener {
        void onPageClosed();

    }

    public interface ProjectSelectedListener {
        void onProjectSelected(Project project);
    }

    private void createCircle(JLabel label) {
        int labelWidth = label.getWidth();
        int labelHeight = label.getHeight();
        int diameter = Math.min(labelWidth, labelHeight) - 10; // Adjust circle size

        CirclePanel circlePanel = new CirclePanel(label.getText()); // Pass the project name to the circle
        circlePanel.setPreferredSize(new Dimension(diameter, diameter));
        circlePanel.setOpaque(false);

        label.setLayout(new GridBagLayout());
        label.add(circlePanel);
        label.revalidate();
        label.repaint();
    }

    private class CirclePanel extends JPanel {
        private String projectName;

        public CirclePanel(String projectName) {
            this.projectName = projectName;
            setPreferredSize(new Dimension(50, 50));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCircle(g);
        }

        private void drawCircle(Graphics g) {
            int radius = 100;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            g.setColor(Color.RED);
            g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            g.setColor(Color.BLACK);
            g.drawString(projectName, centerX - radius, centerY);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 50);
        }
    }

}

