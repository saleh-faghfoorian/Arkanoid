package Panels.Play;

import Application.Listener;
import Frames.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Start extends JPanel implements ActionListener {

    private JButton btn1 = new JButton("New");
    private JButton btn2 = new JButton("Load");
    private JButton btn3 = new JButton("Return");
    private ArrayList<Listener> listeners;
    MainFrame frame = MainFrame.tempFrame;


    public Start() {
        this.listeners = new ArrayList<>();
        frame.getContentPane().removeAll();
        frame.repaint();






    }

    public void addListener(Listener listener){
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
                listener.listen("Return");
            }
        }
    }




}
