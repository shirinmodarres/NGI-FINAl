package UI.Screen.BoardPanel;

import Core.Manager.BoardManager;
import Core.Model.Board;
import Core.Model.Project;
import UI.Component.*;
import UI.Screen.AddBoard.AddBoardView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static UI.Component.Colors.getRandomColor;

public class BoardPanelView extends JPanel {
    private final Font textFont = new Font("Calibri", Font.PLAIN, 20);
    private final Font subTextFont = new Font("Calibri", Font.PLAIN, 14);
    private final Font titleFont = new Font("Calibri", Font.PLAIN, 32);

    private BoardPanelController boardPanelController;
    private JPanel boardPanel = new JPanel();
    private AddBoardView addBoardView;

    public BoardPanelView(BoardManager boardManager, Project project) {
        boardPanelController = new BoardPanelController(boardManager);
        setLayout(null);
        setBounds(15, 0, 700, 600);
        setBackground(new Color(251, 246, 230));

        ImageButton searchBoardIcon = new ImageButton("img/search.png", 430, 35, 44, 44);
        CustomTextField searchField = new CustomTextField("Search..", 479, 35, 130, 30);
        JPanel addBoardPlace = createKanbanBoard();

        if (!boardManager.getAllBoards().isEmpty()) {
            boardPanel.setLayout(null);
            JScrollPane boardScrollPane = new JScrollPane(boardPanel);
            boardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            boardScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(boardScrollPane);

            generateBoardPlacePanels();
        }

        add(addBoardPlace);
        add(searchBoardIcon);
        add(searchField);
        setVisible(true);
    }


    private JPanel createKanbanBoard() {
        JPanel kanbanBoardPlace = new JPanel();
        kanbanBoardPlace.setBorder(new RoundedBorder(new Color(147, 191, 207), 6, 3));
        kanbanBoardPlace.setLayout(null);
        kanbanBoardPlace.setBackground(new Color(255, 255, 255, 25));
        kanbanBoardPlace.setBounds(60, 100, 550, 60);

        CustomLabel title =new CustomLabel("KanbanBoard",textFont,220,10,120,50);
        kanbanBoardPlace.add(title);

        kanbanBoardPlace.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Kanban Board");
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
//        ImageButton addBoardIcon = new ImageButton("img/add.png", 100, 75, 44, 44);
//        addBoardIcon.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addBoardView = new AddBoardView(boardPanelController.getBoardManager());
//                addBoardView.setVisible(true);
//                add(addBoardView);
//                repaint();
//                showPopup();
//            }
//        });

//        addBoardPlace.add(addBoardIcon);
        return kanbanBoardPlace;
    }

    private void showPopup() {
        // Set the visibility of the popup panel to true to show it
        AddBoardView addBoardView = new AddBoardView(boardPanelController.getBoardManager());

        // Show the popup as a modal dialog (blocks input to other windows)
        addBoardView.setModal(true);
        addBoardView.setVisible(true);
    }

    private void generateBoardPlacePanels() {
        int x = 20;
        int y = 103;

        int cardWidth = 550;
        int cardHeight = 60;
        int cardSpacing = 30;
        int containerWidth = cardWidth + 2 * x;
        int currentX = x;
        int currentY = y;

        for (Board board : boardPanelController.getBoardManager().getAllBoards()) {
            Color color = getRandomColor();
            JPanel boardPlace = new JPanel();
            boardPlace.setBorder(new RoundedBorder(color, 6, 3));
            boardPlace.setLayout(null);
            boardPlace.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 60));
            boardPlace.setBounds(currentX, currentY, cardWidth, cardHeight);

            CustomLabel date = new CustomLabel(board.getFormattedDate().toString(), subTextFont, 15, 15, 100, 25);
            Circle circle = new Circle(color, 70, 50, 55, 55);
            CustomLabel title = new CustomLabel(board.getName(), textFont, 80, 50, 110, 25);
            title.setHorizontalAlignment(JLabel.CENTER);

            boardPlace.add(circle);
            boardPlace.add(title);
            boardPlace.add(date);

            boardPanel.add(boardPlace); // Added to the boardPanel instead of the main panel

            currentY += cardHeight + cardSpacing;
        }

        // Update the preferred size of BoardPanelView based on the number of cards
        int totalHeight = currentY + y;
        setPreferredSize(new Dimension(containerWidth, totalHeight));
    }
}
