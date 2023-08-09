package UI.Screen.Member;

import Core.DataBase.UserDatabase;

import Core.Manager.UserManager;
import Core.Model.User;
import UI.Component.*;
import UI.Screen.EditMember.EditMemberView;
//import UI.Screen.EditMember.EditMemberView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static UI.Component.Colors.getRandomColor;

public class MemberView extends JPanel {
    private final Font textFont = new Font("Calibri", Font.PLAIN, 18);
    private final Font subTextFont = new Font("Calibri", Font.PLAIN, 14);
    RoundedBorder border = new RoundedBorder(new Color(147, 191, 207), 6, 3);
    private final MemberController memberController;
    JPanel memberPanel = new JPanel();
    EditMemberView editMemberView;

    public MemberView(UserManager userManager, ActionListener addMemberEvent) {

        // Provide userManager to AddMemberController
        memberController = new MemberController(userManager);

        //Setting
        setVisible(false);
        setLayout(null);
        setBounds(85, 15, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(true);

        //Header
        Font titleFont = new Font("Calibri", Font.PLAIN, 32);
        CustomLabel title = new CustomLabel("Members", titleFont, 20, 32, 390, 40);
        add(title);
        ImageButton searchMemberIcon = new ImageButton("img/search-member.png", 500, 32, 44, 44);
        add(searchMemberIcon);

        CustomTextField searchField = new CustomTextField("Search..", 554, 44, 130, 30);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = searchField.getText().trim();
                    if (!searchText.isEmpty()) {
                        findUser(searchText);
                    }
                }
            }
        });
        add(searchField);

        //Add members card
        JPanel addMemberPlace = createAddMemberPanel(addMemberEvent);
        add(addMemberPlace);

        generateMemberPlacePanels();

    }

    public void pageIsEmpty() {
        memberPanel.setLayout(null);
        JScrollPane memberScrollPane = new JScrollPane(memberPanel);
        memberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        memberScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(memberScrollPane);
        //Membership Card
        generateMemberPlacePanels();

    }

    private JPanel createAddMemberPanel(ActionListener addMemberEvent) {
        JPanel addMemberPlace = new JPanel();
        addMemberPlace.setBorder(border);
        addMemberPlace.setLayout(null);
        addMemberPlace.setVisible(true);
        addMemberPlace.setBackground(new Color(255, 255, 255, 25));
        addMemberPlace.setBounds(20, 103, 175, 212);

        ImageButton addMemberIcon = new ImageButton("img/add.png", 65, 82, 44, 44);
        addMemberIcon.addActionListener(addMemberEvent);
        addMemberPlace.add(addMemberIcon);

        return addMemberPlace;
    }

    private void findUser(String searchText) {
        User foundUser = memberController.findUserByName(searchText);
        if (foundUser != null) {
            System.out.println("User found: " + foundUser);
        } else {
            System.out.println("User not found.");
        }
    }

    private void removeUser(User user) {
        memberController.getUserManager().removeUser(user);
        generateMemberPlacePanels();// Call the removeUser method in your manager
        revalidate();
        repaint();
    }

    public void generateMemberPlacePanels() {
        int x = 20;
        int y = 103;
        int padding = 20;
        int containerWidth = 700;
        int currentX = x;
        int currentY = y;


        //Generate Membership card
        for (User user : memberController.getUserManager().getUserDatabase().getUsers()) {
            currentX += 175 + padding;
            if (currentX + 175 + padding > containerWidth) {
                currentX = x;
                currentY += 212 + padding;
            }
            generateMemeber(user, currentX, currentY, padding, containerWidth);
        }
    }

    public void generateMemeber(User user, int x, int y, int padding, int containerWidth) {
        JPanel memberPlace = new JPanel();
        memberPlace.setBorder(border);
        memberPlace.setLayout(null);
        memberPlace.setBackground(new Color(255, 255, 255, 60));
        memberPlace.setBounds(x, y, 175, 212);
        add(memberPlace);

        ImageButton removeBtn = new ImageButton("img/remove.png", 20, 10, 25, 25);
        removeBtn.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove this user?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION
            );
            if (dialogResult == JOptionPane.YES_OPTION) {
                JPanel memberselectedPlace = (JPanel) removeBtn.getParent();
                removeUser(user);
                memberPanel.remove(memberselectedPlace);
                memberPanel.remove(memberselectedPlace.getParent());
                memberPlace.setVisible(false);
//                    memberPanel.repaint();
//                    generateMemberPlacePanels();
//                    memberPanel.getParent().repaint();
//                    memberPanel.getParent().revalidate();
            }
        });
        memberPlace.add(removeBtn);
        ImageButton editBtn = new ImageButton("img/edit.png", 140, 10, 25, 25);
        editBtn.addActionListener(e -> {
            editMemberView = new EditMemberView(user, UserManager.getInstance(UserDatabase.getInstance()), updatedUser -> {
                memberPlace.removeAll();
                generateMemeber(updatedUser, x, y, padding, containerWidth);
                editMemberView.setVisible(false);
            });
            add(editMemberView, 0);
            revalidate();
            repaint();
        });
        memberPlace.add(editBtn);
        Color color = getRandomColor();
        Circle circle = new Circle(color, 65, 40, 50, 50);
        memberPlace.add(circle);

        CustomLabel name = new CustomLabel(user.getName(), textFont, 40, 105, 100, 25);
        name.setHorizontalAlignment(JLabel.CENTER);
        memberPlace.add(name);

        CustomLabel position = new CustomLabel(user.getRole().toString(), subTextFont, 40, 135, 100, 25);
        position.setHorizontalAlignment(JLabel.CENTER);
        memberPlace.add(position);

        int rowCount = (memberController.getUserManager().getAllUsers().size() + 2) / 3;
        int totalHeight = rowCount * (212 + padding) + y;
        setPreferredSize(new Dimension(containerWidth, totalHeight));
    }
}