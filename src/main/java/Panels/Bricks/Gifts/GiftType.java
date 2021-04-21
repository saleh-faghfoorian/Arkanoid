package Panels.Bricks.Gifts;

import java.util.Random;

public enum GiftType {
    FIREBALL,
    MULTIPLEBALLS,
    LARGEBOARD,
    SMALLBOARD,
    FASTBALL,
    SLOWBALL,
    CONFUSEDBOARD,
    RANDOMGIFT;


    public static GiftType getRandomGift(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

}
