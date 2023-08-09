package UI.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

public class RadioButtonDrawer extends JPanel {
    private final List<String> options;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private ButtonGroup buttonGroup;
    private String selectedOption;

    public RadioButtonDrawer(List<String> options, int x, int y, int width, int height) {
        this.options = options;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initUI();
    }

    private void initUI() {
        setLayout(null);
        setBounds(x, y, width, height);
        buttonGroup = new ButtonGroup();

        int xOffset = 0;
        for (int i = 0; i < options.size(); i++) {
            JRadioButton radioButton = new JRadioButton(options.get(i));
            radioButton.setFont(new Font("Calibri", Font.PLAIN, 14)); // Set the font
            radioButton.setBounds(xOffset, 0, width / options.size(), height);
            radioButton.addActionListener(new RadioButtonActionListener());
            add(radioButton);
            buttonGroup.add(radioButton);
            xOffset += width / options.size();
        }
    }

    private class RadioButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
            selectedOption = selectedRadioButton.getText();
            System.out.println("Selected option: " + selectedOption);
        }
    }

    public String getSelectedOption() {
        return selectedOption;
    }
    public String getSelectedValue() {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null; // No button is selected
    }
}
