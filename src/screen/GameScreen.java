package screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import engine.*;
import entity.*;



import javax.swing.*;

/**
 * Implements the game screen, where the action happens.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class GameScreen extends Screen {

	/** Milliseconds until the screen accepts user input. */
	private static final int INPUT_DELAY = 6000;
	/** Bonus score for each life remaining at the end of the level. */
	private static final int LIFE_SCORE = 100;
	/** Minimum time between bonus ship's appearances. */
	private static final int BONUS_SHIP_INTERVAL = 20000;
	/** Maximum variance in the time between bonus ship's appearances. */
	private static final int BONUS_SHIP_VARIANCE = 10000;
	/** Time until bonus ship explosion disappears. */
	private static final int BONUS_SHIP_EXPLOSION = 500;
	/** Time from finishing the level to screen change. */
	private static final int SCREEN_CHANGE_INTERVAL = 1500;
	/** Height of the interface separation line. */
	private static final int SEPARATION_LINE_HEIGHT = 40;
	/** Current game difficulty settings. */
	private GameSettings gameSettings;
	/** Current difficulty level number. */
	private int level;
	/** Formation of enemy ships. */
	private EnemyShipFormation enemyShipFormation;
	/** Player's ship. */
	private Ship ship;
	/** Bonus enemy ship that appears sometimes. */
	private EnemyShip enemyShipSpecial;
	/** Minimum time between bonus ship appearances. */
	private Cooldown enemyShipSpecialCooldown;
	/** Time until bonus ship explosion disappears. */
	private Cooldown enemyShipSpecialExplosionCooldown;
	/** Time from finishing the level to screen change. */
	private Cooldown screenFinishedCooldown;
	/** Set of bullets fired by on screen ships. */
	private Set<Bullet> bullets;
	/** Set of "BulletY" fired by player ships. */
	private Set<BulletY> bulletsY;
	/** Sound Effects for player's ship and enemy. */
	private SoundEffect soundEffect;
	/** Add and Modify BGM */
	private BGM bgm;
	/** Aiming line. */
	private BulletLine bulletLine;
	/** Current score. */
	private int score;
	/** Player lives left. */
	private double lives;
	/** Total bullets shot by the player. */
	private int bulletsShot;
	/** Total ships destroyed by the player. */
	private int shipsDestroyed;
	/** Moment the game starts. */
	private long gameStartTime;
	/** Checks if the level is finished. */
	private boolean levelFinished;
	/** Checks if a bonus life is received. */
	private boolean bonusLife;
	/** Checks if the game is hardcore. */
	private boolean hardcore;
	/** Checks if the game is paused. */
	private boolean pause;
	/** Set of all items.*/
	private Set<Item> items;
	/** is none exist dropped item?*/
	private boolean isItemAllEat;
	/** Check what color will be displayed*/
	private int colorVariable;

	private int BulletsCount = 99;

	private int attackDamage;
	private int areaDamage;

	/**
	 * Constructor, establishes the properties of the screen.
	 *
	 * @param gameState
	 *            Current game state.
	 * @param gameSettings
	 *            Current game settings.
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */
	public GameScreen(final GameState gameState,
					  final GameSettings gameSettings,
					  final int width, final int height, final int fps) {
		super(width, height, fps);

		this.gameSettings = gameSettings;
		//this.bonusLife = bonusLife;
		this.level = gameState.getLevel();
		this.score = gameState.getScore();
		this.lives = gameState.getLivesRemaining();
		//if (this.bonusLife)
			//this.lives++;
		this.bulletsShot = gameState.getBulletsShot();
		this.shipsDestroyed = gameState.getShipsDestroyed();
		this.hardcore = gameState.getHardCore();
		this.pause = false;
		this.attackDamage = gameSettings.getBaseAttackDamage();
		this.areaDamage = gameSettings.getBaseAreaDamage();
	}

	/**
	 * Initializes basic screen properties, and adds necessary elements.
	 */
	public final void initialize() {
		super.initialize();

		enemyShipFormation = new EnemyShipFormation(this.gameSettings, this.level);
		enemyShipFormation.attach(this);
		this.ship = new Ship(this.width / 2, this.height - 30, "a", Color.WHITE);
		this.bulletLine = new BulletLine(this.width / 2 , this.height + 120);
		// Appears each 10-30 seconds.
		this.enemyShipSpecialCooldown = Core.getVariableCooldown(
				BONUS_SHIP_INTERVAL, BONUS_SHIP_VARIANCE);
		this.enemyShipSpecialCooldown.reset();
		this.enemyShipSpecialExplosionCooldown = Core
				.getCooldown(BONUS_SHIP_EXPLOSION);
		this.screenFinishedCooldown = Core.getCooldown(SCREEN_CHANGE_INTERVAL);
		this.bullets = new HashSet<Bullet>();
		this.bulletsY = new HashSet<BulletY>();

		this.items = new HashSet<Item>();
		this.isItemAllEat = false;
		// Special input delay / countdown.
		this.gameStartTime = System.currentTimeMillis();
		this.inputDelay = Core.getCooldown(INPUT_DELAY);
		this.inputDelay.reset();

		soundEffect = new SoundEffect();
		bgm = new BGM();
		drawManager.initBackgroundTimer();

		drawManager.initBackgroundTimer(this, SEPARATION_LINE_HEIGHT); // Initializes timer for background animation.
	}

	/**
	 * Starts the action.
	 *
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		this.score += LIFE_SCORE * (this.lives - 1);
		this.logger.info("Screen cleared with a score of " + this.score);

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		if (pause) { // Game Pause, press ENTER to continue or BackSpace to quit
			pause = !inputManager.isKeyDown(KeyEvent.VK_ENTER);
			boolean exit = inputManager.isKeyDown(KeyEvent.VK_BACK_SPACE);
			if (exit) {
				this.returnCode = 1;
				this.lives = 0;
				this.isRunning = false;
			}
		}
		else {
			super.update();
			if (this.inputDelay.checkFinished() && !this.levelFinished) {
				pause = inputManager.isKeyDown(KeyEvent.VK_ESCAPE);
				if (!this.ship.isDestroyed()) {
					boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT)
							|| inputManager.isKeyDown(KeyEvent.VK_D);
					boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT)
							|| inputManager.isKeyDown(KeyEvent.VK_A);
					boolean isRightBorder = this.ship.getPositionX()
							+ this.ship.getWidth() + this.ship.getSpeed() > this.width - 1;
					boolean isLeftBorder = this.ship.getPositionX()
							- this.ship.getSpeed() < 1;
					if (moveRight && !isRightBorder) {
						this.ship.moveRight();
					}
					if (moveLeft && !isLeftBorder) {
						this.ship.moveLeft();
					}
					if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
						if(bulletsShot % 3 == 0 && !(bulletsShot == 0)) {
							if (this.ship.shootBulletY(this.bulletsY)) {
								soundEffect.playShipShootingSound();
								this.bulletsShot++;
								this.BulletsCount--;
							}
						}
						else {
							if (this.ship.shoot(this.bullets)) {
								soundEffect.playShipShootingSound();
								this.bulletsShot++;
								this.BulletsCount--;
							}
						}
					}
				}

				if (this.enemyShipSpecial != null) {
					if (!this.enemyShipSpecial.isDestroyed())
						this.enemyShipSpecial.move(2, 0);
					else if (this.enemyShipSpecialExplosionCooldown.checkFinished())
						this.enemyShipSpecial = null;
				}
				if (this.enemyShipSpecial == null
						&& this.enemyShipSpecialCooldown.checkFinished()) {
					colorVariable = (int)(Math.random()*4);
					switch (colorVariable) {
						case 0:
							this.enemyShipSpecial = new EnemyShip(Color.RED);
							break;
						case 1:
							this.enemyShipSpecial = new EnemyShip(Color.YELLOW);
							break;
						case 2:
							this.enemyShipSpecial = new EnemyShip(Color.BLUE);
							break;
						case 3:
							this.enemyShipSpecial = new EnemyShip(Color.WHITE);
							break;
						default:
							break;
					}
					this.enemyShipSpecialCooldown.reset();
					this.logger.info("A special ship appears");
				}
				if (this.enemyShipSpecial != null
						&& this.enemyShipSpecial.getPositionX() > this.width) {
					bgm.enemyShipSpecialbgm_stop();
					this.enemyShipSpecial = null;
					this.logger.info("The special ship has escaped");
				}

				this.ship.update();
				this.enemyShipFormation.update();
				this.enemyShipFormation.shoot(this.bullets);
			}

			manageCollisions();
			manageCollisionsY();
			cleanBullets();
			cleanBulletsY();
			cleanItems();
			draw();
		}
		if (this.enemyShipFormation.isEmpty() && !this.levelFinished) {
			endStageAllEat();
			this.levelFinished = true;
			this.screenFinishedCooldown.reset();
		}
		if (this.lives == 0 && !this.levelFinished) {
			this.ship.update();
			this.levelFinished = true;
			soundEffect.playShipDestructionSound();
			this.screenFinishedCooldown.reset();
		}

		if ((isItemAllEat || this.levelFinished) && this.screenFinishedCooldown.checkFinished()){
			this.isRunning = false;
		}
	}

	/**
	 * when the stage end, eat all dropped item.
	 */
	private void endStageAllEat(){
		Cooldown a = Core.getCooldown(25);
		a.reset();
		while(!this.items.isEmpty()){
			if(a.checkFinished()) {
				manageCollisions();
				for (Item item : this.items) {
					item.resetItem(this.ship);
				}
				a.reset();
			}
			draw();
		}
		isItemAllEat = true;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);
		drawManager.drawBackground(this, SEPARATION_LINE_HEIGHT, (int)this.lives);
		if (this.enemyShipSpecial != null) drawManager.drawBackgroundSpecialEnemy(this, SEPARATION_LINE_HEIGHT);
		drawManager.drawBackgroundLines(this, SEPARATION_LINE_HEIGHT);
		drawManager.drawBackgroundPlayer(this, SEPARATION_LINE_HEIGHT, this.ship.getPositionX(), this.ship.getPositionY(), this.ship.getWidth(), this.ship.getHeight());

		drawManager.drawEntity(this.ship, this.ship.getPositionX(),
				this.ship.getPositionY());
		drawManager.drawEntity(this.bulletLine, this.ship.getPositionX() + 12,
				this.ship.getPositionY() - 320);
		if (this.enemyShipSpecial != null)
			drawManager.drawEntity(this.enemyShipSpecial,
					this.enemyShipSpecial.getPositionX(),
					this.enemyShipSpecial.getPositionY());
		for (Item item : this.items)
			drawManager.drawEntity(item, item.getPositionX(),
					item.getPositionY());
		enemyShipFormation.draw();

		for (Bullet bullet : this.bullets)
			drawManager.drawEntity(bullet, bullet.getPositionX(),
					bullet.getPositionY());

		for (BulletY bulletY : this.bulletsY)
			drawManager.drawEntity(bulletY, bulletY.getPositionX(),
					bulletY.getPositionY());

		// Interface.
		drawManager.drawScore(this, this.score);
		//drawManager.drawLives(this, this.lives);
		drawManager.drawLivesbar(this, this.lives);
		drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);
		drawManager.scoreEmoji(this, this.score);
		drawManager.BulletsCount(this, this.BulletsCount);

		// Countdown to game start.
		if (!this.inputDelay.checkFinished()) {
			int countdown = (int) ((INPUT_DELAY
					- (System.currentTimeMillis()
					- this.gameStartTime)) / 1000);

			drawManager.drawCountDown(this, this.level, countdown,
					this.bonusLife);

			// Fade from white at game start.
			drawManager.drawBackgroundStart(this, SEPARATION_LINE_HEIGHT);

			/* this code is modified with Clean Code (dodo_kdy)  */
			//drawManager.drawHorizontalLine(this, this.height / 2 - this.height / 12);
			//drawManager.drawHorizontalLine(this, this.height / 2 + this.height / 12);

		}

		// If Game has been paused
		if (this.pause) {
			drawManager.drawPaused(this);
		}

		drawManager.completeDrawing(this);
	}

	/**
	 * Cleans bullets that go off screen.
	 */
	private void cleanBullets() {
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (Bullet bullet : this.bullets) {
			bullet.update();
			if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bullet.getPositionY() > this.height)
				recyclable.add(bullet);
		}
		this.bullets.removeAll(recyclable);
		BulletPool.recycle(recyclable);
	}
	private void cleanBulletsY() {
		Set<BulletY> recyclable = new HashSet<BulletY>();
		for (BulletY bulletY : this.bulletsY) {
			bulletY.update();
			if (bulletY.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bulletY.getPositionY() > this.height)
				recyclable.add(bulletY);
		}
		this.bulletsY.removeAll(recyclable);
		BulletPool.recycleBulletY(recyclable);
	}
	/**
	 * update and Cleans items that end the Living-Time
	 */
	private void cleanItems() {
		Set<Item> recyclable = new HashSet<Item>();
		for (Item item : this.items) {
			item.update(this.getWidth(), this.getHeight(), SEPARATION_LINE_HEIGHT);
			if (item.islivingTimeEnd()){
				recyclable.add(item);
			}
		}
		this.items.removeAll(recyclable);
		ItemPool.recycle(recyclable);
	}

	/**
	 * Manages collisions between bullets and ships.
	 */
	private void manageCollisions() {
		Set<Bullet> recyclableBullet = new HashSet<Bullet>();
		Set<Item> recyclableItem = new HashSet<Item>();
		for (Bullet bullet : this.bullets)
			if (bullet.getSpeed() > 0) {
				if (checkCollision(bullet, this.ship) && !this.levelFinished) {
					recyclableBullet.add(bullet);
					if (!this.ship.isDestroyed()) {
						this.ship.destroy();
						if (this.lives != 1) soundEffect.playShipCollisionSound();
						this.lives--;
						this.logger.info("Hit on player ship, " + this.lives
								+ " lives remaining.");
					}
				}
			} else {
				for (EnemyShip enemyShip : this.enemyShipFormation)
					if (!enemyShip.isDestroyed()
							&& checkCollision(bullet, enemyShip)) {
						enemyShip.reduceEnemyLife(this.attackDamage);
						soundEffect.playEnemyDestructionSound();
						if(enemyShip.getEnemyLife() < 1) {
							this.score += enemyShip.getPointValue();
							this.shipsDestroyed++;
							this.enemyShipFormation.destroy(enemyShip, this.items);

						}
						recyclableBullet.add(bullet);
					}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bullet, this.enemyShipSpecial)) {
					enemyShipSpecial.reduceEnemyLife(this.attackDamage);
					if(enemyShipSpecial.getEnemyLife() < 1) {
						this.score += this.enemyShipSpecial.getPointValue();
						this.shipsDestroyed++;
						this.enemyShipSpecial.destroy(this.items);
						bgm.enemyShipSpecialbgm_stop();
						if (this.lives < 2.9) this.lives = this.lives + 0.1;
						this.enemyShipSpecialExplosionCooldown.reset();
					}
					recyclableBullet.add(bullet);
				}
			}
		for (Item item : this.items){
			if(checkCollision(item, this.ship) && !this.levelFinished){
				recyclableItem.add(item);
				this.logger.info("Get Item ");
				this.ship.checkGetItem(item);
			}
		}
		this.items.removeAll(recyclableItem);
		this.bullets.removeAll(recyclableBullet);
		ItemPool.recycle(recyclableItem);
		BulletPool.recycle(recyclableBullet);
	}


	/**
	 * Manages collisions between bulletsY and ships.
	 */
	private void manageCollisionsY() {
		Set<BulletY> recyclableBulletY = new HashSet<BulletY>();
		Set<Item> recyclableItem = new HashSet<Item>();
		for (BulletY bulletY : this.bulletsY)
			if (bulletY.getSpeed() > 0) {
				if (checkCollision(bulletY, this.ship) && !this.levelFinished) {
					recyclableBulletY.add(bulletY);
					if (!this.ship.isDestroyed()) {
						this.ship.destroy();
						this.lives--;
						this.logger.info("Hit on player ship, " + this.lives
								+ " lives remaining.");
					}
				}
			} else {
				for (EnemyShip enemyShip : this.enemyShipFormation)
					if (!enemyShip.isDestroyed()
							&& checkCollision(bulletY, enemyShip)) {
						this.score += enemyShip.getPointValue();
						this.shipsDestroyed++;
						this.enemyShipFormation.destroy(enemyShip, this.items);
						recyclableBulletY.add(bulletY);
					}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bulletY, this.enemyShipSpecial)) {
					this.score += this.enemyShipSpecial.getPointValue();
					this.shipsDestroyed++;
					this.enemyShipSpecial.destroy(this.items);
					bgm.enemyShipSpecialbgm_stop();
					this.enemyShipSpecialExplosionCooldown.reset();
					recyclableBulletY.add(bulletY);
				}
			}
		this.items.removeAll(recyclableItem);
		this.bulletsY.removeAll(recyclableBulletY);
		ItemPool.recycle(recyclableItem);
		BulletPool.recycleBulletY(recyclableBulletY);
	}

	/**
	 * Checks if two entities are colliding.
	 *
	 * @param a
	 *            First entity, the bullet.
	 * @param b
	 *            Second entity, the ship.
	 * @return Result of the collision test.
	 */
	private boolean checkCollision(final Entity a, final Entity b) {
		// Calculate center point of the entities in both axis.
		int centerAX = a.getPositionX() + a.getWidth() / 2;
		int centerAY = a.getPositionY() + a.getHeight() / 2;
		int centerBX = b.getPositionX() + b.getWidth() / 2;
		int centerBY = b.getPositionY() + b.getHeight() / 2;
		// Calculate maximum distance without collision.
		int maxDistanceX = a.getWidth() / 2 + b.getWidth() / 2;
		int maxDistanceY = a.getHeight() / 2 + b.getHeight() / 2;
		// Calculates distance.
		int distanceX = Math.abs(centerAX - centerBX);
		int distanceY = Math.abs(centerAY - centerBY);

		return distanceX < maxDistanceX && distanceY < maxDistanceY;
	}

	/**
	 * Returns a GameState object representing the status of the game.
	 *
	 * @return Current game state.
	 */
	public final GameState getGameState() {
		return new GameState(this.level, this.score, this.lives,
				this.bulletsShot, this.shipsDestroyed, this.hardcore);
	}
}