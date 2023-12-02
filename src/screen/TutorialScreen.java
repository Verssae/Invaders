package screen;

import engine.Cooldown;
import engine.Core;
import engine.SoundEffect;

import java.awt.event.KeyEvent;

public class TutorialScreen extends Screen{
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    /** For selection moving sound */
    private SoundEffect soundEffect;




    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public TutorialScreen(int width, int height, int fps) {
        super(width, height, fps);

        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        soundEffect = new SoundEffect();
        this.returnCode = 5;
    }

    protected final void update() {
        super.update();
        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                soundEffect.playSpaceButtonSound();
                this.returnCode = 1;        // Return to main menu.
                this.isRunning = false;
            }
        }
    }

    public final int run() {
        super.run();

        return this.returnCode;
    }

    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawTutorial(this);
        drawManager.drawHorizontalLine(this, this.getHeight()/2 - 20);
        drawManager.drawVerticalLine(this, this.getWidth()/2 - 1);

        drawManager.completeDrawing(this);
    }
}
