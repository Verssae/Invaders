package entity;
import java.util.Random;

public class Coin {

    private int coin = 0;

    public void addCoin(int addCoin) {
        this.coin += addCoin;
    }

    public void minusCoin(int cost) { // when buy an item
        this.coin -= cost;
    }

    public int getCoin() {
        return this.coin;
    }
}