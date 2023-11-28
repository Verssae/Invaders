package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import engine.Cooldown;
import engine.Core;
import engine.Score;

/**
 * Implements the high scores screen, it shows player records.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class PvpScoreScreen extends Screen {

    /** List of past high scores. */
    private List<Score> pvpScores;
    private int difficulty;
    private Cooldown SelectCooldown;
    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public PvpScoreScreen(final int width, final int height, final int fps) {
        super(width, height, 60);
        this.SelectCooldown = Core.getCooldown(200);
        this.SelectCooldown.reset();
        this.returnCode = 1;
        this.difficulty = 0;
        try {
            this.pvpScores = Core.getFileManager().loadPvpScores();
        } catch (NumberFormatException | IOException e) {
            logger.warning("Couldn't load high scores!");
        }
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
        if(inputManager.isKeyDown(KeyEvent.VK_SPACE)&& this.inputDelay.checkFinished()){
            this.isRunning = false;
        }
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawPvpScoreMenu(this);
        drawManager.drawPvpScores(this, this.pvpScores);
        drawManager.completeDrawing(this);
    }
}
