package UI.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CustomTextField extends JTextField {
    private final String placeholder;
    private boolean isPlaceholderHidden;

    private int cornerRadius = 7;
    private Font placeHolderFont = new Font("Calibri", Font.PLAIN, 16);

    public CustomTextField(String placeholder, int x, int y, int width, int height) {
        super(placeholder);
        this.placeholder = placeholder;
        this.isPlaceholderHidden = false;

        setFont(placeHolderFont);
        setBounds(x, y, width, height);
        setForeground(Color.BLACK);
        setOpaque(false);
        setBorder(new EmptyBorder(1, 4, 1, 4));

        // Add a FocusListener to show/hide the placeholder
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    isPlaceholderHidden = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    isPlaceholderHidden = false;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = cornerRadius * 2;
        int x = 1;
        int y = 1;
        int width = getWidth() - 2;
        int height = getHeight() - 2;

        // Draw rounded rectangle for the background
        g2d.setColor(Color.WHITE); // Set the color of the rounded rectangle
        g2d.fillRoundRect(x, y, width, height, arc, arc);

        // Draw the text on top of the rounded rectangle
        g2d.setColor(Color.BLACK);
        g2d.setFont(getFont());
        FontMetrics metrics = g2d.getFontMetrics(getFont());
        int textX = (getWidth() - metrics.stringWidth(getText())) / 2;
        int textY = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(getText(), textX, textY);

        g2d.dispose();
    }
}
