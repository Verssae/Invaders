package entity;

public class Coin {

    public int coin = 0;

    public int addCoin(int addCoin) {
        coin += addCoin;
        return coin;
    }

    public int minusCoin(int minusCoin) {
        coin -= minusCoin;
        return coin;
    }

}