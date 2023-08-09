package UI.Component;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {
    private final Color circleColor;
    private int circleX, circleY, circleWidth, circleHeight;

    public Circle(Color color, int x, int y, int width, int height) {
        this.circleColor = color;
        this.circleX = x;
        this.circleY = y;
        this.circleWidth = width;
        this.circleHeight = height;
        setOpaque(false); // Make the panel transparent
        setBounds(x, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(circleColor);
        g.fillOval(0, 0, circleWidth, circleHeight);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(circleWidth, circleHeight);
    }
}
