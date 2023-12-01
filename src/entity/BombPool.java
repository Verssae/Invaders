package entity;

import java.util.HashSet;
import java.util.Set;
public final class BombPool {
    private static Set<Bomb> pool = new HashSet<Bomb>();

    private BombPool() {

    }

    public static Bomb getBomb(final int positionX,
                               final int positionY, final int speed) {
        Bomb bomb;
        if(!pool.isEmpty()) {
            bomb = pool.iterator().next();
            pool.remove(bomb);
            bomb.setPositionX(positionX - bomb.getWidth() / 2);
            bomb.setPositionY(positionY);
            bomb.setSpeed(speed);
            bomb.setSprite();
            return bomb;
        }
        bomb = new Bomb(positionX, positionY, speed);
        bomb.setPositionX(positionX - bomb.getWidth() / 2);
        return bomb;
    }


    public static void recycle(final Set<Bomb> bombs) {
        pool.addAll(bombs);
    }

}
