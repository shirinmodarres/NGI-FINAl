package UI.Screen.AddBoard;

import Core.Manager.BoardManager;
import UI.Component.CustomLabel;
import UI.Component.CustomTextField;
import UI.Component.RoundedButton;
import UI.Screen.BoardPanel.BoardPanelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBoardView extends JDialog {
    private AddBoardController addBoardController;
    private final Font titleFont = new Font("Calibri", Font.PLAIN, 32);

    public AddBoardView(BoardManager boardManager) {
        addBoardController = new AddBoardController(boardManager);
        setTitle("Add Board");
        setModal(true); // Make the dialog modal (blocks input to other windows)
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close the dialog on close button

        // Use a JPanel with null layout as the content pane
        JPanel contentPane = new JPanel(null);
        contentPane.setBackground(new Color(251, 246, 230));
        contentPane.setPreferredSize(new Dimension(500, 400));

        Font font = new Font("Calibri", Font.PLAIN, 20);
        CustomLabel title = new CustomLabel("Title", font, 65, 65, 200, 23);
        contentPane.add(title);

        CustomTextField titleField = new CustomTextField("Title",60, 85, 260, 40);
        contentPane.add(titleField);

        RoundedButton saveBtn = new RoundedButton("Save", 50, 200, 100, 40, new Color(96, 150, 180));
        contentPane.add(saveBtn);

        RoundedButton cancelBtn = new RoundedButton("Cancel", 250, 200, 100, 40, new Color(214, 64, 69));
        contentPane.add(cancelBtn);

        // Add action listeners
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Extract data from input fields
                String boardName = titleField.getText();

                addBoardController.addBoard(boardName);
                titleField.setText("");
//                BoardPanelView boardPanelView = new BoardPanelView(boardManager);
//                setVisible(false); // Hide the dialog
//                Container parent = getParent();
//                parent.remove(AddBoardView.this); // Remove the current panel (AddMemberView)
//                parent.add(boardPanelView); // Add the member view panel
//                parent.revalidate(); // Revalidate the container to update the UI
//                parent.repaint(); // Repaint the container to refresh the UI
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleField.setText("");
                setVisible(false); // Hide the dialog
            }
        });

        // Set the custom content pane
        setContentPane(contentPane);

        // Pack the dialog to set its size based on the preferred size of the content pane
        pack();

        // Set the position of the dialog to be centered relative to the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
    }
}






