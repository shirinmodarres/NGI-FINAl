package UI.Component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DropdownField extends JComboBox<String> {

    private int fontSize;
    private Color highlightTextColor;
    private Color arrowColor;

    private final Font subTextFont = new Font("Calibri", Font.BOLD, 14);
    public DropdownField(String[] items, int x, int y, int width, int height) {
        super(items);
        setBounds(x, y, width, height);
        setBackground(Color.WHITE);
        this.fontSize = 18;
        this.highlightTextColor = new Color(147, 191, 207);
        this.arrowColor = new Color(217, 217, 217);

        // Set the font size of the text
        setFont(subTextFont);
        setSelectedIndex(0);

    }
}
