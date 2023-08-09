package UI.Component;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {
    public CustomLabel(String text, Font font, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setForeground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(font);
    }
}