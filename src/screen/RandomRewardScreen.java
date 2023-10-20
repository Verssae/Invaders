package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
import engine.GameState;
import entity.Coin;

public class RandomRewardScreen extends Screen {
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private Coin coin;
    private int randomRes;
    private String rewardTypeString;
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
    public RandomRewardScreen(final GameState gameState, int width, int height, int fps, int randomRes) {
        super(width, height, fps);

        // Defaults to play.
        this.coin = gameState.getCoin();
        this.returnCode = 13;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.randomRes = randomRes;
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
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
                this.isRunning = false;
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextMenuItem() {
        if (this.returnCode == 13)
            this.returnCode = 7;
        else if (this.returnCode == 7)
            this.returnCode = 2;
        else
            this.returnCode = 13;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (this.returnCode == 2) 
            this.returnCode = 7; 
        else if (this.returnCode == 7) 
            this.returnCode = 13; 
        else
            this.returnCode = 2; 
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        // drawManager.drawTitle(this);
        drawManager.drawRandomReward(this, this.returnCode, this.rewardTypeString, this.randomRes);
        drawManager.completeDrawing(this);
    }
}