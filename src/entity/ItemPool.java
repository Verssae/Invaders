package entity;

import java.util.HashSet;
import java.util.Set;

public class ItemPool {
    /** Set of already created items opearate like bulletpool*/
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
    public static Item getItem(final int positionX,
                               final int positionY) {
        Item item;
        if (!pool.isEmpty()) {
            item = pool.iterator().next();
            pool.remove(item);
            item.setPositionX(positionX - item.getWidth() / 2);
            item.setPositionY(positionY);
            item.setSprite();
        } else {
            item = new Item(positionX, positionY);
            item.setPositionX(positionX - item.getWidth() / 2);
        }
        return item;
    }

    /**
     * Adds one or more bullets to the list of available ones.
     * @param item
     *              Items to recycle.
     */
    public static void recycle(final Set<Item> item) { pool.addAll(item); }
}
