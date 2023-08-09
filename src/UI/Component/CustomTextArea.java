package UI.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTextArea extends JTextArea {
    private String placeholder;
    private int cornerRadius;
    private Color borderColor;
    private boolean isPlaceholderHidden;

    public CustomTextArea(String placeholder, int x, int y, int width, int height, Color borderColor) {
        this.placeholder = placeholder;
        this.cornerRadius = 7;
        this.borderColor = borderColor;
        this.isPlaceholderHidden = false;
setText(placeholder);
        setBounds(x, y, width, height);
        setFont(new Font("Calibri", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setOpaque(false); // Make the text area background transparent
        setBorder(new EmptyBorder(10, 10, 10, 10));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isPlaceholderHidden) {
                    setText("");
                    isPlaceholderHidden = true;
                }
                requestFocusInWindow();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = cornerRadius * 2;
        int strokeWidth = 3; // The stroke width of the border

        // Draw rounded rectangle for the background
        g2d.setColor(getBackground()); // Use the background color of the parent container
        g2d.fillRoundRect(strokeWidth, strokeWidth, getWidth() - 2 * strokeWidth, getHeight() - 2 * strokeWidth, arc, arc);

        // Draw rounded rectangle for the border
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawRoundRect(strokeWidth / 2, strokeWidth / 2, getWidth() - strokeWidth, getHeight() - strokeWidth, arc, arc);

        g2d.dispose();
        super.paintComponent(g);
    }

//    @Override
//    public String getText() {
//        if (isPlaceholderHidden) {
//            return super.getText();
//        } else {
//            return "";
//        }
//    }

}
