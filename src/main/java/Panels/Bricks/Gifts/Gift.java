package Panels.Bricks.Gifts;

import Panels.Play.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Gift {

    public GiftType giftType;
    public int positionX;
    public int positionY;
    public int diameter;
    public Color color;
    public int speed;
    public ImageIcon icon;

    public Gift(GiftType giftType, int positionX, int positionY) {
        this.giftType  = giftType;
        this.positionX = positionX;
        this.positionY = positionY;
        this.diameter  = 15;
        this.speed     = 1;
        switch (giftType){
            case FIREBALL      -> this.icon = new ImageIcon("./images/Fire.png");
            case MULTIPLEBALLS -> this.icon = new ImageIcon("./images/multiple_balls.png");
            case LARGEBOARD    -> this.icon = new ImageIcon("./images/large_board.png");
            case SMALLBOARD    -> this.icon = new ImageIcon("./images/small_board.png");
            case FASTBALL      -> this.icon = new ImageIcon("./images/speed.png");
            case SLOWBALL      -> this.icon = new ImageIcon("./images/slow.png");
            case CONFUSEDBOARD -> this.icon = new ImageIcon("./images/confused.png");
            case RANDOMGIFT    -> this.icon = new ImageIcon("./images/gift.png");
        }
    }

    public void fall(Graphics2D g){
        if (this.positionY <= 500){
            this.positionY += this.speed;
            g.drawImage(icon.getImage(), this.positionX, this.positionY, null);
            if (hasGotten()){
                this.action(giftType);
            }
        }
    }

    public boolean hasGotten(){
        Rectangle gift = new Rectangle(this.positionX, this.positionY, this.diameter, this.diameter);
        int x      = GamePanel.board1.positionX;
        int y      = GamePanel.board1.positionY;
        int width  = GamePanel.board1.width;
        int height = GamePanel.board1.height;
        Rectangle board = new Rectangle(x, y, width, height);
        return gift.intersects(board);
    }

    public void action(GiftType giftType){
        switch (giftType){
            case FIREBALL ->      GamePanel.ball1.fireBall();
            case MULTIPLEBALLS -> GamePanel.ball1.multipleBalls();
            case LARGEBOARD ->    GamePanel.board1.changeLength(GiftType.LARGEBOARD);
            case SMALLBOARD ->    GamePanel.board1.changeLength(GiftType.SMALLBOARD);
            case FASTBALL ->      GamePanel.ball1.changeSpeed(GiftType.FASTBALL);
            case SLOWBALL ->      GamePanel.ball1.changeSpeed(GiftType.SLOWBALL);
            case CONFUSEDBOARD -> GamePanel.board1.confuse();
            case RANDOMGIFT ->    this.action(GiftType.getRandomGift());
        }
        this.positionY = 550;
    }
}
