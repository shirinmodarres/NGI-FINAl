package UI.Component;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private final Color borderColor;
    private final int cornerRadius;
    private final int strokeWidth;

    public RoundedBorder(Color borderColor, int cornerRadius, int strokeWidth) {
        this.borderColor = borderColor;
        this.cornerRadius = cornerRadius;
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(strokeWidth));
        int arc = cornerRadius * 2;
        g2d.setColor(borderColor);
        g2d.drawRoundRect(x + strokeWidth / 2, y + strokeWidth / 2, width - strokeWidth, height - strokeWidth, arc, arc);
        g2d.setStroke(oldStroke);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(strokeWidth, strokeWidth, strokeWidth, strokeWidth);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
