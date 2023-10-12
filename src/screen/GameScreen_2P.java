package screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import engine.*;
import entity.*;

/**
 * Implements the game screen, where the action happens.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class GameScreen_2P extends Screen {

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
    private Ship ship_1P;
    private Ship ship_2P;
    /** Bonus enemy ship that appears sometimes. */
    private BulletLine bulletLine_1P;
    private BulletLine bulletLine_2P;
    private EnemyShip enemyShipSpecial;
    /** Minimum time between bonus ship appearances. */
    private Cooldown enemyShipSpecialCooldown;
    /** Time until bonus ship explosion disappears. */
    private Cooldown enemyShipSpecialExplosionCooldown;
    /** Time from finishing the level to screen change. */
    private Cooldown screenFinishedCooldown;
    /** Set of all bullets fired by on screen ships. */
    private Set<Bullet> bullets;
    /** Sound Effects for player's ship and enemy. */
    private SoundEffect soundEffect;
    /** Current score. */
    private int score;
    /** Player lives left. */
    private int lives;
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


    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param gameState
     *            Current game state.
     * @param gameSettings
     *            Current game settings.
     * @param bonusLife
     *            Checks if a bonus life is awarded this level
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public GameScreen_2P(final GameState gameState,
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
    }

    /**
     * Initializes basic screen properties, and adds necessary elements.
     */
    public final void initialize() {
        super.initialize();

        enemyShipFormation = new EnemyShipFormation(this.gameSettings, 1);
        enemyShipFormation.attach(this);
        this.ship_1P = new Ship(this.width / 4, this.height - 30);
        this.bulletLine_1P = new BulletLine(this.width / 4 , this.height + 120);
        this.ship_2P = new Ship((3 * this.width / 4), this.height - 30);
        this.bulletLine_2P = new BulletLine(3 * this.width / 4 , this.height + 120);
        // Appears each 10-30 seconds.
        this.enemyShipSpecialCooldown = Core.getVariableCooldown(
                BONUS_SHIP_INTERVAL, BONUS_SHIP_VARIANCE);
        this.enemyShipSpecialCooldown.reset();
        this.enemyShipSpecialExplosionCooldown = Core
                .getCooldown(BONUS_SHIP_EXPLOSION);
        this.screenFinishedCooldown = Core.getCooldown(SCREEN_CHANGE_INTERVAL);
        this.bullets = new HashSet<Bullet>();

        // Special input delay / countdown.
        this.gameStartTime = System.currentTimeMillis();
        this.inputDelay = Core.getCooldown(INPUT_DELAY);
        this.inputDelay.reset();

        soundEffect = new SoundEffect();
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
                if (!this.ship_1P.isDestroyed()) {
                    boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_D);
                    boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_A);

                    boolean isRightBorder = this.ship_1P.getPositionX()
                            + this.ship_1P.getWidth() + this.ship_1P.getSpeed() > this.width - 1;
                    boolean isLeftBorder = this.ship_1P.getPositionX()
                            - this.ship_1P.getSpeed() < 1;

                    if (moveRight && !isRightBorder) {
                        this.ship_1P.moveRight();
                    }
                    if (moveLeft && !isLeftBorder) {
                        this.ship_1P.moveLeft();
                    }
                    if (inputManager.isKeyDown(KeyEvent.VK_SHIFT))
                        if (this.ship_1P.shoot(this.bullets)) {
                            soundEffect.playShipShootingSound();
                            this.bulletsShot++;
                        }
                }
                if (!this.ship_2P.isDestroyed()) {
                    boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT);
                    boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT);

                    boolean isRightBorder = this.ship_2P.getPositionX()
                            + this.ship_2P.getWidth() + this.ship_2P.getSpeed() > this.width - 1;
                    boolean isLeftBorder = this.ship_2P.getPositionX()
                            - this.ship_2P.getSpeed() < 1;

                    if (moveRight && !isRightBorder) {
                        this.ship_2P.moveRight();
                    }
                    if (moveLeft && !isLeftBorder) {
                        this.ship_2P.moveLeft();
                    }
                    if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
                        if (this.ship_2P.shoot(this.bullets)) {
                            soundEffect.playShipShootingSound();
                            this.bulletsShot++;
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
                    this.enemyShipSpecial = new EnemyShip(Color.RED);
                    this.enemyShipSpecialCooldown.reset();
                    this.logger.info("A special ship appears");
                }
                if (this.enemyShipSpecial != null
                        && this.enemyShipSpecial.getPositionX() > this.width) {
                    this.enemyShipSpecial = null;
                    this.logger.info("The special ship has escaped");
                }

                this.ship_1P.update();
                this.ship_2P.update();
                this.enemyShipFormation.update();
                this.enemyShipFormation.shoot(this.bullets);
            }

            manageCollisions();

            cleanBullets();
            draw();
        }
        if (this.enemyShipFormation.isEmpty() && !this.levelFinished) {
            this.levelFinished = true;
            this.screenFinishedCooldown.reset();
        }
        if (this.lives == 0 && !this.levelFinished) {
            this.levelFinished = true;
            soundEffect.playShipDestructionSound();
            this.screenFinishedCooldown.reset();
        }
        if (this.levelFinished && this.screenFinishedCooldown.checkFinished())
            this.isRunning = false;

    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawEntity(this.ship_1P, this.ship_1P.getPositionX(),
                this.ship_1P.getPositionY());
        drawManager.drawEntity(this.bulletLine_1P, this.ship_1P.getPositionX() + 12,
                this.ship_1P.getPositionY() - 320);

        drawManager.drawEntity(this.ship_2P, this.ship_2P.getPositionX(),
                this.ship_2P.getPositionY());
        drawManager.drawEntity(this.bulletLine_2P, this.ship_2P.getPositionX() + 12,
                this.ship_2P.getPositionY() - 320);

        if (this.enemyShipSpecial != null)
            drawManager.drawEntity(this.enemyShipSpecial,
                    this.enemyShipSpecial.getPositionX(),
                    this.enemyShipSpecial.getPositionY());

        enemyShipFormation.draw();

        for (Bullet bullet : this.bullets)
            drawManager.drawEntity(bullet, bullet.getPositionX(),
                    bullet.getPositionY());


        // Interface.
        drawManager.drawScore(this, this.score);
        //drawManager.drawLives(this, this.lives);
        drawManager.drawLivesbar(this, this.lives);
        drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);
        drawManager.scoreEmoji(this, this.score);

        // Countdown to game start.
        if (!this.inputDelay.checkFinished()) {
            int countdown = (int) ((INPUT_DELAY
                    - (System.currentTimeMillis()
                    - this.gameStartTime)) / 1000);
            drawManager.drawCountDown(this, this.level, countdown,
                    this.bonusLife);

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

    /**
     * Manages collisions between bullets and ships.
     */
    private void manageCollisions() {
        Set<Bullet> recyclable = new HashSet<Bullet>();
        for (Bullet bullet : this.bullets)
            if (bullet.getSpeed() > 0) {
                if (checkCollision(bullet, this.ship_1P) && !this.levelFinished) {
                    recyclable.add(bullet);
                    if (!this.ship_1P.isDestroyed()) {
                        this.ship_1P.destroy();
                        if (this.lives != 1) soundEffect.playShipCollisionSound();
                        this.lives--;
                        this.logger.info("Hit on player ship, " + this.lives
                                + " lives remaining.");
                    }
                }
                else if (checkCollision(bullet, this.ship_2P) && !this.levelFinished) {
                    recyclable.add(bullet);
                    if (!this.ship_2P.isDestroyed()) {
                        this.ship_2P.destroy();
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
                        soundEffect.playEnemyDestructionSound();
                        this.score += enemyShip.getPointValue();
                        this.shipsDestroyed++;
                        this.enemyShipFormation.destroy(enemyShip);
                        recyclable.add(bullet);
                    }
                if (this.enemyShipSpecial != null
                        && !this.enemyShipSpecial.isDestroyed()
                        && checkCollision(bullet, this.enemyShipSpecial)) {
                    this.score += this.enemyShipSpecial.getPointValue();
                    this.shipsDestroyed++;
                    this.enemyShipSpecial.destroy();
                    this.enemyShipSpecialExplosionCooldown.reset();
                    recyclable.add(bullet);
                }
            }

        this.bullets.removeAll(recyclable);
        BulletPool.recycle(recyclable);
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