package Panels.StartPanel;

import Application.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Buttons extends JPanel implements ActionListener {

    JButton btn1 = new JButton("New Game");
    JButton btn2 = new JButton("Load Game");
    JButton btn3 = new JButton("Top Scores");
    JButton btn4 = new JButton("Help");
    JButton btn5 = new JButton("Credits");
    JButton btn6 = new JButton("Exit");
    public LinkedList<Listener> listeners;

    public Buttons() {
        this.listeners = new LinkedList<>();
        this.setPreferredSize(new Dimension(150, 500));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 53));
        this.setBackground(new Color(3, 74, 217));

        btn1.setBackground(new Color(255, 189, 2));
        btn2.setBackground(new Color(255, 189, 2));
        btn3.setBackground(new Color(255, 189, 2));
        btn4.setBackground(new Color(255, 189, 2));
        btn5.setBackground(new Color(255, 189, 2));
        btn6.setBackground(new Color(255, 189, 2));

        btn1.setPreferredSize(new Dimension(70, 30));
        btn2.setPreferredSize(new Dimension(70, 30));
        btn3.setPreferredSize(new Dimension(70, 30));
        btn4.setPreferredSize(new Dimension(70, 30));
        btn5.setPreferredSize(new Dimension(70, 30));
        btn6.setPreferredSize(new Dimension(70, 30));

        btn1.setBorder(BorderFactory.createRaisedBevelBorder());
        btn2.setBorder(BorderFactory.createRaisedBevelBorder());
        btn3.setBorder(BorderFactory.createRaisedBevelBorder());
        btn4.setBorder(BorderFactory.createRaisedBevelBorder());
        btn5.setBorder(BorderFactory.createRaisedBevelBorder());
        btn6.setBorder(BorderFactory.createRaisedBevelBorder());

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);

        this.add(btn1);
        this.add(btn2);
        this.add(btn3);
        this.add(btn4);
        this.add(btn5);
        this.add(btn6);
    }


    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Listener listener : listeners){
            if (e.getSource() == btn1){
                listener.listen("New");
            }
            if (e.getSource() == btn2){
                listener.listen("Load");
            }
            if (e.getSource() == btn3){
                listener.listen("Scores");
            }
            if (e.getSource() == btn4){
                listener.listen("Help");
            }
            if (e.getSource() == btn5){
                listener.listen("Credits");
            }
            if (e.getSource() == btn6){
                listener.listen("Exit");
            }
        }
    }




}
