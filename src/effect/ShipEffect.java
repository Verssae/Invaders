package effect;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.*;
import entity.Bullet;
import entity.BulletY;
import entity.BulletPool;
import entity.Ship;

import java.util.Set;

public class ShipEffect extends Effect{
    /**
     * About the ships to run the effects
     */
    private Ship ship;

    /**
     * @param ship
     *              a ship object
     */
    public ShipEffect(Ship ship) {
        super();
        this.ship = ship;
    }


    /**
     * Bullet shooting effect
     *
     * @param bullets
     *              The factor of the ship's shoot method.
     * @param BULLET_SPEED
     *              the speed of a ship's bullet
     */
    public void shoot(final Set<Bullet> bullets, final int BULLET_SPEED) {
        if (this.getCooldown(SpriteType.Buff_Item).checkFinished())
        {
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), BULLET_SPEED));
        } else {
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2 + 10,
                    ship.getPositionY(), BULLET_SPEED));
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), BULLET_SPEED));
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2 - 10,
                    ship.getPositionY(), BULLET_SPEED));
        }
    }

    public void shootBulletY(final Set<BulletY> bulletsY, final int BULLET_SPEED) {
        if (this.getCooldown(SpriteType.Buff_Item).checkFinished())
        {
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), BULLET_SPEED));
        } else {
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2 + 10,
                    ship.getPositionY(), BULLET_SPEED));
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), BULLET_SPEED));
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2 - 10,
                    ship.getPositionY(), BULLET_SPEED));
        }
    }
}
