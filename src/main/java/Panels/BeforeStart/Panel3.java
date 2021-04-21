package Panels.BeforeStart;

import javax.swing.*;
import java.awt.*;

public class Panel3 extends JPanel {

    public Panel3() {
        //this.setSize(new Dimension(480, 545));
        //this.setBounds(0, 0, 480, 545);
        this.setBackground(new Color(3, 74, 217));
        //this.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon("./images/3.png");
        //label.setBounds(80, 112, 320, 320);
        label.setBackground(new Color(3, 74, 217));
        label.setIcon(icon);
        this.add(label);
    }
}
