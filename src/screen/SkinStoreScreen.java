package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
import engine.GameState;
import engine.SoundEffect;

public class SkinStoreScreen extends Screen {
     /** Milliseconds between changes in user selection. */
     private static final int SELECTION_TIME = 200;
  
     /** Time between changes in user selection. */
     private Cooldown selectionCooldown;
     private int enhanceStone = 0;
     private int numEnhanceArea = 0;
     private int numEnhanceDamage = 0;
    /** For selection moving sound */
    private SoundEffect soundEffect;
 
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
     public SkinStoreScreen(final int width, final int height, final int fps) {
         super(width, height, fps);
 
         // Defaults to play.
         this.returnCode = 86;
         this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
         this.selectionCooldown.reset();
        //  this.score = gameState.getScore();

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
         if (this.returnCode == 16)
             this.returnCode = 16; //화면 넣을 순서 정해지면 변경
         else if (this.returnCode == 16)
             this.returnCode = 2;
        else
            this.returnCode = 16;
     }
 
     /**
      * Shifts the focus to the previous menu item.
      */
     private void previousMenuItem() {
        if (this.returnCode == 2)
             this.returnCode = 16;
        else if (this.returnCode == 16)
            this.returnCode = 16;
        else
             this.returnCode = 2;
     } 
 
     /**
      * Draws the elements associated with the screen.
      */
     private void draw() {
         drawManager.initDrawing(this);
         drawManager.drawSkinStore(this, this.returnCode);
         drawManager.completeDrawing(this);
     }
    
}
