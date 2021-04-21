package Panels.Save;

import Frames.MainFrame;
import Panels.Bricks.Brick;
import Panels.Bricks.BrickType;
import Panels.Bricks.Gifts.Gift;
import Panels.Bricks.Gifts.GiftType;
import Panels.Objects.Ball;
import Panels.Objects.Board;
import Panels.Play.GamePanel;
import Panels.Play.Level;
import Panels.Play.Map;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Load extends JPanel {

    public Map map;
    public Ball ball;
    public Board board;
    public GamePanel gamePanel;

    public Load(int userId, int mapId) {
        getBall(userId, mapId);
        getBoard(userId, mapId);
        getMap(userId, mapId);
        gamePanel = new GamePanel(map.level, false);
        gamePanel.map = map;
        gamePanel.play = true;
        gamePanel.columns = map.columns;
        gamePanel.isPaused = false;
        gamePanel.firstPause = false;
        gamePanel.totalTime = System.currentTimeMillis();
        gamePanel.time = gamePanel.totalTime;
        gamePanel.ball = ball;
        gamePanel.balls = new ArrayList<>();
        gamePanel.balls.add(ball);
        gamePanel.board = board;
        gamePanel.gameThread = null;
    }

    public void getBall(int userId, int mapId){
        ball = new Ball();
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            String sql1 = "SELECT * FROM ball WHERE user_id = " + userId + " AND map_id = " + mapId;

            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sql1);
            while (resultSet.next()){
                ball.positionX  = resultSet.getInt("position_x");
                ball.positionY  = resultSet.getInt("position_y");
                ball.speedX     = resultSet.getInt("speed_x");
                ball.speedY     = resultSet.getInt("speed_y");
                ball.isFireMode = resultSet.getBoolean("is_fire");
                ball.isSpeedy   = resultSet.getBoolean("is_speedy");
                ball.isFast     = resultSet.getBoolean("is_fast");
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }

    public void getBoard(int userId, int mapId){
        board = new Board();
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            LocalDateTime now = LocalDateTime.now();
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            String sql1 = "SELECT * FROM board WHERE user_id = " + userId + " AND map_id = " + mapId;

            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sql1);
            while (resultSet.next()){
                board.positionX    = resultSet.getInt("position_x");
                board.positionY    = resultSet.getInt("position_y");
                board.height       = resultSet.getInt("height");
                board.width        = resultSet.getInt("width");
                board.changeLength = resultSet.getInt("change_length");
                board.isConfused   = resultSet.getBoolean("is_confused");
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }

    public void getMap(int userId, int mapId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/arkanoid";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            String sql1 = "SELECT * FROM map WHERE user_id = " + userId + " AND map_id = " + mapId;

            int rows = 0, columns = 0;
            Level level = null;

            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            while (resultSet1.next()){
                rows    = resultSet1.getInt("rows");
                columns = resultSet1.getInt("columns");
                switch (resultSet1.getString("level")){
                    case "EASY"   : level = Level.EASY;
                    case "NORMAL" : level = Level.NORMAL;
                    case "HARD"   : level = Level.HARD;
                }
            }
            map = new Map(level, false);
            Brick[][] bricks = new Brick[rows][columns];

            String sql2 = "SELECT * FROM bricks WHERE user_id = " + userId + " AND map_id = " + mapId;
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(sql2);
            int i = 0, j = 0;
            while (resultSet2.next()){
                int positionX = resultSet2.getInt("position_x");
                int positionY = resultSet2.getInt("position_y");
                BrickType brickType;
                if (resultSet2.getString("brick_type").equals("GLASS")){
                    brickType = BrickType.GLASS;
                } else {
                    brickType = BrickType.WOOD;
                }
                Brick tempBrick       = new Brick(positionX, positionY, brickType);
                tempBrick.health      = resultSet2.getInt("health");
                tempBrick.brickWidth  = resultSet2.getInt("width");
                tempBrick.brickHeight = resultSet2.getInt("height");
                tempBrick.isUnvisible = resultSet2.getBoolean("is_unvisible");
                tempBrick.isShiny     = resultSet2.getBoolean("is_shiny");
                boolean flag1 = resultSet2.getString("gift_type").equals("null");
                boolean flag2 = resultSet2.getString("gift_type") == null;
                if (!flag1 && ! flag2){
                    switch (resultSet2.getString("gift_type")){
                        case "FIREBALL" : tempBrick.gift = new Gift(GiftType.FIREBALL, positionX, positionY);
                        case "MULTIPLEBALLS" : tempBrick.gift = new Gift(GiftType.MULTIPLEBALLS, positionX, positionY);
                        case "LARGEBOARD" : tempBrick.gift = new Gift(GiftType.LARGEBOARD, positionX, positionY);
                        case "SMALLBOARD" : tempBrick.gift = new Gift(GiftType.SMALLBOARD, positionX, positionY);
                        case "FASTBALL" : tempBrick.gift = new Gift(GiftType.FASTBALL, positionX, positionY);
                        case "SLOWBALL" : tempBrick.gift = new Gift(GiftType.SLOWBALL, positionX, positionY);
                        case "CONFUSEDBOARD" : tempBrick.gift = new Gift(GiftType.CONFUSEDBOARD, positionX, positionY);
                        case "RANDOMGIFT" : tempBrick.gift = new Gift(GiftType.RANDOMGIFT, positionX, positionY);
                    }
                }
                int R = resultSet2.getInt("R");
                int G = resultSet2.getInt("G");
                int B = resultSet2.getInt("B");
                tempBrick.color = new Color(R, G, B);
                bricks[i][j] = tempBrick;
                j++;
                if (j == columns){
                    i++;
                    j = 0;
                }
                if (i == columns){
                    break;
                }
            }


            map.bricks = bricks;
            connection.close();
        } catch (SQLException e){
            System.out.println("Error in connecting to database");
            e.printStackTrace();
        }
    }

    public void action(){
        gamePanel.addNotify();
    }
}
