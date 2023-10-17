package entity;
import java.util.Random;

public class Coin {

    public int coin = 0;

    public int addCoin() {
        Random random = new Random();
        int addCoin = random.nextInt(1, 11); //
        coin += addCoin;
        return coin;
    }

    public int minusCoin(int cost) { // when buy an item
        coin -= cost;
        return coin;
    }

}