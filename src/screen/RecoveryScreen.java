package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;

public class RecoveryScreen extends Screen{
    

    /** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;

	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;

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
    public RecoveryScreen(int width, int height, int fps) {
        super(width, height, fps);
        
         // Defaults to play.
         this.returnCode = 5;
         this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
         this.selectionCooldown.reset();
    }
    
}
