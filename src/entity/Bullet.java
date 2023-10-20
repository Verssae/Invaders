package entity;

import java.awt.Color;
import java.util.Set;

import effect.BulletEffect;
import engine.DrawManager.SpriteType;

/**
 * Implements a bullet that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Bullet extends Entity {

	/**
	 * Speed of the bullet, positive or negative depending on direction -
	 * positive is down.
	 */
	private int speed;
	private BulletEffect bulletEffect;
	private int effectBullet;
	private int damage;

	/**
	 * Constructor, establishes the bullet's properties.
	 *
	 * @param positionX
	 *            Initial position of the bullet in the X axis.
	 * @param positionY
	 *            Initial position of the bullet in the Y axis.
	 * @param speed
	 *            Speed of the bullet, positive or negative depending on
	 *            direction - positive is down.
	 */
	public Bullet(final int positionX, final int positionY, final int speed, final int attackDamage) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);
		this.bulletEffect = new BulletEffect(this);
		this.speed = speed;
		this.effectBullet = 0;
		this.damage = attackDamage;
		setSprite();
	}
	/**
	 * Constructor, establishes the bullet's properties.
	 *
	 * @param positionX
	 *            Initial position of the bullet in the X axis.
	 * @param positionY
	 *            Initial position of the bullet in the Y axis.
	 * @param speed
	 *            Speed of the bullet, positive or negative depending on
	 *            direction - positive is down.
	 * @param bulletType
	 *            Type of bullet.
	 *            Left or Right
	 *
	 */
	public Bullet(final int positionX, final int positionY, final int speed, SpriteType bulletType, final int attackDamage) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);
		this.bulletEffect = new BulletEffect(this);
		this.speed = speed;
		this.spriteType = bulletType;
		this.effectBullet = 0;
		this.damage = attackDamage;
	}

	/**
	 * Sets correct sprite for the bullet, based on speed.
	 */
	public final void setSprite() {
		if (speed < 0)
			this.spriteType = SpriteType.Bullet;
		else
			this.spriteType = SpriteType.EnemyBullet;
	}

	/**
	 * Sets sprite for the enemy bullet, left or right.
	 */
	public final void setSprite(SpriteType bulletType) {
		this.spriteType = bulletType;
	}


	/**
	 * Updates the bullet's position.
	 */
	public final void update() {
		if(this.spriteType == SpriteType.Bullet || this.spriteType == SpriteType.EnemyBullet) {
			this.positionY += this.speed;
		}
		else if(this.spriteType == SpriteType.EnemyBulletLeft) {
			this.positionX -= (int)(this.speed*0.51449575542753);
			this.positionY += this.speed;
		}
		else {
			this.positionX += (int)(this.speed*0.51449575542753);
			this.positionY += this.speed;
		}
	}


	/**
	 * Setter of the speed of the bullet.
	 * 
	 * @param speed
	 *            New speed of the bullet.
	 */
	public final void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * Getter for the speed of the bullet.
	 * 
	 * @return Speed of the bullet.
	 */
	public final int getSpeed() {
		return this.speed;
	}

	public final void splash(Set<Bullet> bullets) {
		bulletEffect.splashEffect(bullets);
	}

	public final int getDamage() { return this.damage; }
	public final int isEffectBullet() {return this.effectBullet; }
	public void setEffectBullet(int n) {this.effectBullet = n;}
}
