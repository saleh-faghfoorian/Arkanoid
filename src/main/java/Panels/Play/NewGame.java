package Panels.Play;

import Application.Listener;
import Frames.MainFrame;
import Panels.StartPanel.StartPanel;

import javax.swing.*;
import java.awt.*;

public class NewGame extends JPanel {

    public Level level;
    MainFrame frame = MainFrame.tempFrame;

    public NewGame() {
        GetLevel getLevel = new GetLevel();
        getLevel.addListener(new Listener() {
            @Override
            public void listen(String string) {
                if (string.equals("Return")){
                    StartPanel.returnToMain();
                }
                else {
                    if (string.equals("Easy")){
                        level = Level.EASY;
                    }
                    if (string.equals("Normal")){
                        level = Level.NORMAL;
                    }
                    if (string.equals("Hard")){
                        level = Level.HARD;
                    }

                    frame.getContentPane().removeAll();
                    GamePanel gamePanel = new GamePanel(level, true);
                    frame.add(gamePanel);
                    frame.setFocusable(true);
                    frame.requestFocus();
                    frame.addKeyListener(gamePanel);
                    frame.repaint();
                    frame.revalidate();
                }
            }
        });
    }



}
