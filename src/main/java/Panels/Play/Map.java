package Panels.Play;

import Panels.Bricks.Brick;
import Panels.Bricks.BrickType;
import Panels.Bricks.Gifts.Gift;
import Panels.Bricks.Gifts.GiftType;
import Panels.Objects.Ball;
import Panels.Objects.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    private static int glassBricks;
    private static int woodBricks;
    private static int unVisibleBricks;
    private static int shinyBricks;
    private static int specialBricks;


    public Brick[][] bricks;
    public int brickWidth = 45;
    public int brickHeight = 20;
    public int rows;
    public int columns;
    public int totalBricks;
    public Level level;
    public Ball ball;
    public Board board;
    public int health;



    public Map(Level level, boolean isNew){
        this.level = level;
        this.health = 3;
        switch (level){
            case EASY -> {
                rows = 4;
                glassBricks     = 24;
                woodBricks      = 12;
                unVisibleBricks = 2;
                shinyBricks     = 4;
                specialBricks   = 8;
            }
            case NORMAL -> {
                rows = 5;
                glassBricks     = 28;
                woodBricks      = 17;
                unVisibleBricks = 5;
                shinyBricks     = 8;
                specialBricks   = 7;
            }
            case HARD -> {
                rows = 6;
                glassBricks     = 32;
                woodBricks      = 22;
                unVisibleBricks = 5;
                shinyBricks     = 8;
                specialBricks   = 7;
            }
        }
        columns = 9;
        totalBricks = rows * columns;
        bricks = new Brick[rows][columns];
        if (isNew){
            createMap();
        }
    }

    public void draw(Graphics2D g){
        for (int i = 0; i < bricks.length; i++){
            for (int j = 0; j < columns; j++){
                if (bricks[i][j].health > 0) {
                    bricks[i][j].draw(g);

                    // Gap between bricks
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 30, i * brickHeight + 30, brickWidth, brickHeight);
                }
            }
        }
    }


    public void createMap(){
        ArrayList<Integer> glassBrickIndexes = new ArrayList<>();
        ArrayList<Integer> woodBrickIndexes = new ArrayList<>();
        ArrayList<Integer> unVisibleBrickIndexes = new ArrayList<>();
        ArrayList<Integer> specialBrickIndexes = new ArrayList<>();
        ArrayList<Integer> shinyBricksIndexes = new ArrayList<>();
        while (glassBrickIndexes.size() < glassBricks){
            Random random = new Random();
            int n = (int) (random.nextDouble() * totalBricks);
            if (!glassBrickIndexes.contains(n)){
                glassBrickIndexes.add(n);
                int i = n / 9;
                int j = n % 9;
                if (n == totalBricks){
                    i = rows - 1;
                    j = columns - 1;
                }
                Brick temp = new Brick(j * brickWidth + 30, i * brickHeight + 30, BrickType.GLASS);
                bricks[i][j] = temp;
            }
        }

        while (woodBrickIndexes.size() < woodBricks){
            Random random = new Random();
            int n = (int) (random.nextDouble() * totalBricks);
            if (!woodBrickIndexes.contains(n) && !glassBrickIndexes.contains(n)){
                woodBrickIndexes.add(n);
                int i = n / 9;
                int j = n % 9;
                if (n == totalBricks){
                    i = rows - 1;
                    j = columns - 1;
                }
                Brick temp = new Brick(j * brickWidth + 30, i * brickHeight + 30, BrickType.WOOD);
                bricks[i][j] = temp;
            }
        }

        while (unVisibleBrickIndexes.size() < unVisibleBricks){
            Random random = new Random();
            int n = (int) (random.nextDouble() * totalBricks);
            if (!unVisibleBrickIndexes.contains(n)){
                int i = n / 9;
                int j = n % 9;
                if (bricks[i][j].brickType == BrickType.GLASS){
                    unVisibleBrickIndexes.add(n);
                    bricks[i][j].isUnvisible = true;
                    bricks[i][j].color = Color.BLACK;
                }
            }
        }

        while (shinyBricksIndexes.size() < shinyBricks){
            Random random = new Random();
            int n = (int) (random.nextDouble() * totalBricks);
            if (!shinyBricksIndexes.contains(n) && !unVisibleBrickIndexes.contains(n)){
                shinyBricksIndexes.add(n);
                int i = n / 9;
                int j = n % 9;
                if (n == totalBricks){
                    i = rows - 1;
                    j = columns - 1;
                }
                bricks[i][j].isShiny = true;
                bricks[i][j].shine   = false;
            }
        }

        while (specialBrickIndexes.size() < specialBricks){
            Random random = new Random();
            int n = (int) (random.nextDouble() * totalBricks);
            if (!specialBrickIndexes.contains(n)){
                specialBrickIndexes.add(n);
                int i = n / 9;
                int j = n % 9;
                if (n == totalBricks){
                    i = rows - 1;
                    j = columns - 1;
                }
                int giftPositionX = bricks[i][j].positionX + bricks[i][j].brickWidth/2;
                int giftPositionY = bricks[i][j].positionY + bricks[i][j].brickHeight/2;
                bricks[i][j].gift = new Gift(GiftType.getRandomGift(), giftPositionX, giftPositionY);
            }
        }
    }


}
