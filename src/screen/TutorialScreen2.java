package screen;

import engine.Cooldown;
import engine.Core;
import engine.SoundEffect;

import java.awt.event.KeyEvent;

public class TutorialScreen2 extends Screen{

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    /** For selection moving sound */
    private SoundEffect soundEffect;
    public TutorialScreen2(int width, int height, int fps) {
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
            if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
                soundEffect.playSpaceButtonSound();
                this.previousMenuItem();
                this.selectionCooldown.reset();
            }
            else if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
                soundEffect.playSpaceButtonSound();
                this.nextMenuItem();
                this.selectionCooldown.reset();
            }
            else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                soundEffect.playSpaceButtonSound();
                this.selectionCooldown.reset();
                this.isRunning = false;
            }
        }
    }

    public final int run() {
        super.run();

        return this.returnCode;
    }

    private void nextMenuItem() {
        if (this.returnCode == 5) {
            this.returnCode = 1;
        }
        else if (this.returnCode == 1) {
            this.returnCode = 5;
        }
    }

    private void previousMenuItem() {
        if (this.returnCode == 5) {
            this.returnCode = 1;
        }
        else if (this.returnCode == 1) {
            this.returnCode = 5;
        }
    }



    public void draw() {
        drawManager.initDrawing(this);

        drawManager.drawTutorial2(this, this.returnCode);

        drawManager.completeDrawing(this);
    }
}
