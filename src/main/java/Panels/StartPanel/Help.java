package Panels.StartPanel;

import Application.Listener;
import Frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Help extends JFrame implements ActionListener {

    private JButton btn;
    private JPanel panel1;
    private JPanel panel2;
    private MainFrame frame = MainFrame.tempFrame;
    public Listener listener;

    public Help() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel1 = new JPanel();
        panel2 = new JPanel();

        panel1.setBounds(0, 0, 620, 507);
        panel2.setBounds(0, 507, 620, 100);

        panel1.setBackground(new Color(3, 74, 217));
        panel2.setBackground(new Color(3, 74, 217));

        panel1.setLayout(null);
        panel2.setLayout(null);


        btn = new JButton("Main Page");
        btn.setBounds(270, 10, 80, 30);
        //btn.setPreferredSize(new Dimension(65, 35));
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setBackground(new Color(255, 189, 2));
        //btn.setBounds(200, 0, 65, 35);
        btn.addActionListener(this);

        JTextArea text = new JTextArea();
        String txt = "                           ***** Help *****\n\n\n                                 Arkanoid\n\n"
                + " Instructions :\n\n Move the board to left and right to prevent falling\n"
                +" the ball by Left and Right keys. Just this :) \n\n I forgot to say there are some gifts for you!\n"
                + " Enjoy the game :D        ";
        text.setText(txt);
        text.setBounds(100, 70, 400, 400);
        text.setBackground(new Color(238, 84, 69));
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 20));


        panel1.add(text);
        panel2.add(btn, BorderLayout.NORTH);

        frame.add(panel1, BorderLayout.SOUTH);
        frame.add(panel2, BorderLayout.CENTER);
    }


    public void setListener(Listener listener){
        this.listener = listener;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn){
            listener.listen("Help");
        }
    }
}
