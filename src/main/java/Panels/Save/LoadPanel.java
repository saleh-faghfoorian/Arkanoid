package Panels.Save;

import Application.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LoadPanel extends JPanel implements ActionListener {


    public Load load;
    public ArrayList<Integer> userIds;
    public JTextArea title;
    public JTextArea games;
    public JTextField textField;
    public JButton btn1, btn2;
    private ArrayList<Listener> listeners;

    public LoadPanel(){
        textField = new JFormattedTextField();
        title     = new JTextArea();
        games     = new JTextArea();
        btn1      = new JButton("Load Game");
        btn2      = new JButton("Return");
        listeners = new ArrayList<>();

        this.setLayout(null);
        this.setBounds(0, 0, 600, 800);
        this.setBackground(new Color(3, 74, 217));

        title.setBounds(220, 40, 150, 30);
        games.setBounds(0, 120, 600, 400);
        textField.setBounds(240, 630, 120, 50);
        btn1.setBounds(260, 700, 80, 30);
        btn2.setBounds(500, 700, 80, 30);

        title.setBackground(new Color(243, 131, 87));
        games.setBackground(Color.CYAN);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        btn1.setBackground(new Color(255, 189, 2));
        btn2.setBackground(new Color(255, 189, 2));

        textField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        games.setFont(new Font("Times New Roman", Font.BOLD, 15));
        games.setForeground(Color.BLACK);

        title.setText("Load Game");
        showSavedGames();

        btn1.setBorder(BorderFactory.createRaisedBevelBorder());
        btn2.setBorder(BorderFactory.createRaisedBevelBorder());
        btn1.addActionListener(this);
        btn2.addActionListener(this);

        this.add(btn1);
        this.add(btn2);
        this.add(title);
        this.add(games);
        this.add(textField);
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Listener listener : listeners){
            if (e.getSource() == btn1){
                listener.listen("Load");
            }
            if (e.getSource() == btn2){
                listener.listen("Return");
            }
        }
    }

    public void showSavedGames(){
        StringBuilder showSaved = new StringBuilder();
        userIds = new ArrayList<>();
        int counter = 1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);

            String sql1 = "SELECT * FROM name WHERE is_finished = 'false'";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sql1);

            while (resultSet.next()){
                userIds.add(resultSet.getInt("user_id"));
                String name = resultSet.getString("name");
                int score   = resultSet.getInt("score");
                String date = resultSet.getTimestamp("time").toString();
                showSaved.append("\n     " + counter + "             Name : " + name + "             Score : "
                        + score + "             Date : " + date + "\n");
                counter++;
            }

            games.setText(showSaved.toString());

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }

    public int getMapIds(int index){
        int mapId = -1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);

            String sql1 = "SELECT * FROM map WHERE user_id = " + userIds.get(index);
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sql1);

            while (resultSet.next()){
                mapId = resultSet.getInt("map_id");
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
        return mapId;
    }



}
