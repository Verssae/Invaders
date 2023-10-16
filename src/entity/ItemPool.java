package entity;

import java.util.HashSet;
import java.util.Set;

public class ItemPool {
    /** Set of already created items opearate like itempool*/
    private static Set<Item> pool = new HashSet<Item>();

    /** not used constructor */
    private ItemPool() {

    }
    /**
     * Return an instance of item that can use ( or reuse ).
     *
     * @param positionX
     *              PositionX of where item is first located.
     * @param positionY
     *              PositionY of where item is first located.s
     *
     * @return Item
     * */
    public static Item getBuffItem(final int positionX,
                               final int positionY) {
        Item buff;
        if (!pool.isEmpty()) {
            buff = pool.iterator().next();
            pool.remove(buff);
            buff.setPositionX(positionX - buff.getWidth() / 2);
            buff.setPositionY(positionY);
            buff.setSprite(Math.random() < 0.5 ? 0 : 1);
            buff.CoolReset();
            buff.setDestroy(false);
        } else {
            buff = new BuffItem(positionX, positionY);
            buff.setPositionX(positionX - buff.getWidth() / 2);
        }
        return buff;
    }

    public static Item getDeBuffItem(final int positionX,
                                   final int positionY) {
        Item debuff;
        if (!pool.isEmpty()) {
            debuff = pool.iterator().next();
            pool.remove(debuff);
            debuff.setPositionX(positionX - debuff.getWidth() / 2);
            debuff.setPositionY(positionY);
            debuff.setSprite(Math.random() < 0.5 ? 2 : 3);
            debuff.CoolReset();
            debuff.setDestroy(false);
        } else {
            debuff = new DeBuffItem(positionX, positionY);
            debuff.setPositionX(positionX - debuff.getWidth() / 2);
        }
        return debuff;
    }

    public static Item getCoin(final int positionX,
                               final int positionY) {
        Item coin;
        if (!pool.isEmpty()) {
            coin = pool.iterator().next();
            pool.remove(coin);
            coin.setPositionX(positionX - coin.getWidth() / 2);
            coin.setPositionY(positionY);
            coin.CoolReset();
            coin.setDestroy(false);
        } else {
            coin = new Coin(positionX, positionY);
            coin.setPositionX(positionX - coin.getWidth() / 2);
        }
        return coin;
    }

    public static Item getEnhanceStone(final int positionX,
                               final int positionY) {
        Item enhance;
        if (!pool.isEmpty()) {
            enhance = pool.iterator().next();
            pool.remove(enhance);
            enhance.setPositionX(positionX - enhance.getWidth() / 2);
            enhance.setPositionY(positionY);
            enhance.CoolReset();
            enhance.setDestroy(false);
        } else {
            enhance = new EnhanceStone(positionX, positionY);
            enhance.setPositionX(positionX - enhance.getWidth() / 2);
        }
        return enhance;
    }

    /**
     * Adds one or more bullets to the list of available ones.
     * @param item
     *              Items to recycle.
     */
    public static void recycle(final Set<Item> item) { pool.addAll(item); }
}
