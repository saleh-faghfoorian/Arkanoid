package Panels.BeforeStart;

import javax.swing.*;
import java.awt.*;

public class Panel2 extends JPanel {
    public Panel2() {
        this.setBounds(0, 0, 480, 545);
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon("./images/2.png");
        label.setBounds(80, 112, 320, 320);
        label.setBackground(new Color(3, 74, 217));
        label.setIcon(icon);
        this.add(label, BorderLayout.CENTER);
    }
}
