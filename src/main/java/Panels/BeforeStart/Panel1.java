package Panels.BeforeStart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel1 extends JPanel implements KeyListener {
    public JLabel label;
    public Panel1() {
        this.setBounds(0, 0, 480, 545);
        this.setLayout(new BorderLayout());
        label = new JLabel();
        label.setOpaque(true);
        ImageIcon icon = new ImageIcon("./images/1.png");
        label.setBounds(80, 112, 320, 320);
        label.setBackground(new Color(3, 74, 217));
        label.setIcon(icon);
        this.add(label, BorderLayout.CENTER);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'a': label.setLocation(label.getX()-1, label.getY());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
