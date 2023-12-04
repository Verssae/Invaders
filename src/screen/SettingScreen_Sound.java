package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
import engine.SoundEffect;
import engine.SoundManager;


public class SettingScreen_Sound extends Screen{
    private static final int SELECTION_TIME = 200;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    /** For selection moving sound */
    private SoundEffect soundEffect;


    public SettingScreen_Sound(int width, int height, int fps) {
        super(width, height, fps);

        this.returnCode = 1;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

        soundEffect = new SoundEffect();
    }

    public final int run() {
        super.run();

        return this.returnCode;
    }

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
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)){
                soundEffect.playSpaceButtonSound();
                this.isRunning = false;
            }
        }
    }
    private void nextMenuItem() {
        if (this.returnCode == 1)
            this.returnCode = 6;
        else if (this.returnCode == 6)
            this.returnCode = 103;
        else if (this.returnCode == 103)
            this.returnCode = 104;
        else if (this.returnCode ==104)
            this.returnCode = 6;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (this.returnCode == 1)
            this.returnCode = 104;
        else if (this.returnCode == 6)
            this.returnCode = 104;
        else if (this.returnCode == 103)
            this.returnCode = 6;
        else if (this.returnCode == 104)
            this.returnCode = 103;
    }
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSoundSettingMenu(this, this.returnCode);

        drawManager.completeDrawing(this);
    }
}