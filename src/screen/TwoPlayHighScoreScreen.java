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
public class TwoPlayHighScoreScreen extends Screen {

    /** List of past high scores. */
    private List<Score> highScores_EASY;
    private List<Score> highScores_NORMAL;
    private List<Score> highScores_HARD;
    private List<Score> highScores_HARDCORE;
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
    public TwoPlayHighScoreScreen(final int width, final int height, final int fps) {
        super(width, height, 60);
        this.SelectCooldown = Core.getCooldown(200);
        this.SelectCooldown.reset();
        this.returnCode = 1;
        this.difficulty = 0;
        try {
            this.highScores_EASY = Core.getFileManager().load2PHighScores(0);
            this.highScores_NORMAL = Core.getFileManager().load2PHighScores(1);
            this.highScores_HARD = Core.getFileManager().load2PHighScores(2);
            this.highScores_HARDCORE = Core.getFileManager().load2PHighScores(3);
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
        if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                && this.SelectCooldown.checkFinished()) {
            this.difficulty += 1;
            if (this.difficulty > 3)
                this.difficulty = 0;
            this.SelectCooldown.reset();
        }
        else if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                && this.SelectCooldown.checkFinished()) {
            this.difficulty -= 1;
            if (this.difficulty < 0)
                this.difficulty = 3;
            this.SelectCooldown.reset();
        }
        else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)
                && this.inputDelay.checkFinished())
            this.isRunning = false;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawHighScoreMenu(this);
        drawManager.drawDiffScore(this, this.difficulty);
        if (this.difficulty == 0)
            drawManager.drawHighScores(this, this.highScores_EASY);
        else if (this.difficulty == 1)
            drawManager.drawHighScores(this, this.highScores_NORMAL);
        else if (this.difficulty == 2)
            drawManager.drawHighScores(this, this.highScores_HARD);
        else
            drawManager.drawHighScores(this, this.highScores_HARDCORE);

        drawManager.completeDrawing(this);
    }
}
