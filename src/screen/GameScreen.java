package screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;

import engine.BGM;
import engine.Cooldown;
import engine.Core;
import engine.CountUpTimer;
import engine.DrawManager.SpriteType;
import engine.EnhanceManager;
import engine.GameSettings;
import engine.GameState;
import engine.ItemManager;
import engine.SoundEffect;
import entity.*;

import javax.script.ScriptEngine;


/**
 * Implements the game screen, where the action happens.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class GameScreen extends Screen {
	/** Sound status on/off. */
	private boolean isSoundOn = true;

	/** Milliseconds until the screen accepts user input. */
	private static final int INPUT_DELAY = 6000;
	/** Bonus score for each life remaining at the end of the level. */
	private static final int LIFE_SCORE = 100;
	/** Minimum time between bonus ship's appearances. */
	private static final int BONUS_SHIP_INTERVAL = 20000;
	/** Maximum variance in the time between bonus ship's appearances. */
	private static final int BONUS_SHIP_VARIANCE = 10000;
	/** Minimum time between bonus ship's appearances. */
	private static final int BONUS_SHIP_EXPLOSION = 1500;
	/** Maximum variance in the time between laser's appearances. */
	private static int LASER_INTERVAL = 5000;
	/** Maximum variance in the time between Laser's appearances. */
	private static int LASER_VARIANCE = 1000;
	/** Maximum variance in the time between Laser's appearances. */
	private static int LASER_LOAD = 2000;
	/** Time until laser disappears. */
	private static final int LASER_ACTIVATE = 1000;
	/** Minimum time between Beam's appearances. */
	private static final int BEAM_INTERVAL = 10000;
	/** Maximum variance in the time between Beam's appearances. */
	private static final int BEAM_VARIANCE = 1000;
	/** Maintaining time of beam. */
	private static final int BEAM_ACTIVATE = 2000;
	/** Load time of Beam. */
	private static final int BEAM_LOAD = 1000;
	/** Time until Blaze disappears. */
	private static final int[] Blaze_ACTIVATE = {2000, 3000, 4000, 4000};
	/** Time until Poison disappears. */
	private static final int[] Poison_ACTIVATE = {2000, 2500, 3000, 3000};
	/** Time until Smog disappears. */
	private static final int[] Smog_ACTIVATE = {3000, 4000, 5000, 5000};
	/** Time until EMP disappears. */
	private static final int[] EMP_ACTIVATE = {10000, 10000, 10000, 10000};
	private static final int[][] SPAttack_ACT = {Blaze_ACTIVATE, Poison_ACTIVATE, Smog_ACTIVATE, EMP_ACTIVATE};
	private static final int SpAtSpriteCooldown = 250;
	/** Time from finishing the level to screen change. */
	private static final int SCREEN_CHANGE_INTERVAL = 3000;
	/** Height of the interface separation line. */
	private static final int SEPARATION_LINE_HEIGHT = 40;
	private static final int INIT_BOMB_COUNT = 5;
	private static final int BOMB_INTERVAL = 1000;
	private static final int[] DX = new int[] {1, 0, -1, 0, 1, 1, -1, -1};
	private static final int[] DY = new int[] {0, 1, 0, -1, 1, -1, 1, -1};

	private static final int DRILL_SPEED = -5;

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
	private Cooldown SpecialAttackSpriteCooldown;
	/** Time from finishing the level to screen change. */
	private Cooldown screenFinishedCooldown;
	/** Set of bullets fired by on screen ships. */
	private Set<Bullet> bullets;

	/** Check boss. */
	private int bossCode;
	/** Beam */
	private Beam beam;
	/** Time between beam launch */
	private Cooldown beamCooldown;
	/** Load time of beam */
	private Cooldown beamLoadCooldown;
	/** Maintaining time of beam */
	private Cooldown beamLaunchCooldown;
	/** Beamline */
	private LaserLine beamLine;
	/** Laser */
	private Laser laser;
	/** Laserline */
	private LaserLine laserline;
	/** Location of next Laser */
	private int nextLaserX;
	/** Minimum time between laser launch */
	private Cooldown laserCooldown;
	/** Load time of laser */
	private Cooldown laserLoadCooldown;
	/** Maintaining time of laser*/
	private Cooldown laserLaunchCooldown;
	/** Laser on/off (difficulty normal, upper than 4level or difficulty hard, hardcore */
	private boolean laserActivate;
	/** Set of "BulletY" fired by player ships. */
	private Cooldown bosslaserLaunchCooldown;
	private Set<BulletY> bulletsY;

	/** Sound Effects for player's ship and enemy. */
	private SoundEffect soundEffect;
	/** Add and Modify BGM */
	private BGM bgm;
	/** Aiming line. */
	private BulletLine bulletLine;
	/** Current score. */
	private int score;
	/** Current coin. */
	private Coin coin;
	/** Player lives left. */
	private double lives;
	/** Total bullets shot by the player. */
	private int bulletsShot;
	/** Total ships destroyed by the player. */
	private int shipsDestroyed;
	/** Moment the game starts. */
	private long gameStartTime;
	/** Checks if the level is finished. */
	public boolean levelFinished;
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
	/** Set of Bombs fired by ships on screen */
	private Set<Bomb> bombs;
	/** the number of bomb*/
	private int bombCount;
	/** minimum time between bomb launch */
	/** Drill */
	private Drill drill = null;
	private Cooldown bombCooldown;
	/** Current Value of Enhancement Attack. */
	private int attackDamage;
	/** Current Value of Enhancement Area. */
	private int areaDamage;
	/** EnhanceManager to manage enhancement (received from Core.java) */
	private EnhanceManager enhanceManager;
	/** X postion where the special bullet is launched */
	private int launchPos;
	/** Combo counting*/
	private int combo=0;
	/**  */
	private boolean isboss;
	/**  */

	/**  */
	private CountUpTimer timer;
	private int Miss = 0;
	private ItemManager itemManager;
	private String clearCoin;
	private GameScreen gamescreen;
	private Color shipColor;
	private String nowSkinString;
	private Map<Color, Boolean> ownedSkins;
	private Map<Color, Boolean> equippedSkins;

	/** Special Bullet **/
	private SpecialBullet SpBullet;
	private Cooldown SpecialAttackCooldown;
	private Cooldown SpecialAtDamageCooldown;
	private Cooldown SpecialAtMaintainCooldown;
	private final int[] BlazeDamageCooldown = {750, 600, 500, 500};
	private final int[] BlazeMaintainCooldown = {2000, 2500, 3000, 3000};
	private final int[] PoisonDamageCooldown = {800, 750, 600, 600};
	private final int[] PoisonMaintainCooldown = {2000, 2500, 3000, 3000};
	private final double[] PoisonDamage = {0.1, 0.1, 0.2, 0.2};
	private final double[] BlazeDamage = {0.1, 0.1, 0.2, 0.2};
	private int BulletsRemaining=99;
	/** EMP Emergency Escape Code **/
	private int[] EmerCode = {KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3
			, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9};

	private int lastSpAt;


	/**
	 * Constructor, establishes the properties of the screen.
	 *
	 * @param gameState
	 *            Current game state.
	 * @param gameSettings
	 *            Current game settings.
	 * @param enhanceManager
	 *            Current EnhanceManager.
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */
	public GameScreen(final GameState gameState,
					  final GameSettings gameSettings,
					  final EnhanceManager enhanceManager, final ItemManager itemManager,
					  final int width, final int height, final int fps) {
		super(width, height, fps);


		this.gameSettings = gameSettings;
		this.enhanceManager = enhanceManager;
		this.itemManager = itemManager;
		this.level = gameState.getLevel();
		this.score = gameState.getScore();
		timer = new CountUpTimer();
		this.coin = gameState.getCoin();
		this.lives = gameState.getLivesRemaining();
		this.bulletsShot = gameState.getBulletsShot();
		this.shipsDestroyed = gameState.getShipsDestroyed();
		this.hardcore = gameState.getHardCore();
		this.pause = false;
		this.attackDamage = gameSettings.getBaseAttackDamage();
		this.areaDamage = gameSettings.getBaseAreaDamage();
		this.BulletsRemaining = getGameState().getBulletsRemaining();
		//this.BulletsCount = getBulletsCount();
		this.clearCoin = getClearCoin();
		this.shipColor = gameState.getShipColor();
		this.nowSkinString = gameState.getNowSkinString();
		this.bombCount = INIT_BOMB_COUNT;
		this.laserActivate = (gameSettings.getDifficulty() == 1 && getGameState().getLevel() >= 4) || (gameSettings.getDifficulty() > 1);
		if (gameSettings.getDifficulty() > 1) {
			LASER_INTERVAL = 3000;
			LASER_VARIANCE = 500;
			LASER_LOAD = 1500;
		}

		this.bossCode = gameSettings.getBossCode();

	}


	/**
	 * Initializes basic screen properties, and adds necessary elements.
	 */
	public final void initialize() {
		super.initialize();

		enemyShipFormation = new EnemyShipFormation(this.gameSettings, this.level);
		enemyShipFormation.attach(this);
		this.ship = new Ship(this.width / 2, this.height - 30, "d", this.shipColor);
		this.ship.setSPEED(2);
		this.bulletLine = new BulletLine(this.width / 2 , this.height + 120);
		// Appears each 10-30 seconds.
		this.enemyShipSpecialCooldown = Core.getVariableCooldown(
				BONUS_SHIP_INTERVAL, BONUS_SHIP_VARIANCE);
		this.enemyShipSpecialCooldown.reset();
		this.enemyShipSpecialExplosionCooldown = Core
				.getCooldown(BONUS_SHIP_EXPLOSION);
		// Laser appears each (4~6 or 2.5~3.5) seconds, be loaded for 2 or 1.5 seconds and takes a second for launch)
		this.nextLaserX = -1;
		this.laser = null;
		this.laserCooldown = Core.getVariableCooldown(
				LASER_INTERVAL, LASER_VARIANCE);
		this.laserCooldown.reset();
		this.laserLoadCooldown = Core
				.getCooldown(LASER_LOAD);
		this.laserLoadCooldown.reset();
		this.laserLaunchCooldown = Core
				.getCooldown(LASER_ACTIVATE);
		this.laserLaunchCooldown.reset();
		this.beamCooldown = Core.getVariableCooldown(
				BEAM_INTERVAL, BEAM_VARIANCE);
		beamCooldown.reset();
		this.beamLoadCooldown = Core
				.getCooldown(BEAM_LOAD);
		beamLoadCooldown.reset();
		this.beamLaunchCooldown = Core
				.getCooldown(BEAM_ACTIVATE);
		beamLaunchCooldown.reset();
		this.screenFinishedCooldown = Core.getCooldown(SCREEN_CHANGE_INTERVAL);
		this.bombCooldown = Core.getCooldown(BOMB_INTERVAL);
		this.bombs = new HashSet<Bomb>();
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
//		bgm.InGame_bgm_stop();
		bgm.InGame_bgm_play();
		bgm.getIsMuted();


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
		timer.update();

		if (pause) { // Game Pause, press ENTER to continue or BackSpace to quit
			pause = !inputManager.isKeyDown(KeyEvent.VK_ENTER);
			boolean exit = inputManager.isKeyDown(KeyEvent.VK_BACK_SPACE);
			if (exit) {
				this.returnCode = 1;
				this.lives = 0;
				this.isRunning = false;
				bgm.InGame_bgm_stop();
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

					if (this.ship.getSpeed() >= 0)
					{
						if (moveRight && !isRightBorder && getActivatedType() != 3) {
							this.ship.moveRight();
						}
						if (moveLeft && !isLeftBorder && getActivatedType() != 3) {
							this.ship.moveLeft();
						}
					} else {
						if (moveRight && !isLeftBorder && getActivatedType() != 3) {
							this.ship.moveRight();
						}
						if (moveLeft && !isRightBorder && getActivatedType() != 3) {
							this.ship.moveLeft();
						}
					}
					if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && getActivatedType() != 3) {
						if (bulletsShot % 3 == 0 && !(bulletsShot == 0)) {
							if (this.ship.shootBulletY(this.bulletsY, this.attackDamage)) {
								soundEffect.playShipShootingSound();
								this.bulletsShot++;
								this.BulletsCount--;
								this.BulletsRemaining--;
							}
						} else {
							if (this.ship.shoot(this.bullets, this.attackDamage)) {
								soundEffect.playShipShootingSound();
								this.bulletsShot++;
								this.BulletsCount--;
								this.BulletsRemaining--;
							}
						}
					}
					/**
					 * B키를 누르면 폭탄이 나갑니다! 헉!
					 * 폭탄은 데미지랑 상관 없이 한 열을 지워버리나봐요!!
					 * 너무 사기적이라 보스에는 아마 적용이 안 될 거 같아요!!
					 */
					if(!isboss && inputManager.isKeyDown(KeyEvent.VK_B) && getActivatedType() != 3
							&& bombCount > 0 && this.ship.shootBomb(this.bombs)) {
						this.bombCount--;

					}

					if(!isboss && inputManager.isKeyDown(KeyEvent.VK_N) && getActivatedType() != 3
							&& this.drill == null) {
						this.drill = new Drill(ship.getPositionX() + ship.getWidth() / 2,
								ship.getPositionY(), DRILL_SPEED);

					}
				}
				if (this.laserActivate) {
					if (this.laser != null) {
						if (this.laserLaunchCooldown.checkFinished()) {
							this.laser = null;
							this.laserCooldown.reset();
							this.nextLaserX = -1;
							this.logger.info("Laser has disappeared.");
						}
					}
					if (this.laser == null) {
						if (this.laserLoadCooldown.checkFinished() && this.nextLaserX != -1) {
							this.laserLaunchCooldown.reset();
							this.laserline = null;
							this.laser = new Laser(this.nextLaserX, SEPARATION_LINE_HEIGHT, true);
							soundEffect.playLaserSound();
							this.logger.info("Laser has been launched.");
						} else {
							if (this.nextLaserX == -1 && laserCooldown.checkFinished()) {
								this.logger.info("Laser will be launched.");
								this.nextLaserX = (int) (Math.random() * 420) + 10;
								this.laserline = new LaserLine(this.nextLaserX, SEPARATION_LINE_HEIGHT);
								this.laserLoadCooldown.reset();
							}
						}
					}
				}


				if (this.enemyShipSpecial != null) {
					if (!this.enemyShipSpecial.isDestroyed()) {
						this.enemyShipSpecial.move(2, 0);
						if (this.enemyShipSpecial.getPositionX() > this.enemyShipSpecial.getLaunchPos()
								&& !this.enemyShipSpecial.getSpBulletLoaded()) {
							this.enemyShipSpecial.LaunchSpecialBullet();
							this.SpBullet = new SpecialBullet(this.enemyShipSpecial.getPositionX(),
									this.enemyShipSpecial.getPositionY(), 10, 1,
									this.enemyShipSpecial.getColor(), this.enemyShipSpecial.getBulletType());
						}
					}
					else if (this.enemyShipSpecialExplosionCooldown.checkFinished())
						this.enemyShipSpecial = null;

				}
				if (this.enemyShipSpecial == null
						&& this.enemyShipSpecialCooldown.checkFinished()) {
					bgm.enemyShipSpecialbgm_play();
					colorVariable = (int)(Math.random()*4);
					launchPos = (int)(this.getWidth() * Math.random());
					switch (colorVariable) {
						case 0:
							this.enemyShipSpecial = new EnemyShip(Color.RED, colorVariable, launchPos);
							break;
						case 1:
							this.enemyShipSpecial = new EnemyShip(Color.YELLOW, colorVariable, launchPos);
							break;
						case 2:
							this.enemyShipSpecial = new EnemyShip(Color.BLUE, colorVariable, launchPos);
							break;
						case 3:
							this.enemyShipSpecial = new EnemyShip(Color.WHITE, colorVariable, launchPos);
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
				if (this.SpBullet != null) {
					if (this.SpBullet.getPositionY() < getHeight() && !this.SpBullet.getActivate()){
						this.SpBullet.update();
					}
					else if (this.SpecialAttackCooldown == null){
						this.SpecialAttackCooldown = Core.getCooldown(SPAttack_ACT[this.SpBullet.getType()][(int)gameSettings.getDifficulty()]);
						this.SpecialAttackCooldown.reset();
						this.SpecialAttackSpriteCooldown = Core.getCooldown(SpAtSpriteCooldown);
						this.SpecialAttackSpriteCooldown.reset();
						this.SpBullet.setActivate((int)gameSettings.getDifficulty() + 2 - (int)(gameSettings.getDifficulty()/3));
						if (this.SpBullet.getType() == 0 || this.SpBullet.getType() == 1) {
							this.SpBullet.ChangePos(this.SpBullet.getPositionX() - 4 * this.SpBullet.getWidth(),
									getHeight()-4*this.SpBullet.getHeight()+5);
						}
						if (this.SpBullet.getType() == 0)
							soundEffect.playBlazeSound();
						else if (this.SpBullet.getType() == 1)
							soundEffect.playPoisonSound();
						else if (this.SpBullet.getType() == 2)
							soundEffect.playSmogSound();
						else if (this.SpBullet.getType() == 3)
							soundEffect.playEMPSound();
						logger.info("Special Bullet has been activated");
					}
					else if (this.SpBullet.getActivate() && this.SpecialAttackCooldown.checkFinished()) {
						this.SpecialAttackCooldown = null;
						this.SpBullet = null;
						this.SpecialAttackSpriteCooldown = null;
						logger.info("Special Bullet has been disappeared");
					}
					else {
						if (this.SpecialAttackSpriteCooldown != null) {
							if (this.SpecialAttackSpriteCooldown.checkFinished()) {
								this.SpBullet.update_sprite();
								this.SpecialAttackSpriteCooldown.reset();
							}
						}
					}
				}

				if (this.SpecialAtMaintainCooldown != null){
					//System.out.println(this.SpecialAtMaintainCooldown.timePassed());
					if (this.SpecialAtDamageCooldown != null && colorVariable == 1) {
						//System.out.println(this.SpecialAtDamageCooldown.timePassed());
						if (this.SpecialAtDamageCooldown.checkFinished()) {
							//System.out.println("damaged!");
							this.lives -= this.PoisonDamage[(int)gameSettings.getDifficulty()];
							this.SpecialAtDamageCooldown.reset();
						}
					}
					if (this.SpecialAtMaintainCooldown.checkFinished()) {
						//System.out.println(this.SpecialAtMaintainCooldown.getMilliseconds());
						if (colorVariable == 0) this.ship.setSPEED(this.ship.getSpeed()*2);
						this.SpecialAtMaintainCooldown = null;
						this.SpecialAtDamageCooldown = null;
						this.ship.setColor(Color.white);
					}
				}

				if (getActivatedType() == 3) {
					if (inputManager.isKeyDown(this.EmerCode[this.SpBullet.getEmerCode()])) {
						if (!this.SpBullet.CountDown()) {
							this.SpBullet = null;
							this.SpecialAttackCooldown = null;
							this.SpecialAttackSpriteCooldown = null;
							logger.info("Special Bullet has been disappeared");
						}
					}
				}

				this.ship.update();
				this.enemyShipFormation.update();
				this.enemyShipFormation.shoot(this.bullets);

				if (this.bossCode == 2) {
					if (this.beamCooldown.checkFinished() && beamLine == null) {
						this.beamLine = new LaserLine(
								enemyShipFormation.getPositionX() + enemyShipFormation.getWidth()/2,
								enemyShipFormation.getPositionY() + 36);
						beamLine.setColor(Color.RED);
						beamCooldown.reset();
						beamLaunchCooldown.reset();
					}
					else if (this.beamLine != null && this.beam == null && beamLaunchCooldown.checkFinished()) {
						this.beamLine = null;
						enemyShipFormation.shootBeam();
						this.beam = enemyShipFormation.getBeam();
						beamLaunchCooldown.reset();
					}
					else if(this. beamLine == null && this.beam != null && beamLaunchCooldown.checkFinished()) {
						enemyShipFormation.clearBeam();
						this.beam = null;
					}
					if (this.beamLine != null) {
						beamLine.setPositionX(enemyShipFormation.getPositionX() + enemyShipFormation.getWidth()/2);
					}
					if (this.beam != null) {
						beam.setPositionX(enemyShipFormation.getPositionX()
								+ enemyShipFormation.getWidth()/2 - 32);
					}
				}
				}
			manageCollisions();
			manageCollisionsY();
			manageBombColisions();
			cleanBullets();
			cleanBulletsY();
			cleanBombs();
			cleanItems();
			if(this.drill != null){
				manageDrillColisions();
				updateDrill();
			}
			draw();
		}
		if (this.enemyShipFormation.isEmpty() && !this.levelFinished) {
			endStageAllEat();
			bgm.enemyShipSpecialbgm_stop();
			bgm.InGame_bgm_stop();
			this.levelFinished = true;
			this.screenFinishedCooldown.reset();
			timer.stop();
		}
		if (this.lives <= 0 && !this.levelFinished) {
			bgm.InGame_bgm_stop();
			this.lives = 0;
			this.ship.update();
			bgm.enemyShipSpecialbgm_stop();
			this.levelFinished = true;
			drawManager.ghostPostionX = this.ship.getPositionX();
			drawManager.ghostPostionY = this.ship.getPositionY() - 25;
			drawManager.endTimer.reset();
			drawManager.ghostTImer = System.currentTimeMillis();
			soundEffect.playShipDestructionSound();
			this.screenFinishedCooldown.reset();
			timer.stop();
		}

		if ((isItemAllEat || this.levelFinished) && this.screenFinishedCooldown.checkFinished()){
			soundEffect.playStageChangeSound();
			this.isRunning = false;
			timer.stop();
			if ((int)(timer.getElapsedTime() / 1000) > 0 && (int)(timer.getElapsedTime() / 1000) < 30) {
				this.coin.addCoin(20);
			}
			else if ((int)(timer.getElapsedTime() / 1000) >= 30 && (int)(timer.getElapsedTime() / 1000) < 40) {
				this.coin.addCoin(15);
			}
			else if ((int)(timer.getElapsedTime() / 1000) >= 40 && (int)(timer.getElapsedTime() / 1000) < 50) {
				this.coin.addCoin(10);
			}
			else{
				this.coin.addCoin(5);
			}
		}
		if ((this.BulletsCount < 0) && !this.levelFinished){
			this.BulletsCount = 0;
			bgm.InGame_bgm_stop();
			this.ship.update();
			bgm.enemyShipSpecialbgm_stop();
			this.levelFinished = true;
			drawManager.ghostPostionX = this.ship.getPositionX();
			drawManager.ghostPostionY = this.ship.getPositionY() - 25;
			drawManager.endTimer.reset();
			drawManager.ghostTImer = System.currentTimeMillis();
			soundEffect.playShipDestructionSound();
			this.screenFinishedCooldown.reset();
		}

		timer.update();
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
		if (this.laser != null) drawManager.drawBackgroundEntity(this, SEPARATION_LINE_HEIGHT
				, this.laser.getPositionX(), this.laser.getPositionY(), this.laser.getWidth(), this.laser.getHeight()
				, 255, 0, 0, 2, 6, 1);
		if (this.laserline != null) drawManager.drawBackgroundEntity(this, SEPARATION_LINE_HEIGHT
				, this.laserline.getPositionX(), this.laserline.getPositionY(), this.laserline.getWidth(), this.laserline.getHeight()
				,255, 255, 0, 2, 6, 1);
		if (getActivatedType() != -1) {
			int r, g, b;
			if (getActivatedType() == 0) {
				drawManager.drawBackgroundEntity(this, SEPARATION_LINE_HEIGHT
						, this.SpBullet.getPositionX() + this.SpBullet.getWidth()/2, this.SpBullet.getPositionY(), this.SpBullet.getWidth(), this.SpBullet.getHeight()
						, 255, 155, 0, 0,2, 2);
			}
		}

		if(!isboss){
			drawManager.bombsCount(this, this.bombCount);
		}

		drawManager.BulletsCount(this, this.BulletsCount);
		drawManager.drawEntity(this.ship, this.ship.getPositionX(),
				this.ship.getPositionY());
		drawManager.drawEntity(this.bulletLine, this.ship.getPositionX() + 12,
				this.ship.getPositionY() - 320);
		if (this.enemyShipSpecial != null)
			drawManager.drawEntity(this.enemyShipSpecial,
					this.enemyShipSpecial.getPositionX(),
					this.enemyShipSpecial.getPositionY());
		if (this.laser != null)
			drawManager.drawEntity(this.laser,
					this.laser.getPositionX(),
					this.laser.getPositionY());
		if (this.laserline != null)
			drawManager.drawEntity(this.laserline,
					this.laserline.getPositionX(),
					this.laserline.getPositionY());
		for (Item item : this.items)
			drawManager.drawEntity(item, item.getPositionX(),
					item.getPositionY());
		enemyShipFormation.draw();
		if (beamLine != null) {
			drawManager.drawEntity(this.beamLine,
					this.beamLine.getPositionX(),
					this.beamLine.getPositionY());
		}
		if (beam != null) {
			drawManager.drawEntity(beam, beam.getPositionX(),
					beam.getPositionY());
		}

		for (Bullet bullet : this.bullets)
			drawManager.drawEntity(bullet, bullet.getPositionX(),
					bullet.getPositionY());

		for (BulletY bulletY : this.bulletsY)
			drawManager.drawEntity(bulletY, bulletY.getPositionX(),
					bulletY.getPositionY());


		for (Bomb bomb : this.bombs)
			drawManager.drawEntity(bomb, bomb.getPositionX(), bomb.getPositionY());

		if(this.drill != null)
			drawManager.drawEntity(this.drill, drill.getPositionX(), drill.getPositionY());

		if (this.SpBullet != null){
			if (!this.SpBullet.getActivate())
				drawManager.drawEntity(this.SpBullet,
						this.SpBullet.getPositionX(),
						this.SpBullet.getPositionY());
			else {
				if (this.SpBullet.getType() == 0 || this.SpBullet.getType() == 1) {
					for (int i = 0; i < 8; i++) {
						drawManager.drawEntity(this.SpBullet,
								this.SpBullet.getPositionX() + i * this.SpBullet.getWidth()/8,
								this.SpBullet.getPositionY());
					}
				}
				else if (this.SpBullet.getType() == 2) {
					drawManager.DrawSmog(this);
				}
				else if (this.SpBullet.getType() == 3) {
					drawManager.EMPEmergency(this, this.SpBullet.getEmerCode());
				}
			}
		}

		// Interface.
		drawManager.drawScore(this, this.score);
		drawManager.drawLivesbar(this, this.lives);
		drawManager.drawCoin(this, this.coin, 0);
		drawManager.drawitemcircle(this,itemManager.getShieldCount(),itemManager.getBombCount());
		isboss = gameSettings.checkIsBossStage();

		// Check if the 1 key is pressed
		if (inputManager.isKeyPressedOnce(KeyEvent.VK_1)) {
			if (itemManager.getShieldCount() > 0 && timer.getElapsedTime() != 0 && ship.getShieldState() != true && !levelFinished)
			{
				logger.info("Key number 1 press");	
				itemManager.PlusShieldCount(-1);
				ship.setShieldState(true);
				ship.update();
			}


		}
		else if (inputManager.isKeyPressedOnce(KeyEvent.VK_2) & timer.getElapsedTime() != 0 && enemyShipFormation.isEmpty() == false)
		{
			if (itemManager.getBombCount() > 0)
			{
				logger.info("Key number 2 press");
				itemManager.PlusBombCount(-1);
				this.enemyShipFormation.bombDestroy(items);
			}

		}


		if (isboss) {
			for (EnemyShip enemyShip : this.enemyShipFormation)
				drawManager.drawBossLivesbar(this, enemyShip.getEnemyLife());
		}
		drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);
		drawManager.scoreEmoji(this, this.score);
		drawManager.BulletsCount(this, this.BulletsCount);
		drawManager.drawLevel(this, this.level);
		drawManager.drawSoundButton1(this);
		if (inputManager.isKeyDown(KeyEvent.VK_C)) {
			isSoundOn = !isSoundOn;
			if (isSoundOn) {
				bgm.InGame_bgm_play();
			} else {
				bgm.InGame_bgm_stop();
				bgm.enemyShipSpecialbgm_stop();
				soundEffect.SoundEffect_stop();

			}
		}
		drawManager.drawSoundStatus1(this, isSoundOn);

		drawManager.drawTimer(this, timer.getElapsedTime());
		if(Miss==1) {
			drawManager.ComboCount(this, this.combo);
		}
		//GameOver
		drawManager.gameOver(this, this.levelFinished, this.lives,this.BulletsCount, this.timer, this.coin, this.clearCoin);
		drawManager.changeGhostColor(this.levelFinished, this.lives);
		drawManager.drawGhost(this.levelFinished, this.lives);
		this.ship.gameEndShipMotion(this.levelFinished, this.lives);

		
		// Countdown to game start.
		if (!this.inputDelay.checkFinished()) {
			int countdown = (int) ((INPUT_DELAY
					- (System.currentTimeMillis()
					- this.gameStartTime)) / 1000);

			drawManager.drawCountDown(this, this.level, countdown, this.bonusLife);

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
			if (bullet.getPositionY() > this.height){
				bullet.getPositionY();
			}
			if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bullet.getPositionY() > this.height) {
				recyclable.add(bullet);
			}
		}
		this.bullets.removeAll(recyclable);
		BulletPool.recycle(recyclable);
	}
	private void cleanBulletsY() {
		Set<BulletY> recyclable = new HashSet<BulletY>();
		for (BulletY bulletY : this.bulletsY) {
			bulletY.update();
			if (bulletY.getPositionY() > this.height){
				bulletY.getPositionY();
			}
			if (bulletY.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bulletY.getPositionY() > this.height) {
				recyclable.add(bulletY);
			}
		}
		this.bulletsY.removeAll(recyclable);
		BulletPool.recycleBulletY(recyclable);
	}

	private void cleanBombs() {
		Set<Bomb> recyclable = new HashSet<>();
		for(Bomb bomb : this.bombs) {
			bomb.update();
			if(bomb.getPositionY() < SEPARATION_LINE_HEIGHT || bomb.getPositionY() > this.height)
				recyclable.add(bomb);
		}
		this.bombs.removeAll(recyclable);
		BombPool.recycle(recyclable);
	}

	private void updateDrill() {
		this.drill.update();
		if(this.drill.getPositionY() < SEPARATION_LINE_HEIGHT || this.drill.getPositionY() > this.height)
			this.drill = null;
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
					if (this.ship.getShieldState()){
						this.ship.setShieldState(false);
					} else {
						if (!this.ship.isDestroyed()) {
							this.ship.destroy();
							if (this.lives != 1) soundEffect.playShipCollisionSound();
							this.lives--;
							if (gameSettings.getDifficulty() == 3) this.lives = 0;
							this.logger.info("Hit on player ship, " + this.lives
									+ " lives remaining.");
						}
					}
				}
			} else {
				for (EnemyShip enemyShip : this.enemyShipFormation) {
					if (!enemyShip.isDestroyed()
							&& checkCollision(bullet, enemyShip)) {
						enemyShip.reduceEnemyLife(bullet.getDamage()); // 수정
						this.logger.info("Attack the enemy with " + bullet.getDamage()
							+ " of damage.");
						soundEffect.playEnemyDestructionSound();
						this.combo++;
						this.score += combo;
						this.Miss =1;
						if(enemyShip.getEnemyLife() < 1) {
							this.score += enemyShip.getPointValue();
							this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
							this.shipsDestroyed++;
							this.shipsDestroyed += this.enemyShipFormation.getShipsDestroyed();
							this.logger.info("Current Number of Ships Destroyed : " + this.shipsDestroyed);
							// if(){ this.levelFinished = True; }
						}
						recyclableBullet.add(bullet);
					}
				}
				if (bullet.getPositionY()<50){
					combo =0;
					Miss =1;
				}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bullet, this.enemyShipSpecial)) {
					enemyShipSpecial.reduceEnemyLife(bullet.getDamage());
					this.logger.info("Attack the enemy with " + bullet.getDamage()
						+ " of damage.");
					this.combo ++;
					this.score += combo;
					this.Miss =1;
					if(enemyShipSpecial.getEnemyLife() < 1) {
						this.score += this.enemyShipSpecial.getPointValue();
						this.shipsDestroyed++;
						this.enemyShipSpecial.destroy(this.items);
						soundEffect.enemyshipspecialDestructionSound();
						bgm.enemyShipSpecialbgm_stop();
						if (this.lives < 2.9) this.lives = this.lives + 0.1;
						this.enemyShipSpecialExplosionCooldown.reset();
					}
					recyclableBullet.add(bullet);
				}
				if (bullet.getPositionY()<50){
					combo =0;
					Miss =1;
				}
			}
		if (this.laser != null) {
			if (checkCollision(this.laser, this.ship) && !this.levelFinished) {
				if (!this.ship.isDestroyed()) {
					this.ship.destroy();
					if (this.lives != 1) soundEffect.playShipCollisionSound();
					this.lives--;
					if (gameSettings.getDifficulty() == 3) this.lives = 0;
					this.logger.info("Hit on player ship, " + this.lives
							+ " lives remaining.");
				}
			}
		}
		if (this.beam != null) {
			if (checkCollision(this.beam, this.ship) && !this.levelFinished) {
				if (!this.ship.isDestroyed()) {
					this.ship.destroy();
					this.lives = 0;
					this.logger.info("Hit on player ship, " + this.lives
							+ " lives remaining.");
				}
			}
		}
		if (this.SpBullet != null) {
			if (checkCollision(this.SpBullet, this.ship) && !this.levelFinished) {
				if (!this.ship.isDestroyed()) {
					if (gameSettings.getDifficulty() == 3) this.lives = 0;
					if (!this.SpBullet.getActivate()) {
						this.ship.destroy();
						if (this.lives != 1) soundEffect.playShipCollisionSound();
						this.lives--;
						this.logger.info("Hit on player ship, " + this.lives
								+ " lives remaining.");
					}
					else if (this.SpBullet.getType() == 0) {
						if (this.SpecialAtDamageCooldown == null) {
							//System.out.println("On Fire!");
							this.SpecialAtDamageCooldown = Core.getCooldown(this.BlazeDamageCooldown[(int)gameSettings.getDifficulty()]);
							this.SpecialAtDamageCooldown.reset();
						}
						else if (this.SpecialAtDamageCooldown.checkFinished()) {
							this.lives -= this.BlazeDamage[(int)gameSettings.getDifficulty()];
							this.SpecialAtDamageCooldown.reset();
						}
						if (this.SpecialAtMaintainCooldown == null) {
							this.SpecialAtMaintainCooldown = Core.getCooldown(this.BlazeMaintainCooldown[(int)gameSettings.getDifficulty()]);
							this.ship.setColor(Color.RED);
							this.ship.setSPEED(this.ship.getSpeed()/2);
							this.SpecialAtMaintainCooldown.reset();
						}
						else {
							this.SpecialAtMaintainCooldown.reset();
						}
					}
					else if (this.SpBullet.getType() == 1) {
						if (this.SpecialAtMaintainCooldown == null) {
							this.SpecialAtMaintainCooldown = Core.getCooldown(this.PoisonMaintainCooldown[(int)gameSettings.getDifficulty()]);
							//System.out.println("Poisoned!");
							this.ship.setColor(new Color(0,66,0));
							this.SpecialAtMaintainCooldown.reset();
						}
						else {
							this.SpecialAtMaintainCooldown.reset();
						}
						if (this.SpecialAtDamageCooldown == null) {
							this.SpecialAtDamageCooldown = Core.getCooldown(this.PoisonDamageCooldown[(int)gameSettings.getDifficulty()]);
							this.SpecialAtDamageCooldown.reset();
						}
					}
				}
			}
			else {
				if (this.SpBullet.getActivate() && this.SpecialAtDamageCooldown != null && this.SpBullet.getType() != 1) {
					this.SpecialAtDamageCooldown = null;
				}
			}
		}
		for (Item item : this.items){
			if(checkCollision(item, this.ship) && !this.levelFinished){
				recyclableItem.add(item);
				this.logger.info("Get Item ");

				//* settings of coins randomly got when killing monsters
				ArrayList<Integer> coinProbability = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 2, 3, 4));
				Random random = new Random();
				int randomIndex = random.nextInt(coinProbability.size());

				if(item.getSpriteType() == SpriteType.Coin){
					this.coin.addCoin(coinProbability.get(randomIndex));

				}
				if(item.getSpriteType() == SpriteType.BlueEnhanceStone){
					this.enhanceManager.PlusNumEnhanceStoneArea(1);
				}
				if(item.getSpriteType() == SpriteType.PerpleEnhanceStone){
					this.enhanceManager.PlusNumEnhanceStoneAttack(1);
				}
				this.ship.checkGetItem(item);
			}
		}
		for (Bullet bullet : recyclableBullet) {
			if (bullet.getSpeed() < 0 && bullet.isEffectBullet() == 0) {
				bullet.splash(this.bullets);
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
						enemyShip.reduceEnemyLife(bulletY.getDamage()); // 수정
						this.logger.info("Attack the enemy with " + bulletY.getDamage()
							+ " of damage.");
						soundEffect.playEnemyDestructionSound();
						this.combo ++;
						this.score += combo;
						if(enemyShip.getEnemyLife() < 1) {
							this.score += enemyShip.getPointValue();
							this.shipsDestroyed++;
							this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
							// this.enemyShipFormation.areaDestory(enhanceManager.getlvEnhanceArea(), 3, this.items);
						}
						recyclableBulletY.add(bulletY);
					}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bulletY, this.enemyShipSpecial)) {
					enemyShipSpecial.reduceEnemyLife(bulletY.getDamage());
					this.logger.info("Attack the enemy with " + bulletY.getDamage()
						+ " of damage.");
					this.combo ++;
					this.score += combo;
					this.Miss =1;
					if(enemyShipSpecial.getEnemyLife() < 1) {
						this.score += this.enemyShipSpecial.getPointValue();
						this.shipsDestroyed++;
						this.enemyShipSpecial.destroy(this.items);
						soundEffect.enemyshipspecialDestructionSound();
						bgm.enemyShipSpecialbgm_stop();
						if (this.lives < 2.9) this.lives = this.lives + 0.1;
						this.enemyShipSpecialExplosionCooldown.reset();
					}
					recyclableBulletY.add(bulletY);
				}
				if (bulletY.getPositionY()<50){
					combo =0;
					Miss =1;
				}
			}
		this.items.removeAll(recyclableItem);
		this.bulletsY.removeAll(recyclableBulletY);
		ItemPool.recycle(recyclableItem);
		BulletPool.recycleBulletY(recyclableBulletY);
	}

	private void manageBombColisions() {
		Set<Bomb> recyclableBomb = new HashSet<>();
		for(Bomb bomb : this.bombs) {
			for(EnemyShip enemyShip : this.enemyShipFormation) {
				if(!enemyShip.isDestroyed() && checkCollision(bomb, enemyShip)) {
					areaDestroy(enemyShip);
					recyclableBomb.add(bomb);
				}
			}

			if (this.enemyShipSpecial != null
					&& !this.enemyShipSpecial.isDestroyed()
					&& checkCollision(bomb, this.enemyShipSpecial)) {

				this.score += this.enemyShipSpecial.getPointValue();
				this.shipsDestroyed++;
				this.enemyShipSpecial.destroy(this.items);
				soundEffect.enemyshipspecialDestructionSound();
				bgm.enemyShipSpecialbgm_stop();
				if (this.lives < 2.9) this.lives = this.lives + 0.1;
				this.enemyShipSpecialExplosionCooldown.reset();

				recyclableBomb.add(bomb);
			}
		}
		this.bombs.removeAll(recyclableBomb);
		BombPool.recycle(recyclableBomb);
	}

	private void manageDrillColisions() {
		for(EnemyShip enemyShip : this.enemyShipFormation) {
			if(!enemyShip.isDestroyed() && checkCollision(this.drill, enemyShip)) {
				this.score += enemyShip.getPointValue();
				this.shipsDestroyed++;
				this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
			}
		}

		if (this.enemyShipSpecial != null
				&& !this.enemyShipSpecial.isDestroyed()
				&& checkCollision(this.drill, this.enemyShipSpecial)) {
			this.score += this.enemyShipSpecial.getPointValue();
			this.shipsDestroyed++;
			this.enemyShipSpecial.destroy(this.items);
			soundEffect.enemyshipspecialDestructionSound();
			bgm.enemyShipSpecialbgm_stop();
			if (this.lives < 2.9) this.lives = this.lives + 0.1;
			this.enemyShipSpecialExplosionCooldown.reset();
		}
	}

	private void areaDestroy(EnemyShip enemyShip) {
		int col = -1, row = -1;
		List<List<EnemyShip>> enemyShips = this.enemyShipFormation.getEnemyShips();
		for(List<EnemyShip> column : enemyShips) {
			if(column.contains(enemyShip)) {
				col = enemyShips.indexOf(column);
			}
		}
		List<EnemyShip> column = enemyShips.get(col);
		row = column.indexOf(enemyShip);
		this.score += enemyShip.getPointValue();
		this.shipsDestroyed++;
		this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
		for(int dir = 0; dir < 8; dir++) {
			int nRow = row + DX[dir];
			int nCol = col + DY[dir];
			if(nRow < 0 || nCol < 0 ||  nCol >= enemyShips.size() || nRow >= enemyShips.get(nCol).size()) {
				continue;
			}
			EnemyShip enemy = enemyShips.get(nCol).get(nRow);
			if(!enemy.isDestroyed()) {
				this.score += enemyShip.getPointValue();
				this.shipsDestroyed++;
				this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemy, this.items);
			}
		}
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
		return new GameState(this.level, this.score, this.coin, this.lives,
							this.bulletsShot, this.shipsDestroyed, this.hardcore, 
							this.shipColor, this.nowSkinString, this.ownedSkins, this.equippedSkins, 
							this.BulletsRemaining);
	}
	public Ship getShip(){
		return ship;
	}
	public String getClearCoin() {
		return this.clearCoin;
	}

	public int getActivatedType() {
		if (this.SpBullet != null) {
			if (this.SpBullet.getActivate())
				return this.SpBullet.getType();
			else
				return -1;
		}
		return -1;
	}
}