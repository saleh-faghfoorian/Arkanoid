package Panels.StartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Show extends JPanel implements ActionListener {
    JLabel label;


    public Show() {
        label = new JLabel();
        label.setBackground(Color.CYAN);
        ImageIcon icon = new ImageIcon("./images/Start.jpg");
        label.setIcon(icon);
        this.add(label);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
