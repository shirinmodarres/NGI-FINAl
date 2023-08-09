package UI.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusListener;

import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private int cornerRadius = 25;

    public RoundedButton(String text, int x, int y, int width, int height, Color color) {
        super(text);
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font("Calibri", Font.PLAIN, 24));
        setBackground(color);
        setBounds(x, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (hasFocus()) {
            g.setColor(getBackground()); // Darken the background color when focused
        } else {
            g.setColor(getBackground());
        }

        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getBackground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
    }

    @Override
    protected void processFocusEvent(FocusEvent e) {
        // Override the focus behavior to repaint the button
        if (e.getID() == FocusEvent.FOCUS_GAINED || e.getID() == FocusEvent.FOCUS_LOST) {
            repaint();
        }
        super.processFocusEvent(e);
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        return shape.contains(x, y);
    }
}
