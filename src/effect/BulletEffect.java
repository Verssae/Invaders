package effect;

import entity.Bullet;
import entity.BulletPool;
import entity.BulletY;

import java.util.Set;

public class BulletEffect extends Effect {
    /**
     * temp Bullet
     */
    private Bullet bullet;
    /**
     * constructor
     */
    public BulletEffect(Bullet bullet){
        this.bullet = bullet;
    }
    /**
     * when kill enemy, bullet is diverged with two with speed -1
     */
    public void splashEffect(Set<Bullet> bullets){
        if (!this.buffSplashEffectCooldown.checkFinished()) {
            Bullet n1, n2;
            n1 = BulletPool.getBullet(bullet.getPositionX() + bullet.getWidth() / 2 + 15, bullet.getPositionY(), -1, bullet.getDamage());
            n2 = BulletPool.getBullet(bullet.getPositionX() + bullet.getWidth() / 2 - 15, bullet.getPositionY(), -1, bullet.getDamage());
            n1.setEffectBullet(1);
            n2.setEffectBullet(1);
            bullets.add(n1);
            bullets.add(n2);
        }
    }
}
