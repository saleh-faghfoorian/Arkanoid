package Panels.Objects;

import Panels.Bricks.Gifts.GiftType;
import Panels.Play.GamePanel;

import java.awt.*;

public class Ball {

    public int positionX, positionY, speedX, speedY;
    public int diameter = 15;
    public Color color;
    public Color fireModeColor;
    public boolean isFireMode;
    public boolean isSpeedy;
    public boolean isFast;
    public long timer;
    boolean flag;
    public double speedCoefficient;

    public Ball(){
        this.positionX = 282;
        this.positionY = 463;
        this.speedX = -3;
        this.speedY = -3;
        this.color = Color.GREEN;
        this.fireModeColor = Color.RED;
        this.isFireMode = false;
        this.isSpeedy   = false;
        flag = false;
    }

    public void draw(Graphics2D g) {
        if (flag){
            if (System.currentTimeMillis() - timer >= 7000){
                if (isFireMode){
                    isFireMode = false;
                }
                if (isSpeedy) {
                    if (isFast){
                        if ((int) (speedX * 2 / 3) != 0 && (int) (speedY * 2 / 3) != 0){
                            speedX = speedX * 2 / 3;
                            speedY = speedY * 2 / 3;
                        } else if (speedY - 1 != 0 && speedX - 1 != 0){
                            speedY--;
                            speedX--;
                        }

                    } else {
                        speedX *= speedCoefficient;
                        speedY *= speedCoefficient;
                    }
                }
                flag = false;
            }
        }
        if (isFireMode){
            g.setColor(this.fireModeColor);
        } else {
            g.setColor(this.color);
        }
        g.fillOval(positionX + speedX, positionY + speedY, diameter, diameter);
    }

    public void fireBall(){
        flag = true;
        this.isFireMode = true;
        timer = System.currentTimeMillis();
    }

    public void changeSpeed(GiftType giftType){
        flag = true;
        isSpeedy = true;
        speedCoefficient = 1;
        timer = System.currentTimeMillis();
        if (giftType == GiftType.FASTBALL){
            speedX *= 1.5;
            speedY *= 1.5;
            isFast = true;
        } else if (giftType == GiftType.SLOWBALL){
            if ((int) (speedX / 1.5) != 0 && (int) (speedY / 1.5) != 0){
                speedCoefficient = 1.5;
            } else if ((int) (speedX / 1.4) != 0 && (int) (speedY / 1.4) != 0){
                speedCoefficient = 1.4;
            } else if ((int) (speedX / 1.3) != 0 && (int) (speedY / 1.3) != 0){
                speedCoefficient = 1.3;
            } else if ((int) (speedX / 1.2) != 0 && (int) (speedY / 1.2) != 0){
                speedCoefficient = 1.2;
            } else if ((int) (speedX / 1.1) != 0 && (int) (speedY / 1.1) != 0){
                speedCoefficient = 1.1;
            }
            speedX /= speedCoefficient;
            speedY /= speedCoefficient;
            isFast = false;
        }
    }

    public void multipleBalls(){
        Ball ball1 = new Ball();
        Ball ball2 = new Ball();
        ball1.positionX = GamePanel.board1.positionX + GamePanel.board1.width - diameter;
        ball2.positionX = GamePanel.board1.positionX + GamePanel.board1.width - diameter;
        ball1.speedX = -2;
        ball1.speedY = -3;
        ball2.speedX = 2;
        ball2.speedY = -3;
        GamePanel.thisGamePanel.balls.add(ball1);
        GamePanel.thisGamePanel.balls.add(ball2);
    }


}
