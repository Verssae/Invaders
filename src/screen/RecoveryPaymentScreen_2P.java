package screen;

import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;
import engine.SoundEffect;
import engine.GameState_2P;


public class RecoveryPaymentScreen_2P extends Screen {


    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    /** For selection moving sound */
    private SoundEffect soundEffect;

    /** To retrieve the number of coins obtained during the game. */
    private GameState_2P gameState;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *               Screen width.
     * @param height
     *               Screen height.
     * @param fps
     *               Frames per second, frame rate at which the game is run.
     */
    public RecoveryPaymentScreen_2P(GameState_2P gameState, int width, int height, int fps ) {
        super(width, height, fps);

        // Defaults to play.
        this.returnCode = 51;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.gameState = gameState;

        soundEffect = new SoundEffect();
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
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                soundEffect.playButtonClickSound();
                previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                soundEffect.playButtonClickSound();
                nextMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                soundEffect.playSpaceButtonSound();
                this.isRunning = false;
            }
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextMenuItem() {

        if (this.returnCode == 51)
            this.returnCode = 52;
        else
            this.returnCode = 51;


    }

    /**
     * Shifts the focus to the previous menu item.
     */

    private void previousMenuItem() {

        if (this.returnCode == 52)
            this.returnCode = 51;
        else
            this.returnCode = 52;

    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawRecoveryConfirmPage_2P(this.gameState, this,this.returnCode);

        drawManager.completeDrawing(this);
    }
}
