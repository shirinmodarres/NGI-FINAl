
import UI.Screen.MainFrame;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and display the main frame
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });


    }


}
