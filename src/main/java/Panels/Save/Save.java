package Panels.Save;

import Application.Listener;
import Panels.Play.Map;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Save extends JPanel implements ActionListener {

    public String name;
    public Map map;
    public int score;
    public JTextArea textArea;
    public TextField textField;
    JButton btn;
    ArrayList<Listener> listeners;

    public Save(Map map, int score) {
        this.name = "";
        this.map   = map;
        this.score = score;
        this.setLayout(null);
        this.setBounds(0, 0, 300, 320);
        this.setBackground(new Color(3, 74, 217));

        listeners = new ArrayList<>();
        textArea  = new JTextArea();
        textField = new TextField();
        btn       = new JButton("Save");


        textArea.setBounds(80, 30, 130, 30);
        textField.setBounds(55, 100, 180, 50);
        btn.setBounds(100, 190, 80, 30);

        textArea.setBackground(new Color(243, 131, 87));
        textArea.setForeground(Color.BLACK);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        btn.setBackground(new Color(255, 189, 2));

        textArea.setText("Enter your name");
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        this.name = textField.getText();

        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.addActionListener(this);
        this.add(btn);
        this.add(textArea);
        this.add(textField);
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Listener listener : listeners){
            if (e.getSource() == btn){
                listener.listen("Save");
            }
        }
    }

    public void action(boolean isFinished){
        int userId, mapId;
        userId = submitRecord(isFinished);
        if (!isFinished){
            mapId = submitMap(userId);
            submitBricks(userId, mapId);
            submitBall(userId, mapId);
            submitBoard(userId, mapId);
        }
    }

    public int submitRecord(boolean isFinished){
        int userId = -1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            String sql1 = "INSERT INTO name (name, score, time, is_finished)"
                    + " VALUES ('" + this.name + "', '" + this.score + "', '" + now + "', '"
                    + isFinished + "')";

            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);

            String sql2 = "SELECT * FROM name";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet = statement2.executeQuery(sql2);
            while (resultSet.next()){
                userId = resultSet.getInt("user_id");
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
        return userId;
    }


    public int submitMap(int userId){
        int mapId = -1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            String sql1 = "INSERT INTO map (user_id, level, health, rows, columns)"
                    + " VALUES ('" + userId + "', '" + map.level + "', '" + map.health + "', '" + map.rows
                    + "', '" + map.columns + "')";

            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);

            String sql2 = "SELECT * FROM map";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet = statement2.executeQuery(sql2);
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


    public void submitBricks(int userId, int mapId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            for (int i = 0; i < map.bricks.length; i++){
                for (int j = 0; j < map.bricks[0].length; j++){
                    String sql;
                    if (map.bricks[i][j].gift != null){
                        sql = "INSERT INTO bricks (user_id, map_id, brick_type, health, position_x, position_y,"
                                + " width, height, is_unvisible, is_shiny, gift_type, R, G, B)"
                                + " VALUES ('" + userId + "', '" + mapId + "', '" + map.bricks[i][j].brickType + "', '"
                                + map.bricks[i][j].health + "', '" + map.bricks[i][j].positionX + "', '"
                                + map.bricks[i][j].positionY + "', '" + map.bricks[i][j].brickWidth + "', '"
                                + map.bricks[i][j].brickHeight + "', '" + map.bricks[i][j].isUnvisible + "', '"
                                + map.bricks[i][j].isShiny + "', '" + map.bricks[i][j].gift.giftType + "', '"
                                + map.bricks[i][j].color.getRed() + "', '" + map.bricks[i][j].color.getGreen() + "', '"
                                + map.bricks[i][j].color.getBlue() + "')";
                    } else {
                        sql = "INSERT INTO bricks (user_id, map_id, brick_type, health, position_x, position_y,"
                                + " width, height, is_unvisible, is_shiny, gift_type, R, G, B)"
                                + " VALUES ('" + userId + "', '" + mapId + "', '" + map.bricks[i][j].brickType + "', '"
                                + map.bricks[i][j].health + "', '" + map.bricks[i][j].positionX + "', '"
                                + map.bricks[i][j].positionY + "', '" + map.bricks[i][j].brickWidth + "', '"
                                + map.bricks[i][j].brickHeight + "', '" + map.bricks[i][j].isUnvisible + "', '"
                                + map.bricks[i][j].isShiny + "', 'null', '" + map.bricks[i][j].color.getRed()
                                + "', '" + map.bricks[i][j].color.getGreen() + "', '"
                                + map.bricks[i][j].color.getBlue() + "')";
                    }

                    Statement statement = connection.createStatement();
                    int rows = statement.executeUpdate(sql);
                }
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }


    public void submitBall(int userId, int mapId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            String sql = "INSERT INTO ball (user_id, map_id, position_x, position_y, speed_x, speed_y, is_fire,"
                    + " is_speedy, is_fast)"
                    + " VALUES ('" + userId + "', '" + mapId + "', '" + map.ball.positionX + "', '" + map.ball.positionY
                    + "', '" + map.ball.speedX + "', '" + map.ball.speedY + "', '" + map.ball.isFireMode + "', '"
                    + map.ball.isSpeedy + "', '" + map.ball.isFast + "')";

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }


    public void submitBoard(int userId, int mapId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            String sql = "INSERT INTO board (user_id, map_id, position_x, position_y, height, width, change_length,"
                    + " is_confused)"
                    + " VALUES ('" + userId + "', '" + mapId + "', '" + map.board.positionX + "', '" + map.board.positionY
                    + "', '" + map.board.height + "', '" + map.board.width + "', '" + map.board.changeLength + "', '"
                    + map.board.isConfused + "')";

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }

}
