package Panels.Play;

import Application.Listener;
import Frames.MainFrame;
import Panels.Bricks.Brick;
import Panels.Bricks.BrickType;
import Panels.Bricks.Gifts.Gift;
import Panels.Bricks.Gifts.GiftType;
import Panels.Objects.Ball;
import Panels.Objects.Board;
import Panels.Save.Save;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends PlayNewGame implements Runnable {

    public static GamePanel thisGamePanel;
    public long time;
    protected long timer;
    public JFrame pause;
    public long totalTime;
    public Thread gameThread;
    private int score = 0;
    public boolean play = false;
    public Map map;
    private int rows;
    public int columns;
    public boolean flag = false;
    public Brick[][] bricks;
    public ArrayList<Gift> gifts;
    public long tempTime;
    public boolean isPaused;
    public boolean firstPause;
    public Pause pausePanel;

    public Ball ball;
    public ArrayList<Ball> balls;
    public static Ball ball1;
    public static Board board1;
    public Board board;

    public GamePanel(Level level, boolean isNew) {
        thisGamePanel = this;
        this.gameLevel = level;
        this.addKeyListener(this);
        gifts = new ArrayList<>();
        tempTime = 0;
        if (isNew){
            balls = new ArrayList<>();
            ball = new Ball();
            balls.add(ball);
            board = new Board();
            //this.gift = null;
            switch (gameLevel){
                case EASY -> {
                    rows = 4;
                    columns = 9;
                }
                case NORMAL -> {
                    rows = 5;
                    columns = 9;
                }
                case HARD -> {
                    rows = 6;
                    columns = 9;
                }
            }
            init();
        }
    }

    @Override
    public void addNotify(){
        super.addNotify();
        ball1 = ball;
        board1 = board;
        if (gameThread == null){
            gameThread = new Thread(this::run);
        }
        gameThread.start();
    }

    @Override
    public void onDraw(Graphics2D g) {
        // Game background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 480, 620);

        // Menu background
        g.setColor(new Color(1, 32, 94));
        g.fillRect(480, 0, 140, 620);

        g.setColor(new Color(255, 189, 2));
        g.fillRect(490, 505, 105, 50);


        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("Press Esc to", 490, 525);


        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("     pause", 490, 545);


        // Drawing map
        map.draw(g);

        // Borders
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 2, 620);
        g.fillRect(0, 0, 620, 2);
        g.fillRect(480, 0, 2, 620);
        g.fillRect(0, 565, 620, 2);
        g.fillRect(603, 0, 2, 620);

        // Score border
        g.setColor(new Color(246, 243, 6));
        g.drawRect(483, 135, 115, 35);

        // Timer border
        g.setColor(new Color(246, 243, 6));
        g.drawRect(490, 50, 105, 25);

        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 22));
        g.drawString("Score : " + score, 485, 160);

        // Health
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 22));
        g.drawString("Health : " + this.map.health, 490, 260);

        // Health Border
        g.setColor(new Color(246, 243, 6));
        g.drawRect(487, 235, 105, 35);

        // Timer
        if (!isPaused){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", Font.BOLD, 17));
            long showTime = System.currentTimeMillis() - tempTime;
            g.drawString("Time : " + showTime/(1000*60) + ":" + (showTime/1000) % 60, 500, 70);
        }

        // Board
        board.draw(g);

        // Ball
        if (!balls.isEmpty()){
            for (Ball ball : balls) {
                ball.draw(g);
            }
        }

        // Gift
        if (!gifts.isEmpty() && play && !isPaused){
            for (int i = 0; i < gifts.size(); i++){
                if (gifts.get(i).positionY <= 500){
                    gifts.get(i).fall(g);
                }
                else {
                    gifts.remove(gifts.get(i));
                }
            }
        }

        //play = false;
        balls.removeIf(thisBall -> thisBall.positionY >= 550 && thisBall.speedY != 0 && thisBall.speedX != 0);

        if (balls.isEmpty()){
            isPaused = true;
            firstPause = true;
            ball = null;
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString("Game Over, Score : " + score, 105, 250);
            if (this.map.health > 0){
                g.setFont(new Font("Times New Roman", Font.BOLD, 20));
                g.drawString("Press Enter to Restart", 140, 270);
            } else {
                g.setFont(new Font("Times New Roman", Font.BOLD, 20));
                g.drawString("Press Enter to Start a new game", 120, 270);
                this.map.health = 3;
            }
        }

        getTotalBricks();

        if (map.totalBricks <= 0){
            play = false;
            ball.speedX = 0;
            ball.speedY = 0;

            g.setColor(Color.GREEN);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString("        Win!, Score : " + score, 105, 250);

            g.setFont(new Font("Times New Roman", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 140, 270);

            save(true);
        }

        g.dispose();
    }

    @Override
    protected void onKeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT){
            if (!board.isConfused){
                moveRight();
            } else {
                moveLeft();
            }
        }
        if (key == KeyEvent.VK_LEFT){
            if (!board.isConfused){
                moveLeft();
            } else {
                moveRight();
            }
        }
        if (key == KeyEvent.VK_ENTER){
            if (isPaused) {
                this.map.health--;
                MainFrame frame = MainFrame.tempFrame;
                frame.getContentPane().removeAll();
                GamePanel gamePanel;
                if (this.map.health == 0){
                    gamePanel = new GamePanel(this.gameLevel, true);
                } else {
                    gamePanel = new GamePanel(this.gameLevel, false);
                    gamePanel.play = true;
                    gamePanel.score = score;
                    gamePanel.time = time;
                    gamePanel.map = map;
                    gamePanel.isPaused = false;
                    gamePanel.map.health = this.map.health;
                    gamePanel.balls = new ArrayList<>();
                    ball = new Ball();
                    gamePanel.ball = ball;
                    gamePanel.balls.add(ball);
                    //ball.speedX = gamePanel.ball.speedX;
                    //ball.speedY = gamePanel.ball.speedY;
                    gamePanel.board = new Board();
                    gamePanel.totalTime = totalTime;

                    switch (gameLevel){
                        case EASY -> {
                            gamePanel.rows = 4;
                            gamePanel.columns = 9;
                        }
                        case NORMAL -> {
                            gamePanel.rows = 5;
                            gamePanel.columns = 9;
                        }
                        case HARD -> {
                            gamePanel.rows = 6;
                            gamePanel.columns = 9;
                        }
                    }



                }
                frame.add(gamePanel);
                frame.setFocusable(true);
                frame.requestFocus();
                frame.addKeyListener(gamePanel);
                frame.repaint();
                frame.revalidate();
                isPaused = false;
                //play = true;
            }
        }
        if (key == KeyEvent.VK_ESCAPE){
            if (!isPaused){
                tempTime = System.currentTimeMillis();
            }
            firstPause = true;
            isPaused = true;
            pause = new JFrame("Pause");
            pause.setBounds(0, 0, 200, 250);
            pause.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pause.setLocationRelativeTo(null);
            pause.setResizable(false);
            pause.setLayout(null);
            pause.setVisible(true);
            pausePanel = new Pause();
            pause.add(pausePanel);
            pausePanel.addListener(new Listener() {
                @Override
                public void listen(String string) {
                    if (string.equals("Resume")){
                        pause.dispose();
                        pause = null;
                        pausePanel = null;
                        play = true;
                        isPaused = false;
                        firstPause = false;
                    }
                    if (string.equals("Save")){
                        save(false);
                        //pause.dispose();
                    }
                    if (string.equals("Exit")){
                        pause.dispose();
                        MainFrame.tempFrame.dispose();
                    }
                    if (string.equals("Restart")){
                        MainFrame frame = MainFrame.tempFrame;
                        frame.getContentPane().removeAll();
                        GamePanel gamePanel = new GamePanel(gameLevel, true);
                        frame.add(gamePanel);
                        frame.setFocusable(true);
                        frame.requestFocus();
                        frame.addKeyListener(gamePanel);
                        frame.repaint();
                        frame.revalidate();
                        isPaused = false;
                        pause.dispose();
                    }
                }
            });
        }
    }

    @Override
    public void run() {
        timer = System.currentTimeMillis();
        bricks = map.bricks;
        play = true;
        tempTime = System.currentTimeMillis();
        while (play){

            if (!isPaused){
                if (System.currentTimeMillis() - time >= 40000){
                    updateMap();
                    time = System.currentTimeMillis();
                }
                bricks = map.bricks;

                long startTime = System.currentTimeMillis();
                updateGame();
                long endTime = System.currentTimeMillis();
                long milliSeconds = 1000L;
                int FPS = 100;
                long duration = endTime - startTime;
                long waitTime = milliSeconds/FPS - duration / milliSeconds;
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (firstPause){
                    time = System.currentTimeMillis();
                    totalTime = System.currentTimeMillis();
                    firstPause = false;
                }
            }

            renderGame();
        }
    }

    private void updateGame() {
        if (play){
            for (Ball thisBall : balls){
                Rectangle ballBorder  = new Rectangle(thisBall.positionX, thisBall.positionY, thisBall.diameter,
                        thisBall.diameter);
                Rectangle boardBorder = new Rectangle(board.positionX, board.positionY,
                        board.width + board.changeLength, board.height);
                if (ballBorder.intersects(boardBorder)){


                    int len5 = (board.width + board.changeLength) / 5;
                    int x1 = board.positionX;
                    int x2 = x1 + len5;
                    int x3 = x2 + len5;
                    int x4 = x3 + len5;
                    int x5 = x4 + len5;
                    int x6 = x5 + len5;
                    int radius = thisBall.diameter / 2;
                    int xBall = thisBall.positionX + radius;

                    boolean flag1  =  (xBall >= x1) && (xBall < x2)  && (thisBall.speedX > 0);
                    boolean flag2  =  (xBall >= x2) && (xBall < x3)  && (thisBall.speedX > 0);
                    boolean flag3  =  (xBall >= x3) && (xBall < x4)  && (thisBall.speedX > 0);
                    boolean flag4  =  (xBall >= x4) && (xBall < x5)  && (thisBall.speedX > 0);
                    boolean flag5  =  (xBall >= x5) && (xBall <= x6) && (thisBall.speedX > 0);

                    boolean flag6  =  (xBall >= x1) && (xBall < x2)  && (thisBall.speedX < 0);
                    boolean flag7  =  (xBall >= x2) && (xBall < x3)  && (thisBall.speedX < 0);
                    boolean flag8  =  (xBall >= x3) && (xBall < x4)  && (thisBall.speedX < 0);
                    boolean flag9  =  (xBall >= x4) && (xBall < x5)  && (thisBall.speedX < 0);
                    boolean flag10  = (xBall >= x5) && (xBall <= x6) && (thisBall.speedX < 0);

                    if ((flag1 || flag10) && thisBall.speedY > 0){
                        int initialSpeedX = thisBall.speedX;
                        if (thisBall.speedX <= 2 && thisBall.speedX >= -2){
                            thisBall.speedX = thisBall.speedX / Math.abs(thisBall.speedX);
                        } else {
                            if (thisBall.speedX < 0){
                                thisBall.speedX += 2;
                            } else {
                                thisBall.speedX -= 2;
                            }
                        }
                        thisBall.speedY = (int) ((-1) * Math.abs(Math.sqrt(thisBall.speedY * thisBall.speedY
                                + initialSpeedX * initialSpeedX - thisBall.speedX * thisBall.speedX)));
                    } else if ((flag2 || flag9) && thisBall.speedY > 0){
                        int initialSpeedX = thisBall.speedX;
                        if (thisBall.speedX > 1 || thisBall.speedX < -1){
                            if (thisBall.speedX < 0){
                                thisBall.speedX++;
                            } else {
                                thisBall.speedX--;
                            }
                        }
                        thisBall.speedY = (int) ((-1) * Math.abs(Math.sqrt(thisBall.speedY * thisBall.speedY
                                + initialSpeedX * initialSpeedX - thisBall.speedX * thisBall.speedX)));
                    } else if ((flag3 || flag8) && thisBall.speedY > 0){
                        thisBall.speedY = -thisBall.speedY;
                    } else if ((flag4 || flag7) && thisBall.speedY > 0){
                        int sign = thisBall.speedX / Math.abs(thisBall.speedX);
                        int initialSpeedY = thisBall.speedY;
                        if (thisBall.speedY > 1){
                            thisBall.speedY--;
                            thisBall.speedY = -thisBall.speedY;
                        }
                        thisBall.speedX = (int) (Math.abs(Math.sqrt(thisBall.speedX * thisBall.speedX
                                + initialSpeedY * initialSpeedY - thisBall.speedY * thisBall.speedY)));
                        thisBall.speedX *= sign;
                    } else if ((flag5 || flag6) && thisBall.speedY > 0){
                        int sign = thisBall.speedX / Math.abs(thisBall.speedX);
                        int initialSpeedY = thisBall.speedY;
                        if (thisBall.speedY <= 2){
                            thisBall.speedY = -1;
                        } else {
                            thisBall.speedY -= 2;
                            thisBall.speedY = -thisBall.speedY;
                        }
                        thisBall.speedX = (int) (Math.abs(Math.sqrt(thisBall.speedX * thisBall.speedX
                                + initialSpeedY * initialSpeedY - thisBall.speedY * thisBall.speedY)));
                        thisBall.speedX *= sign;
                    } else if (xBall < x1 || xBall > x6){
                        thisBall.speedX = - thisBall.speedX;
                    }
                }

                checkIntersection();

                thisBall.positionX += thisBall.speedX;
                thisBall.positionY += thisBall.speedY;

                if (thisBall.positionX < 8 || thisBall.positionX > 465){
                    thisBall.speedX = - thisBall.speedX;
                }
                if (thisBall.positionY < 8){
                    thisBall.speedY = - thisBall.speedY;
                }
            }
        }
    }

    private void renderGame(){
        this.revalidate();
        this.repaint();
    }

    private void moveRight(){
        play = true;
        if (board.positionX >= 480 - board.width - board.changeLength){
            board.positionX = 480 - board.width - board.changeLength;
        } else {
            board.positionX += 15;
        }
    }

    private void moveLeft(){
        play = true;
        if (board.positionX <= 6){
            board.positionX = 6;
        } else {
            board.positionX -= 15;
        }
    }

    private void init() {
        isPaused = false;
        totalTime = System.currentTimeMillis();
        play = true;
        time = totalTime;
        map = new Map(this.gameLevel, true);
        map.ball = ball;
        map.board = board;
        ball1 = ball;
        board1 = board;
    }

    private void checkIntersection(){
        A: for (Brick[] brick : bricks) {
            for (int j = 0; j < map.columns; j++) {
                if (brick[j].health > 0 && !brick[j].shine) {
                    for (Ball thisBall : balls){
                        Rectangle ballBorder = new Rectangle(thisBall.positionX, thisBall.positionY, thisBall.diameter,
                                thisBall.diameter);
                        int brickX = brick[j].positionX;
                        int brickY = brick[j].positionY;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        Rectangle brickBorder = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                        if (ballBorder.intersects(brickBorder)) {
                            if (brick[j].isUnvisible){
                                brick[j].color = new Color(64, 232, 198);
                                try {
                                    Thread.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (thisBall.isFireMode){
                                brick[j].health = 0;
                            } else {
                                brick[j].health--;
                            }
                            if (brick[j].health == 0) {
                                if (brick[j].gift != null){
                                    gifts.add(brick[j].gift);
                                }
                                map.totalBricks--;
                            } else if (brick[j].brickType == BrickType.WOOD) {
                                brick[j].color = new Color(147, 83, 26);
                            }
                            switch (this.gameLevel) {
                                case EASY -> {
                                    score += 5;
                                }
                                case NORMAL -> {
                                    score += 10;
                                }
                                case HARD -> {
                                    score += 15;
                                }
                            }
                            flag = true;

                            if (!thisBall.isFireMode){
                                Rectangle intersection = ballBorder.intersection(brickBorder);
                                if (intersection.width >= intersection.height){
                                    thisBall.speedY = -thisBall.speedY;
                                }
                                if (intersection.height >= intersection.width){
                                    thisBall.speedX = -thisBall.speedX;
                                }
                            }
                            break A;
                        }
                    }
                }
            }
        }

    }

    public void updateMap(){
        ArrayList<Integer> newRowGlassIndexes = new ArrayList<>();
        ArrayList<Integer> newRowWoodIndexes = new ArrayList<>();
        int newRowWood = 0;
        int newRowGlass = 0;
        switch (gameLevel){
            case EASY, NORMAL -> {
                newRowGlass     = 7;
                newRowWood      = 2;
            }
            case HARD -> {
                newRowGlass     = 6;
                newRowWood      = 3;
            }
        }
        Brick[][] newBricks = new Brick[bricks.length + 1][columns];
        while (newRowGlassIndexes.size() < newRowGlass){
            Random random = new Random();
            int n = (int) (random.nextDouble() * 9);
            n = n % 9;
            if (!newRowGlassIndexes.contains(n)){
                newRowGlassIndexes.add(n);
                Brick temp = new Brick(n * map.brickWidth + 30,  30, BrickType.GLASS);
                temp.isUnvisible = false;
                newBricks[0][n] = temp;
            }
        }
        while (newRowWoodIndexes.size() < newRowWood){
            Random random = new Random();
            int n = (int) (random.nextDouble() * 9);
            n = n % 9;
            if (!newRowWoodIndexes.contains(n) && !newRowGlassIndexes.contains(n)){
                newRowWoodIndexes.add(n);
                Brick temp = new Brick(n * map.brickWidth + 30,  30, BrickType.WOOD);
                temp.isUnvisible = false;
                newBricks[0][n] = temp;
            }
        }

        for (int i = 0; i < map.bricks.length; i++){
            for (int j = 0; j < map.bricks[0].length; j++){
                newBricks[i + 1][j] = map.bricks[i][j];
                newBricks[i + 1][j].positionY += 20;
            }
        }

        Random random = new Random();
        int n = (int) (random.nextDouble() * columns);
        bricks[0][n].gift = new Gift(GiftType.getRandomGift(), bricks[0][n].positionX, bricks[0][n].positionY);
        int k = (int) (random.nextDouble() * columns);
        while (k == n){
            k = (int) (random.nextDouble() * columns);
        }
        bricks[0][k].gift = new Gift(GiftType.getRandomGift(), bricks[0][k].positionX, bricks[0][k].positionY);

        bricks = newBricks;
        map.bricks = newBricks;

    }

    private void getTotalBricks() {
        bricks = map.bricks;
        int totalBricks = 0;
        for (int i = 0; i < bricks.length; i++){
            for (int j = 0; j < bricks[0].length; j++){
                if (bricks[i][j].health > 0){
                    totalBricks++;
                }
            }
        }
        map.totalBricks = totalBricks;
    }

    public void save(boolean isFinished){
        JFrame saveFrame = new JFrame();
        saveFrame.setBounds(0, 0, 300, 320);
        saveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        saveFrame.setLocationRelativeTo(null);
        saveFrame.setResizable(false);
        saveFrame.setLayout(null);
        saveFrame.setVisible(true);

        Save save = new Save(map, score);
        saveFrame.add(save);
        save.addListener(new Listener() {
            @Override
            public void listen(String string) {
                if (string.equals("Save")){
                    map.ball = ball;
                    map.board = board;
                    save.name = save.textField.getText();
                    save.action(isFinished);
                    saveFrame.dispose();
                }
            }
        });
    }

}
