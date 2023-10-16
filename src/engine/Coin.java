package engine;

public class Coin {

    public int coin = 0;

    public int addCoin(int addcoin) {
        coin += addcoin;
        return coin;
    }

    public int minuscoin(int minuscoin) {
        coin -= minuscoin;
        return coin;
    }

}
