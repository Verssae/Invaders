package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
import engine.GameSettings;
import engine.GameState;
import entity.Coin;
import engine.DrawManager;
public class RandomBoxScreen extends Screen {
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private Coin coin;
    public String getRandomCoin;
    private GameState gameState;
    private int randomCoin;
    /**
     * Constructor, establishes the properties of the screen.
     * 
     * @param gameState
     *                  Current game state.
     * @param width
     *                  Screen width.
     * @param height
     *                  Screen height.
     * @param fps
     *                  Frames per second, frame rate at which the game is run.
     */
    public RandomBoxScreen(final GameState gameState, int width, int height, int fps) {
        super(width, height, fps);

        // Defaults to play.
        this.gameState = gameState;
        this.coin = gameState.getCoin();
        this.returnCode = 20;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.randomCoin = 0;
    }
 
    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }
    /**
	 * Returns a GameState object representing the status of the game.
	 *
	 * @return Current game state.
	 */
	public final GameState getGameState() {
		return this.gameState;
	}

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();
        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                || inputManager.isKeyDown(KeyEvent.VK_D)) {
                nextMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)){
                if (this.returnCode == 20) {
                    int randomCoin = (int) (Math.random() * 11) * 5;
    			    getRandomCoin = Integer.toString(randomCoin);
                    this.coin.addCoin(randomCoin);
                    this.gameState.setCoin(this.coin);
                    this.randomCoin = randomCoin;
                }
                if (this.returnCode == 21) {
                    int randomCoin = (int) (Math.random() * 11) * 5;
    			    getRandomCoin = Integer.toString(randomCoin);
                    this.coin.addCoin(randomCoin);
                    this.gameState.setCoin(this.coin);
                    this.randomCoin = randomCoin;
                }
                if (this.returnCode == 22) {
                    int randomCoin = (int) (Math.random() * 11) * 5;
    			    getRandomCoin = Integer.toString(randomCoin);
                    this.coin.addCoin(randomCoin);
                    this.gameState.setCoin(this.coin);
                    this.randomCoin = randomCoin;
                }
                this.isRunning = false;
            }
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextMenuItem() {
        if (this.returnCode == 20)
            this.returnCode = 21;
        else if (this.returnCode == 21)
            this.returnCode = 22;
        else
            this.returnCode = 20;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (this.returnCode == 22) 
            this.returnCode = 21; 
        else if (this.returnCode == 21) 
            this.returnCode = 20; 
        else
            this.returnCode = 22; 
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        // drawManager.drawTitle(this);
        drawManager.drawRandomBox(this, this.returnCode); // 3개 인자 
        drawManager.completeDrawing(this);
    }

    public int getRandomCoin(){
        return this.randomCoin;
    }
}
