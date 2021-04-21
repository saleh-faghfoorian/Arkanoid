package Panels.Play;

import Frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public abstract class PlayNewGame extends JPanel implements KeyListener{


    protected Level gameLevel;


    public PlayNewGame() {
        this.addKeyListener(this);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        onDraw(g2D);
    }


    /*
    @Override
    public void actionPerformed(ActionEvent e) {}

     */



    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        onKeyPressed(e);
    }


    protected abstract void onDraw(Graphics2D g);

    protected abstract void onKeyPressed(KeyEvent e);

}



