package UI.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static UI.Component.Colors.getRandomColor;

public class CircleList extends JPanel {
    private final ArrayList<String> itemList;
    private final int circleSize;
    private final int xMargin;
    private final int yMargin;

    public CircleList(ArrayList<String> itemList, int circleSize, int xMargin, int yMargin) {
        this.itemList = itemList;
        this.circleSize = circleSize;
        this.xMargin = xMargin;
        this.yMargin = yMargin;
        setupUI();
    }

    private void setupUI() {
        removeAll(); // Clear existing components
        setOpaque(false); // Make the panel transparent

        int totalHeight = circleSize + yMargin; // Total height for each item panel

        for (String item : itemList) {
            JPanel itemPanel = new JPanel();
            itemPanel.setPreferredSize(new Dimension(circleSize + xMargin, totalHeight));

            Color color = getRandomColor();
            Circle circle = new Circle(color, 0, 0, circleSize, circleSize);
            JLabel nameLabel = new JLabel(item);
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Set positions for circle and label manually
            circle.setBounds((circleSize + xMargin - circleSize) / 2, 0, circleSize, circleSize);
            nameLabel.setBounds(0, circleSize, circleSize + xMargin, 10); // Adjust the y coordinate as needed

            itemPanel.setLayout(null); // Use null layout to position components manually
            itemPanel.add(circle);
            itemPanel.add(nameLabel);

            add(itemPanel);
        }
    }



    public JScrollPane getScrollPane() {
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }
}
