package screen;

import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;
import engine.GameState;

public class EhanceScreen extends Screen {
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Height of the interface separation line. */
    private static final int SEPARATION_LINE_HEIGHT = 40;
    /** Current score. */
    private int score;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private int enhanceStone = 0;
    private int numEnhanceArea = 0;
    private int numEnhanceDamage = 0;

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
    public EhanceScreen(final GameState gameState, final int width, final int height, final int fps) {
        super(width, height, fps);

        // Defaults to play.
        this.returnCode = 8;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.score = gameState.getScore();
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
                previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
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
        if (this.returnCode == 8)
            this.returnCode = 9;
        else if (this.returnCode == 9)
            this.returnCode = 6;
        else if (this.returnCode == 6)
            this.returnCode = 2;
        else
            this.returnCode = 8;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (this.returnCode == 8)
            this.returnCode = 2;
        else if (this.returnCode == 2)
            this.returnCode = 6;
        else if (this.returnCode == 6)
            this.returnCode = 9;
        else
            this.returnCode = 8;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawScore(this, this.score);
        drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);
        drawManager.drawEnhanceElem(this, this.enhanceStone, this.numEnhanceArea, this.numEnhanceDamage);

        // drawManager.drawTitle(this);
        drawManager.drawEnhancePage(this, this.returnCode, this.enhanceStone, this.numEnhanceArea,
                this.numEnhanceDamage);

        drawManager.completeDrawing(this);
    }
}
