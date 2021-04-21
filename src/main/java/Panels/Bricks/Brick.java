package Panels.Bricks;

import Panels.Bricks.Gifts.Gift;
import Panels.Bricks.Gifts.GiftType;

import java.awt.*;

public class Brick {

    public int positionX;
    public int positionY;
    public BrickType brickType;
    public Color color;

    public Gift gift;

    public boolean isShiny;
    public boolean shine;
    public Color shinyColor;

    public boolean isUnvisible;

    public int brickWidth;
    public int brickHeight;
    public int health;
    public long time;

    public Brick(int positionX, int positionY, BrickType brickType) {
        time = System.currentTimeMillis();
        this.positionX   = positionX;
        this.positionY   = positionY;
        this.brickType   = brickType;
        this.gift        = null;
        this.isShiny     = false;
        this.brickWidth  = 45;
        this.brickHeight = 20;

        switch (brickType){
            case GLASS -> {
                this.color = new Color(64, 232, 198);
                this.health = 1;
            }
            case WOOD -> {
                this.color = new Color(212, 133, 62);
                this.health = 2;
            }
        }
        this.shinyColor = Color.BLACK;
    }


    public void draw(Graphics2D g){
        if (this.isShiny){
            if (System.currentTimeMillis() - time >= 2000){
                if (this.shine){
                    g.setColor(this.color);
                } else {
                    g.setColor(this.shinyColor);
                }
                time = System.currentTimeMillis();
                this.shine = !this.shine;
            }
            else {
                if (this.shine){
                    g.setColor(this.shinyColor);
                } else {
                    g.setColor(this.color);
                }
            }
        }
        else {
            g.setColor(this.color);
        }
        g.fillRect(this.positionX, this.positionY, this.brickWidth, this.brickHeight);
    }

}
