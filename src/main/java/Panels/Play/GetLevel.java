package Panels.Play;

import Application.Listener;
import Frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GetLevel extends JPanel implements ActionListener {

    // Fields
    private final JButton btn1 = new JButton("Easy");
    private final JButton btn2 = new JButton("Normal");
    private final JButton btn3 = new JButton("Hard");
    private final JButton btn4 = new JButton("Return");
    public LinkedList<Listener> listeners;
    MainFrame frame = MainFrame.tempFrame;

    // Constructor
    public GetLevel() {
        listeners = new LinkedList<>();
        frame.getContentPane().removeAll();
        frame.repaint();
        this.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        JPanel title = new JPanel();
        JPanel up = new JPanel();
        JPanel middle = new JPanel();
        JPanel down = new JPanel();

        top.setLayout(null);
        up.setLayout(null);
        middle.setLayout(null);
        down.setLayout(null);

        top.setBounds(0, 0, 620, 80);
        title.setBounds(110, 10, 400, 60);
        up.setBounds(0, 80, 620, 160);
        middle.setBounds(0, 240, 620, 160);
        down.setBounds(0, 400, 620, 170);

        top.setBackground(new Color(3, 74, 217));
        title.setBackground(new Color(253, 153, 255));
        up.setBackground(new Color(3, 74, 217));
        middle.setBackground(new Color(3, 74, 217));
        down.setBackground(new Color(3, 74, 217));

        JTextArea textArea = new JTextArea();
        String text = "\n\t      Select Level";
        textArea.setText(text);
        textArea.setBounds(10, 0, 380, 40);
        textArea.setBackground(new Color(253, 153, 255));
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        title.add(textArea);

        btn1.setBackground(new Color(71, 239, 10));
        btn2.setBackground(new Color(191, 58, 189));
        btn3.setBackground(new Color(215, 48, 68));
        btn4.setBackground(new Color(255, 189, 2));

        btn1.setBounds(350, 65, 80, 30);
        btn2.setBounds(170, 65, 80, 30);
        btn3.setBounds(350, 65, 80, 30);
        btn4.setBounds(50, 85, 80, 30);

        btn1.setBorder(BorderFactory.createRaisedBevelBorder());
        btn2.setBorder(BorderFactory.createRaisedBevelBorder());
        btn3.setBorder(BorderFactory.createRaisedBevelBorder());
        btn4.setBorder(BorderFactory.createRaisedBevelBorder());

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);

        ImageIcon icon1 = new ImageIcon("./images/Easy.png");
        ImageIcon icon2 = new ImageIcon("./images/Normal.jpg");
        ImageIcon icon3 = new ImageIcon("./images/Hard.jpg");

        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();

        label1.setBounds(450, 1, 125, 157);
        label2.setBounds(35, 1, 125, 157);
        label3.setBounds(450, 1, 125, 157);

        label1.setIcon(icon1);
        label2.setIcon(icon2);
        label3.setIcon(icon3);

        up.add(btn1);
        up.add(label1);

        middle.add(btn2);
        middle.add(label2);

        down.add(btn3);
        down.add(label3);
        down.add(btn4);

        top.add(title);

        frame.add(top);
        frame.add(up);
        frame.add(middle);
        frame.add(down);
    }

    // Listener
    public void addListener(Listener listener){
        listeners.add(listener);
    }

    // Actions
    @Override
    public void actionPerformed(ActionEvent e) {
        for (Listener listener : listeners){
            if (e.getSource() == btn1){
                listener.listen("Easy");
            }
            if (e.getSource() == btn2){
                listener.listen("Normal");
            }
            if (e.getSource() == btn3){
                listener.listen("Hard");
            }
            if (e.getSource() == btn4){
                listener.listen("Return");
            }
        }
    }


}
