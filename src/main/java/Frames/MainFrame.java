package Frames;

import Panels.StartPanel.*;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

    public static MainFrame tempFrame;
    public StartPanel startPanel;

    public MainFrame() {

        super("Arkanoid");
        this.setBounds(3, 3, 620, 607);
        //this.setSize(480, 545);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.startPanel = new StartPanel();
        this.add(startPanel);
        tempFrame = this;
    }

}
