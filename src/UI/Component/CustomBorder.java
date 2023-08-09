package UI.Component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Line2D;

public class CustomBorder implements Border {

    private final Color borderColor;
    private final int strokeThickness;

    public CustomBorder(Color borderColor, int strokeThickness) {
        this.borderColor = borderColor;
        this.strokeThickness = strokeThickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(strokeThickness));
        g2d.draw(new Line2D.Double(x, y + height - 1, x + width - 1, y + height - 1));
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(strokeThickness, strokeThickness, strokeThickness, strokeThickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
