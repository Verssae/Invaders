package entity;

import java.awt.Color;
import java.util.Set;

import effect.Effect;
import effect.ShipEffect;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

/**
 * Implements a ship, to be controlled by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Ship extends Entity {

	/** Time between shots. */
	private static int SHOOTING_INTERVAL = 750;
	/** Time between shooting bomb */
	private static int SHOOTING_BOMB_INTERVAL = 1000;
	/** Speed of the bullets shot by the ship. */
	public static final int BULLET_SPEED = -6;
	/** Speed of the bullets shot by the ship. */
	public static final int BULLETY_SPEED = -9;
	/** Speed of the bombs shot by the ship. */
	public static final int BOMB_SPEED = -5;
	/** Movement of the ship for each unit of time. */
	private static int SPEED = 2;

	/** Minimum time between shots. */
	public Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	public Cooldown destructionCooldown;
	/** EveryThing of item effect. */

	/** Minimum time between shooting bomb */
	private Cooldown bombCooldown;
	private ShipEffect shipEffect;

	/**
	 * Constructor, establishes the ship's properties.
	 *
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 */
	public Ship(final int positionX, final int positionY, String type, Color color) {

		super(positionX, positionY, 13 * 2, 8 * 2, color);
		this.shipEffect = new ShipEffect(this);
		if(type == "a") {
			this.spriteType = SpriteType.ShipA;
		}
		else if(type == "b"){
			this.spriteType = SpriteType.ShipB;
		}
		else if(type == "c"){
			this.spriteType = SpriteType.ShipC;
		}
		else if(type == "d"){
			this.spriteType = SpriteType.ShipD;
		}
		else if(type == "e"){
			this.spriteType = SpriteType.ShipE;
		}
		else if(type == "f"){
			this.spriteType = SpriteType.ShipF;
		}
		else if(type == "g"){
			this.spriteType = SpriteType.ShipG;
		}

		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
		this.destructionCooldown = Core.getCooldown(1000);
		this.bombCooldown = Core.getCooldown(SHOOTING_BOMB_INTERVAL);
	}

	/**
	 * Moves the ship speed uni ts right, or until the right screen border is
	 * reached.
	 *
	 * jtaejune : 스턴 아이템을 먹으면 속도가 0이 됨.
	 */
	public final void moveRight() {

		this.positionX += SPEED * this.shipEffect.moveEffect();
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 *
	 * jtaejune : 스턴 아이템을 먹으면 속도가 0이 됨.
	 */
	public final void moveLeft() {
		this.positionX -= SPEED * this.shipEffect.moveEffect();
	}

	/**
	 * Shoots a bullet upwards.
	 * 
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @param ENHANCED_DAMAGE
	 *            Enhanced Attack Damage. (on EnhanceScreen)
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets, final int ENHANCED_DAMAGE) {
		this.shipEffect.attackSpeedUp();
		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();
			this.shipEffect.shoot(bullets, BULLET_SPEED, ENHANCED_DAMAGE);
			return true;
		}
		return false;
	}

	public final boolean shootBulletY(final Set<BulletY> bulletsY, final int ENHANCED_DAMAGE) {
		this.shipEffect.attackSpeedUp();
		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();
			this.shipEffect.shootBulletY(bulletsY, BULLETY_SPEED, ENHANCED_DAMAGE);
			return true;
		}
		return false;
	}

	public final boolean shootBomb(final Set<Bomb> bombs) {
		if(this.bombCooldown.checkFinished()) {
			this.bombCooldown.reset();
			bombs.add(BombPool.getBomb(this.getPositionX() + this.getWidth() / 2,
					this.getPositionY(), BOMB_SPEED));
			return true;
		}
		return false;
	}

	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if(this.spriteType == spriteType.ShipA || this.spriteType == spriteType.ShipADestroyed || this.spriteType == spriteType.ShipAShileded) {
			if(this.shipEffect.getShieldState() == true){
				this.spriteType = spriteType.ShipAShileded;
			}else{
				if (!this.destructionCooldown.checkFinished()) {
					this.spriteType = SpriteType.ShipADestroyed;
				} else {
					this.spriteType = SpriteType.ShipA;
				}
			}
		}else if(this.spriteType == spriteType.ShipB || this.spriteType == spriteType.ShipBDestroyed || this.spriteType == spriteType.ShipBShileded) {
			if(this.shipEffect.getShieldState()){
				this.spriteType = spriteType.ShipBShileded;
			}else{
				if (!this.destructionCooldown.checkFinished()) {
					this.spriteType = SpriteType.ShipBDestroyed;
				} else {
					this.spriteType = SpriteType.ShipB;
				}
			}
		}else if(this.spriteType == spriteType.ShipC || this.spriteType == spriteType.ShipCDestroyed || this.spriteType == spriteType.ShipCShileded) {
			if(this.shipEffect.getShieldState()){
				this.spriteType = spriteType.ShipCShileded;
			}else{
				if (!this.destructionCooldown.checkFinished()) {
					this.spriteType = SpriteType.ShipCDestroyed;
				} else {
					this.spriteType = SpriteType.ShipC;
				}
			}
		}else if(this.spriteType == spriteType.ShipD || this.spriteType == spriteType.ShipDDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
					this.spriteType = SpriteType.ShipDDestroyed;
				} else {
					this.spriteType = SpriteType.ShipD;
				}
			}
		else if(this.spriteType == spriteType.ShipE || this.spriteType == spriteType.ShipEDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.ShipEDestroyed;
			} else {
				this.spriteType = SpriteType.ShipE;
			}

		}
		else if(this.spriteType == spriteType.ShipF || this.spriteType == spriteType.ShipFDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.ShipFDestroyed;
			} else {
				this.spriteType = SpriteType.ShipF;
			}

		}
		else if(this.spriteType == spriteType.ShipG || this.spriteType == spriteType.ShipGDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.ShipGDestroyed;
			} else {
				this.spriteType = SpriteType.ShipG;
			}

		}
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		this.destructionCooldown.reset();
	}

	/**
	 * Checks if the ship is destroyed.
	 * 
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 * Getter for the ship's speed.
	 * 
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return SPEED * this.shipEffect.moveEffect();
	}

	/**
	 * Reset cooldown when ship get an item
	 *
	 * @param item Items acquired by ship
	 */
	public final void checkGetItem(final Item item) {
		item.setDestroy(true);
		this.shipEffect.CooldownReset(item.getSpriteType());
	}



/* -- Item 6. some helpful code
	public final void catchItem(Item item) {
		if (item.spriteType == SpriteType.Item1) {
			this.bulletEffectCooldown.reset();
		} else if (item.spriteType == SpriteType.Item2) {
			this.shipEffectCooldown.reset();
=======
>>>>>>> develop
		if (item.spriteType == SpriteType.Buff_Item || item.spriteType == SpriteType.Debuff_Item) {
			this.shipEffect.CooldownReset(item.getSpriteType());
		}
	}

<<<<<<< HEAD
 */
public final void gameEndShipMotion(boolean levelFinished, double lives){
	if(levelFinished){
		if(lives == 0 ) {
			this.setColor(Color.gray);
			this.spriteType = SpriteType.gravestone;
		}
	}
}

	public void setSPEED(int s) {SPEED=s;}
	/** Return the ship's attack speed
	 * @return shootingCooldown
	 */
	public Cooldown getShootingInterval(){return this.shootingCooldown;}
	/** Set the ship's attack speed
	 */
	public void setShootingInterval(Cooldown cool){this.shootingCooldown = cool;}
	/** Return the ship's shiled state.
	 * @return ShiledState
	 */
	public boolean getShieldState() { return this.shipEffect.getShieldState(); }
	/** set the ship's shiled state.
	 */
	public void setShieldState(boolean state) { this.shipEffect.setShieldState(state); }
	/** Return the ship has bomb?.
	 * @return bomb
	 */
	public boolean getBomb(){
		return shipEffect.bomb;
	}
	/** set the ship has bomb?.
	 */
	public void setBomb(boolean t){
		this.shipEffect.bomb = t;
	}
}
