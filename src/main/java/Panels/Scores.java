package Panels;

import Application.Listener;
import Frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Scores extends JPanel implements ActionListener {

    private JButton btn;
    private JPanel panel1;
    private JPanel panel2;
    public Listener listener;
    JTextArea text;
    private MainFrame frame = MainFrame.tempFrame;

    public Scores() {
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
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setBackground(new Color(255, 189, 2));
        btn.setBounds(270, 10, 80, 30);
        btn.addActionListener(this);

        text = new JTextArea();
        //text.setText(txt);
        text.setBounds(80, 70, 440, 400);
        text.setBackground(Color.CYAN);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        getScores();

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
            listener.listen("Return");
        }
    }

    public void getScores(){
        StringBuilder topScores = new StringBuilder();
        int counter = 1;
        topScores.append("                         ***** Top Scores *****\n\n");
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);

            String sql1 = "SELECT * FROM name ORDER BY score DESC";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sql1);

            while (resultSet.next()){
                String name = resultSet.getString("name");
                int score   = resultSet.getInt("score");
                topScores.append("\n     " + counter + "             Name : " + name);
                int len = name.length();
                if (len >= 6){
                    topScores.append("\t  ");
                } else {
                    topScores.append("\t\t  ");
                }
                topScores.append("Score : " + score + "\n");
                counter++;
            }

            text.setText(topScores.toString());

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }
}
