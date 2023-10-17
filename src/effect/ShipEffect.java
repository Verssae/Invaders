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
     * shipeffect.Shoot과 비슷하게 move effct를 적용
     * 이 코드에 디버프에 해당하는 것을 작성하면 됨.
     * 그후 ship.shoot과 비슷하게
     * moveRight or Left 등을 수정. -> 스피드를 바로 수정하는거는 static 이랑 private을 건드는 일이라
     * 크게 안건들고 수정하길 바람.
     */

    public void moveRightEffect(){
        if (this.getCooldown(SpriteType.Debuff_Item).checkFinished()) {
            ship.setPositionX(ship.getPositionX() + ship.getSpeed());
        }
        else {
            ship.setPositionX(ship.getPositionX() - ship.getSpeed());
        }
    }

    public void moveLeftEffect(){
        if (this.getCooldown(SpriteType.Debuff_Item).checkFinished()) {
            ship.setPositionX(ship.getPositionX() - ship.getSpeed());
        }
        else {
            ship.setPositionX(ship.getPositionX() + ship.getSpeed());
        }
    }

    /**
     * 이와 비슷하게 여기서도 shoot에서도 Bullet speed를 바꾼다던가
     * shooting interval을 줄인다거나 그러한 쿨타임을 조정 가능.
     * 이에 대해서도 위에 서술한 대로 코드 구현 바람.
     *
     * Bullet shooting effect
     *
     * @param bullets
     *              The factor of the ship's shoot method.
     * @param BULLET_SPEED
     *              the speed of a ship's bullet
     */
    public void shoot(final Set<Bullet> bullets, final int BULLET_SPEED) {
        if (this.tripleshotEffectCooldown.checkFinished())
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

    /**
     * Bullet shooting effect
     *
     * @param bulletsY
     *              The factor of the ship's shoot method.
     * @param BULLETY_SPEED
     *              the speed of a ship's bulletY
     */
    public void shootBulletY(final Set<BulletY> bulletsY, final int BULLETY_SPEED) {
        if (this.tripleshotEffectCooldown.checkFinished())
        {
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), BULLETY_SPEED));
        } else {
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2 + 10,
                    ship.getPositionY(), BULLETY_SPEED));
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), BULLETY_SPEED));
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2 - 10,
                    ship.getPositionY(), BULLETY_SPEED));
        }
    }

    
    public void attackSpeedUp() {
        if (this.attackSpeedEffectCooldown.checkFinished()) {
            if (ship.getShootingInterval().getMilliseconds() == 100)
                ship.setShootingInterval(Core.getCooldown(750));
        } else {
            if (ship.getShootingInterval().getMilliseconds() == 750)
                ship.setShootingInterval(Core.getCooldown(100));
        }
/**
     *  스턴 디버프에 걸리면 0반환
     *  평소에는 1을 반환
     *
     *  사용처 : Ship 클래스
     */
    public int shipSturnEffect() {
        if (this.debuffSturnEffect.checkFinished())
            return (1);
        else
            return (0);
    }
}
