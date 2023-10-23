package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
import engine.GameSettings;
import engine.GameState;
import entity.Coin;
import engine.DrawManager;
import engine.EnhanceManager;
public class RandomBoxScreen extends Screen {
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private Coin coin;
    public String getRandomCoin;
    private GameState gameState;
    private int randomRes;
    private String rewardTypeString;
    private EnhanceManager enhanceManager;
    private int BST;
    private int PST;
    
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
    public RandomBoxScreen(final GameState gameState, int width, int height, int fps, final EnhanceManager enhanceManager) {
        super(width, height, fps);

        // Defaults to play.
        this.gameState = gameState;
        this.coin = gameState.getCoin();
        this.returnCode = 20;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.randomRes = 0;
        this.BST = enhanceManager.getNumEnhanceStoneArea();
        this.PST = enhanceManager.getNumEnhanceStoneAttack();
        this.enhanceManager = enhanceManager;
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
	 * Returns a GameState object representing the status of the game.
	 *
	 * @return Current game state.
	 */
	public final GameState getGameState() {
		return this.gameState;
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
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)){
                if (this.returnCode == 20) {
                    int probability = (int) (Math.random() * 10);
                    if (probability >= 0 && probability <= 7) {
                        int randomCoin = (int) (Math.random() * 11) * 5;
                        getRandomCoin = Integer.toString(randomCoin);
                        this.coin.addCoin(randomCoin);
                        this.gameState.setCoin(this.coin);
                        this.randomRes = randomCoin;
                        this.rewardTypeString = "C O I N ";
                    }
                    else{
                        if (probability == 8){
                            int randomEnhance = (int) (Math.random() * 4);
                            this.enhanceManager.PlusNumEnhanceStoneArea(randomEnhance);
                            this.randomRes = randomEnhance;
                            this.rewardTypeString = "BLUE ENHANCE STONE";
                        }
                        else{
                            int randomEnhance = (int) (Math.random() * 4);
                            this.enhanceManager.PlusNumEnhanceStoneAttack(randomEnhance);
                            this.randomRes = randomEnhance;
                            this.rewardTypeString = "PURPLE ENHANCE STONE";
                            }
                        }
                    }
                
                
                if (this.returnCode == 21) {
                    int rewardType =  (int) (Math.random() * 2); //0 : coin, 1: 강화석
                    if (rewardType == 0){
                        int randomCoin = (int) (Math.random() * 11) * 5;
                        getRandomCoin = Integer.toString(randomCoin);
                        this.coin.addCoin(randomCoin);
                        this.gameState.setCoin(this.coin);
                        this.randomRes = randomCoin;
                        this.rewardTypeString = "C O I N ";
                    }
                    else{
                        int randomEnhanceType = (int) (Math.random() * 2); // 0: bst, 1: pst
                        if (randomEnhanceType == 0){
                            int randomEnhance = (int) (Math.random() * 4);
                            this.enhanceManager.PlusNumEnhanceStoneArea(randomEnhance);
                            this.randomRes = randomEnhance;
                            this.rewardTypeString = "BLUE ENHANCE STONE";
                        }
                        else{
                            int randomEnhance = (int) (Math.random() * 4);
                            this.enhanceManager.PlusNumEnhanceStoneAttack(randomEnhance);
                            this.randomRes = randomEnhance;
                            this.rewardTypeString = "PURPLE ENHANCE STONE";
                        }
                    }
                }
                if (this.returnCode == 22) {
                    int rewardType =  (int) (Math.random() * 2); //0 : coin, 1: 강화석
                    if (rewardType == 0){
                        int randomCoin = (int) (Math.random() * 11) * 5;
                        getRandomCoin = Integer.toString(randomCoin);
                        this.coin.addCoin(randomCoin);
                        this.gameState.setCoin(this.coin);
                        this.randomRes = randomCoin;
                        this.rewardTypeString = "C O I N ";
                    }
                    else{
                        int randomEnhanceType = (int) (Math.random() * 2); // 0: bst, 1: pst
                        if (randomEnhanceType == 0){
                            int randomEnhance = (int) (Math.random() * 4);
                            this.enhanceManager.PlusNumEnhanceStoneArea(randomEnhance);
                            this.randomRes = randomEnhance;
                            this.rewardTypeString = "BLUE ENHANCE STONE";
                        }
                        else{
                            int randomEnhance = (int) (Math.random() * 4);
                            this.enhanceManager.PlusNumEnhanceStoneAttack(randomEnhance);
                            this.randomRes = randomEnhance;
                            this.rewardTypeString = "PURPLE ENHANCE STONE";
                        }
                    }
                }
                this.isRunning = false;
            }
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextMenuItem() {
        if (this.returnCode == 20)
            this.returnCode = 21;
        else if (this.returnCode == 21)
            this.returnCode = 22;
        else
            this.returnCode = 20;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (this.returnCode == 22) 
            this.returnCode = 21; 
        else if (this.returnCode == 21) 
            this.returnCode = 20; 
        else
            this.returnCode = 22; 
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        // drawManager.drawTitle(this);
        drawManager.drawRandomBox(this, this.returnCode);
        drawManager.completeDrawing(this);
    }

    public int getRandomRes(){
        return this.randomRes;
    }

    public String getRewardTypeString(){
        return this.rewardTypeString;
    }

}
