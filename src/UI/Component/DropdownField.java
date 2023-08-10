package UI.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DropdownField extends JComboBox<String> {

    private int fontSize;
    private Color highlightTextColor;
    private Color arrowColor;

    private final Font subTextFont = new Font("Calibri", Font.BOLD, 14);
    public DropdownField(List<String> items, int x, int y, int width, int height) {
        super(items.toArray(new String[0])); // Convert the List to an array
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
