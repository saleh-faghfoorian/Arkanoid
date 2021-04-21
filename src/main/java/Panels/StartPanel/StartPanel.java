package Panels.StartPanel;

import Application.Listener;
import Frames.MainFrame;
import Panels.Play.NewGame;
import Panels.Save.Load;
import Panels.Save.LoadPanel;
import Panels.Scores;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    public Buttons buttons;
    public Show show;

    public StartPanel() {
        this.setLayout(new BorderLayout());
        buttons   = new Buttons();
        show      = new Show();
        this.add(buttons, BorderLayout.WEST);
        this.add(show, BorderLayout.CENTER);

        buttons.addListener(new Listener() {
            @Override
            public void listen(String string) {

                if (string.equals("New")){
                    NewGame newGame = new NewGame();
                }

                if (string.equals("Load")){
                    JFrame loadFrame = new JFrame("Load");
                    loadFrame.setBounds(0, 0, 600, 800);
                    loadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loadFrame.setLocationRelativeTo(null);
                    loadFrame.setResizable(false);
                    loadFrame.setLayout(null);
                    loadFrame.setVisible(true);
                    LoadPanel loadPanel = new LoadPanel();
                    loadFrame.add(loadPanel);
                    loadPanel.addListener(new Listener() {
                        @Override
                        public void listen(String string) {
                            if (string.equals("Load")){
                                int row = Integer.parseInt(loadPanel.textField.getText());
                                int mapId = loadPanel.getMapIds(row - 1);
                                loadPanel.load = new Load(loadPanel.userIds.get(row - 1), mapId);
                                loadFrame.dispose();
                                MainFrame.tempFrame.dispose();
                                JFrame frame = new JFrame();
                                frame.setBounds(3, 3, 620, 607);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.setLocationRelativeTo(null);
                                frame.setResizable(false);
                                frame.setLayout(new BorderLayout());
                                frame.setVisible(true);
                                frame.add(loadPanel.load.gamePanel);
                                loadPanel.load.action();
                            }
                            if (string.equals("Return")){
                                loadFrame.dispose();
                                returnToMain();
                            }
                        }
                    });
                }


                if (string.equals("Scores")){
                    Scores scores = new Scores();
                    scores.setListener(new Listener() {
                        @Override
                        public void listen(String string) {
                            if (string.equals("Return")){
                                returnToMain();
                            }
                        }
                    });
                }


                if (string.equals("Help")){
                    Help help = new Help();
                    help.setListener(new Listener() {
                        @Override
                        public void listen(String string2) {
                            if (string2.equals("Help")){
                                returnToMain();
                            }
                        }
                    });
                }


                if (string.equals("Credits")){
                    Credits credits = new Credits();
                    credits.setListener(new Listener() {
                        @Override
                        public void listen(String string2) {
                            if (string2.equals("Main")){
                                returnToMain();
                            }
                        }
                    });
                }


                if (string.equals("Exit")){
                    System.out.println("Exit!");
                    MainFrame.tempFrame.dispose();
                }


            }
        });




    }


    public static void returnToMain(){
        MainFrame frame = MainFrame.tempFrame;
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(frame.startPanel, BorderLayout.CENTER);
    }


}

