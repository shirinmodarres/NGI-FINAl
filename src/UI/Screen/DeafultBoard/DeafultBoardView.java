package UI.Screen.DeafultBoard;

import UI.Component.CustomLabel;

import javax.swing.*;
import java.awt.*;

public class DeafultBoardView extends JPanel {
    private final Font textFont = new Font("Calibri", Font.PLAIN, 20);
    private final Font subTextFont = new Font("Calibri", Font.PLAIN, 14);
    private final Font titleFont = new Font("Calibri", Font.PLAIN, 32);

    public DeafultBoardView(){
        //Setting
        setLayout(null);
        setVisible(false);
        setBounds(0, 0, 700, 570);
        setBackground(new Color(251, 246, 230));
        setVisible(true);

        //Header
//        CustomLabel title = new CustomLabel("Kanban Board", titleFont, 20, 32, 390, 40);
//        add(title);

        //SetUp component
        Panel header = new Panel();
        header.setLayout(null);
        header.setBounds(95,0,700,60);
        header.setBackground(new Color(251, 246, 230));
        add(header);

        CustomLabel todoLabel = new CustomLabel("TODO",textFont,5,20,100,30);
        header.add(todoLabel);
        CustomLabel inProgressLabel =new CustomLabel("In Progress",textFont,130,20,100,30);
        header.add(inProgressLabel);
        CustomLabel qaLabel = new CustomLabel("QA",textFont,310,20,100,30);
        header.add(qaLabel);
        CustomLabel doneLabel=new CustomLabel("Done",textFont,470,20,100,30);
        header.add(doneLabel);



    }
}
