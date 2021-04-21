package Panels.Play;

import Application.Listener;
import Frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Pause extends JPanel implements ActionListener {

    JButton btn1, btn2, btn3, btn4;
    ArrayList<Listener> listeners;

    public Pause() {
        this.setLayout(null);
        this.setBounds(0, 0, 200, 250);
        this.setBackground(new Color(3, 74, 217));

        listeners = new ArrayList<>();

        btn1 = new JButton("Resume");
        btn2 = new JButton("Save");
        btn4 = new JButton("Exit");
        btn3 = new JButton("Restart");

        btn1.setBackground(new Color(255, 189, 2));
        btn2.setBackground(new Color(255, 189, 2));
        btn3.setBackground(new Color(255, 189, 2));
        btn4.setBackground(new Color(255, 189, 2));

        btn1.setBounds(50, 20, 80, 30);
        btn2.setBounds(50, 70, 80, 30);
        btn3.setBounds(50, 120, 80, 30);
        btn4.setBounds(50, 170, 80, 30);

        btn1.setBorder(BorderFactory.createRaisedBevelBorder());
        btn2.setBorder(BorderFactory.createRaisedBevelBorder());
        btn3.setBorder(BorderFactory.createRaisedBevelBorder());
        btn4.setBorder(BorderFactory.createRaisedBevelBorder());

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);

        this.add(btn1);
        this.add(btn2);
        this.add(btn3);
        this.add(btn4);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Listener listener : listeners){
            if (e.getSource() == btn1){
                listener.listen("Resume");
            }
            if (e.getSource() == btn2){
                listener.listen("Save");
            }
            if (e.getSource() == btn3){
                listener.listen("Restart");
            }
            if (e.getSource() == btn4){
                listener.listen("Exit");
            }
        }
    }



}
