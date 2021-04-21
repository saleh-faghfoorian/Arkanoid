package Panels.Objects;

import Panels.Bricks.Gifts.GiftType;

import java.awt.*;

public class Board {

    public int positionX;
    public int positionY;
    public int height;
    public int width;
    public int changeLength;
    public long timer1, timer2;
    public Color color;
    public boolean isConfused;

    public Board(){
        this.positionX = 240;
        this.positionY = 480;
        this.height = 8;
        this.width = 100;
        this.changeLength = 0;
        this.color = new Color(252, 6, 121);
        this.isConfused = false;
    }

    public void changeLength(GiftType giftType){
        timer1 = System.currentTimeMillis();
        if (giftType == GiftType.LARGEBOARD){
            this.changeLength += 50;
        } else if (giftType == GiftType.SMALLBOARD){
            this.changeLength -= 50;
        }
    }

    public void draw(Graphics2D g) {
        if (System.currentTimeMillis() - timer1 >= 7000){
            changeLength = 0;
        }
        if (System.currentTimeMillis() - timer2 >= 7000){
            this.isConfused = false;
        }
        g.setColor(this.color);
        g.fillRect(positionX, positionY, width + changeLength, height);
    }

    public void confuse(){
        this.isConfused = true;
        timer2 = System.currentTimeMillis();
    }

}
