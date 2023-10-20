package entity;

import java.util.HashSet;
import java.util.Set;

import engine.DrawManager.SpriteType;

/**
 * Implements a pool of recyclable bullets.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class BulletPool {

	/** Set of already created bullets. */
	private static Set<Bullet> pool = new HashSet<Bullet>();
	private static Set<BulletY> poolY = new HashSet<BulletY>();

	/**
	 * Constructor, not called.
	 */
	private BulletPool() {

	}

	/**
	 * Returns a bullet from the pool if one is available, a new one if there
	 * isn't.
	 *
	 * @param positionX
	 *            Requested position of the bullet in the X axis.
	 * @param positionY
	 *            Requested position of the bullet in the Y axis.
	 * @param speed
	 *            Requested speed of the bullet, positive or negative depending
	 *            on direction - positive is down.
	 * @param damage
	 *            Requested enhanced damage of the bullet.
	 * @return Requested bullet.
	 */
	public static Bullet getBullet(final int positionX,
								   final int positionY, final int speed,
								   final int damage) {
		Bullet bullet;
		if (!pool.isEmpty()) {
			bullet = pool.iterator().next();
			pool.remove(bullet);
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
			bullet.setPositionY(positionY);
			bullet.setSpeed(speed);
			bullet.setSprite();
			bullet.setEffectBullet(0);
			
		} else {
			bullet = new Bullet(positionX, positionY, speed, damage);
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
		}
		return bullet;
	}

	/**
	 * Returns a bullet from the pool if one is available, a new one if there
	 * isn't.
	 *
	 * @param positionX
	 *            Requested position of the bullet in the X axis.
	 * @param positionY
	 *            Requested position of the bullet in the Y axis.
	 * @param speed
	 *            Requested speed of the bullet, positive or negative depending
	 *            on direction - positive is down.
	 * @param damage
	 *            Requested enhanced damage of the bullet.
	 * @return Requested bullet.
	 */
	public static Bullet getBullet(final int positionX,
								   final int positionY, final int speed, SpriteType bulletType,
								   final int damage) {
		Bullet bullet;
		if (!pool.isEmpty()) {
			bullet = pool.iterator().next();
			pool.remove(bullet);
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
			bullet.setPositionY(positionY);
			bullet.setSpeed(speed);
			bullet.setSprite(bulletType);
		} else {
			bullet = new Bullet(positionX, positionY, speed, bulletType, damage);
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
		}
		return bullet;
	}

	/**
	 * Returns a bullet from the pool if one is available, a new one if there
	 * isn't.
	 *
	 * @param positionX
	 *            Requested position of the bullet in the X axis.
	 * @param positionY
	 *            Requested position of the bullet in the Y axis.
	 * @param speed
	 *            Requested speed of the bullet, positive or negative depending
	 *            on direction - positive is down.
	 * @param damage
	 *            Requested enhanced damage of the bullet.
	 * @return Requested bulletY.
	 */
	public static BulletY getBulletY(final int positionX,
									 final int positionY, int speed,
									 final int damage) {
		BulletY bulletY;
		if (!poolY.isEmpty()) {
			bulletY = poolY.iterator().next();
			poolY.remove(bulletY);
			bulletY.setPositionX(positionX - bulletY.getWidth() / 2);
			bulletY.setPositionY(positionY);
			bulletY.setSpeed(speed);
			bulletY.setSprite();
		} else {
			bulletY = new BulletY(positionX, positionY, speed, damage);
			bulletY.setPositionX(positionX - bulletY.getWidth() / 2);
		}
		return bulletY;
	}

	/**
	 * Adds one or more bullets to the list of available ones.
	 * 
	 * @param bullet
	 *            Bullets to recycle.
	 */
	public static void recycle(final Set<Bullet> bullet) {
		pool.addAll(bullet);
	}
	public static void recycleBulletY(final Set<BulletY> bulletY) {
		poolY.addAll(bulletY);
	}
}
