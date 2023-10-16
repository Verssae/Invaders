package entity;

import entity.Item;

import java.awt.*;

public class Coin extends Item {

    public int coin = 0;
    public int value = 10;

    /**
     * Constructor, establishes the Item's properties.
     * and Set sprite dot image which can find what Item it ts.
     *
     * @param positionX Initial position of the Item in the X axis.
     * @param positionY Initial position of the Item in the Y axis.
     */
    public Coin(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public int addCoin(int addcoin) {
        coin += addcoin;
        return coin;
    }

    public int minuscoin(int minuscoin) {
        coin -= minuscoin;
        return coin;
    }

}
