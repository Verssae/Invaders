package engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Coin;
import screen.*;

/**
 * Implements core game logic.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public final class Core {
    private static BGM outgame_bgm;
    /**
     * Width of current screen.
     */
    private static final int WIDTH = 448;
    /**
     * Height of current screen.
     */
    private static final int HEIGHT = 520;
    /**
     * Max fps of current screen.
     */
    private static final int FPS = 60;
    /**
     * Max lives.
     */
    private static final int MAX_LIVES = 3;
    /**
     * Levels between extra life.
     */
    private static final int EXTRA_LIFE_FRECUENCY = 3;
    /**
     * Total number of levels.
     */
    private static final int NUM_LEVELS = 8;
    /**
     * difficulty of the game
     */
    private static int difficulty = 1;

    public static boolean checkInfinity = false ;

    /**
     * Difficulty settings for level 1.
     */
    private static GameSettings SETTINGS_LEVEL_1 = new GameSettings(5, 4, 60, 2000, 1, 1, 1);
    /**
     * Difficulty settings for level 2.
     */
    private static GameSettings SETTINGS_LEVEL_2 = new GameSettings(5, 5, 50, 2500, 1, 1, 1);
    /**
     * Difficulty settings for level 3.
     */
    private static GameSettings SETTINGS_LEVEL_3 = new GameSettings(6, 5, 40, 1500, 1, 1, 1);
    /**
     * Difficulty settings for level 4.
     */
    private static GameSettings SETTINGS_LEVEL_4 = new GameSettings(6, 6, 30, 1500, 1, 1, 1);
    /**
     * Difficulty settings for level 5.
     */
    private static GameSettings SETTINGS_LEVEL_5 = new GameSettings(7, 6, 20, 3900, 1, 1, 1);
    /**
     * Difficulty settings for level 6.
     */
    private static GameSettings SETTINGS_LEVEL_6 = new GameSettings(7, 7, 10, 3600, 1, 1, 1);
    /**
     * Difficulty settings for level 7.
     */

    private static GameSettings SETTINGS_LEVEL_7 = new GameSettings(8, 7, 2, 3300, 1, 1, 1);

    /**
     * Difficulty settings for level 8(Boss).
     */
    private static GameSettings SETTINGS_LEVEL_8 =
            new GameSettings(10, 1000,1, 1, 1);


    /**
     * Frame to draw the screen on.
     */
    private static Frame frame;
    /**
     * Screen currently shown.
     */
    private static Screen currentScreen;
    /**
     * Difficulty settings list.
     */
    private static List<GameSettings> gameSettings;
    /**
     * Application logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Core.class
            .getSimpleName());
    /**
     * Logger handler for printing to disk.
     */
    private static Handler fileHandler;
    /**
     * Logger handler for printing to console.
     */
    private static ConsoleHandler consoleHandler;

    private static Boolean boxOpen = false;
    private static Boolean isInitMenuScreen = true;

    private static int BulletsRemaining;

    private static int BulletsRemaining_1p;
    private static int BulletsRemaining_2p;



    /**
     * Test implementation.
     *
     * @param args Program args, ignored.
     */
    public static void main(final String[] args) {
        try {

            outgame_bgm = new BGM();

            LOGGER.setUseParentHandlers(false);

            fileHandler = new FileHandler("log");
            fileHandler.setFormatter(new MinimalFormatter());

            consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new MinimalFormatter());

            LOGGER.addHandler(fileHandler);
            LOGGER.addHandler(consoleHandler);
            LOGGER.setLevel(Level.ALL);

        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }

        frame = new Frame(WIDTH, HEIGHT);
        DrawManager.getInstance().setFrame(frame);
        int width = frame.getWidth();
        int height = frame.getHeight();
        int stage;

        GameState gameState;
        GameState_2P gameState_2P;
        EnhanceManager enhanceManager;
        ItemManager itemManager;
        Map<Color, Boolean> equippedSkins = new HashMap<>();
        Map<Color, Boolean> ownedSkins = new HashMap<>();


        int returnCode = 1;
        do {
            Coin coin = new Coin(0, 0);

            gameState = new GameState(1, 0, coin, MAX_LIVES, 0, 0, false, Color.WHITE, "B U Y", ownedSkins, equippedSkins, 99);
            gameState_2P = new GameState_2P(1, 0, 0,coin, MAX_LIVES, MAX_LIVES,0, 0, 0, false, 50,50);

            enhanceManager = new EnhanceManager(0, 0, 0, 0, 1);
            itemManager = new ItemManager(0, 0);

            switch (returnCode) {
                case 1:
                    // Main menu.
                    currentScreen = new TitleScreen(width, height, FPS);

                    outgame_bgm.OutGame_bgm_play(); //대기화면 비지엠 (수정중)

                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " title screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing title screen.");
                    break;

                case 2:
                    currentScreen = new SelectScreen(width, height, FPS, 0); // Difficulty Selection
                    LOGGER.info("Select Difficulty");
                    difficulty = frame.setScreen(currentScreen);
                    if (difficulty == 4) {
                        returnCode = 1;
                        LOGGER.info("Go Main");
                        break;
                    } else {
                        gameSettings = new ArrayList<GameSettings>();
                        if (difficulty == 3)
                            gameState.setHardCore();
                        LOGGER.info("Difficulty : " + difficulty);
                        SETTINGS_LEVEL_1.setDifficulty(difficulty);
                        SETTINGS_LEVEL_2.setDifficulty(difficulty);
                        SETTINGS_LEVEL_3.setDifficulty(difficulty);
                        SETTINGS_LEVEL_4.setDifficulty(difficulty);
                        SETTINGS_LEVEL_5.setDifficulty(difficulty);
                        SETTINGS_LEVEL_6.setDifficulty(difficulty);
                        SETTINGS_LEVEL_7.setDifficulty(difficulty);
                        SETTINGS_LEVEL_8.setDifficulty(difficulty);
                        gameSettings.add(SETTINGS_LEVEL_1);
                        gameSettings.add(SETTINGS_LEVEL_2);
                        gameSettings.add(SETTINGS_LEVEL_3);
                        gameSettings.add(SETTINGS_LEVEL_4);
                        gameSettings.add(SETTINGS_LEVEL_5);
                        gameSettings.add(SETTINGS_LEVEL_6);
                        gameSettings.add(SETTINGS_LEVEL_7);
                        gameSettings.add(SETTINGS_LEVEL_8);
                        gameSettings.add(SETTINGS_LEVEL_1);

                    }

                    LOGGER.info("select Level"); // Stage(Level) Selection
                    currentScreen = new StageSelectScreen(width, height, FPS, gameSettings.toArray().length, 1);
                    stage = frame.setScreen(currentScreen);
                    if (stage == 0) {
                        returnCode = 2;
                        LOGGER.info("Go Difficulty Select");
                        break;
                    }
                    if (stage == 9)
                        checkInfinity = true;
                    else
                        checkInfinity = false;

                    LOGGER.info("Closing Level screen.");
                    gameState.setLevel(stage);

                    outgame_bgm.OutGame_bgm_stop(); //게임 대기 -> 시작으로 넘어가면서 outgame bgm 종료

                    // Game & score.
                    do {
                        if (gameState.getLevel() == NUM_LEVELS + 1) {
                            gameState.setLevel(1);
                            currentScreen = new GameScreen(gameState,
                                    gameSettings.get(gameState.getLevel() - 1),
                                    enhanceManager, itemManager,
                                    width, height, FPS);
                        }

                        else {
                            currentScreen = new GameScreen(gameState,
                                    gameSettings.get(gameState.getLevel() - 1),
                                    enhanceManager, itemManager,
                                    width, height, FPS);
                        }

                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " game screen at " + FPS + " fps.");
                        returnCode = frame.setScreen(currentScreen);
                        LOGGER.info("Closing game screen.");

                        gameState = ((GameScreen) currentScreen).getGameState();
                        BulletsRemaining = gameState.getBulletsRemaining();

                        if (gameState.getLevel() == NUM_LEVELS && checkInfinity) {
                            gameState = new GameState(1,
                                    gameState.getScore(),
                                    gameState.getCoin(),
                                    gameState.getLivesRemaining(),
                                    gameState.getBulletsShot(),
                                    gameState.getShipsDestroyed(),
                                    gameState.getHardCore(),
                                    gameState.getShipColor(),
                                    gameState.getNowSkinString(),
                                    gameState.getOwnedSkins(),
                                    gameState.getEquippedSkins(),
                                    99);
                        }
                        else {
                            gameState = new GameState(gameState.getLevel() + 1,
                                    gameState.getScore(),
                                    gameState.getCoin(),
                                    gameState.getLivesRemaining(),
                                    gameState.getBulletsShot(),
                                    gameState.getShipsDestroyed(),
                                    gameState.getHardCore(),
                                    gameState.getShipColor(),
                                    gameState.getNowSkinString(),
                                    gameState.getOwnedSkins(),
                                    gameState.getEquippedSkins(),
                                    99);
                        }

						// SubMenu | Item Store & Enhancement & Continue & Skin Store
						do{
							if (gameState.getLivesRemaining() <= 0) { break; }
                            if (BulletsRemaining <= 0) { break; }
							if (!boxOpen){
								currentScreen = new RandomBoxScreen(gameState, width, height, FPS, enhanceManager);
								returnCode = frame.setScreen(currentScreen);
								boxOpen = true;
                                String getRewardTypeString = ((RandomBoxScreen) currentScreen).getRewardTypeString();
								currentScreen = new RandomRewardScreen(gameState, width, height, FPS, ((RandomBoxScreen) currentScreen).getRandomRes(), getRewardTypeString);
								returnCode = frame.setScreen(currentScreen);
							}
							if (isInitMenuScreen || currentScreen.returnCode == 5) {
								currentScreen = new SubMenuScreen(width, height, FPS);
								LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
										+ " subMenu screen at " + FPS + " fps.");
								returnCode = frame.setScreen(currentScreen);
								LOGGER.info("Closing subMenu screen.");
								isInitMenuScreen = false;
							}
							if (currentScreen.returnCode == 6 || currentScreen.returnCode == 35 || currentScreen.returnCode == 36 || currentScreen.returnCode == 37 || currentScreen.returnCode == 38) {
								currentScreen = new StoreScreen(width, height, FPS, gameState, enhanceManager, itemManager);
                                enhanceManager = ((StoreScreen) currentScreen).getEnhanceManager();
                                gameState = ((StoreScreen)currentScreen).getGameState();
								LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
										+ " store screen at " + FPS + " fps.");
								returnCode = frame.setScreen(currentScreen);
								LOGGER.info("Closing subMenu screen.");
							}
							if (currentScreen.returnCode == 7 || currentScreen.returnCode == 8 || currentScreen.returnCode == 9 || currentScreen.returnCode == 14) {
								currentScreen = new EnhanceScreen(enhanceManager, gameSettings, gameState, width, height, FPS);
								gameSettings = ((EnhanceScreen) currentScreen).getGameSettings();
								enhanceManager = ((EnhanceScreen) currentScreen).getEnhanceManager();
								LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
										+ " enhance screen at " + FPS + " fps.");
								returnCode = frame.setScreen(currentScreen);
								LOGGER.info("Closing subMenu screen.");
							}
							if (currentScreen.returnCode == 86 || currentScreen.returnCode == 15 || currentScreen.returnCode == 87 || currentScreen.returnCode == 88 || currentScreen.returnCode == 89) {
								currentScreen = new SkinStoreScreen(width, height, FPS, gameState, enhanceManager);
                                gameState = ((SkinStoreScreen) currentScreen).getGameState();
								LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
										+ "skin store screen at " + FPS + " fps.");
								returnCode = frame.setScreen(currentScreen);
								LOGGER.info("Closing subMenu screen.");
							}
						} while (currentScreen.returnCode != 2);
						boxOpen = false;
						isInitMenuScreen = true;
					} while (gameState.getLivesRemaining() > 0
							&& gameState.getLevel() <= NUM_LEVELS && BulletsRemaining > 0);


                    // Recovery | Default State & Exit

                    currentScreen = new RecoveryScreen(width, height, FPS);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " Recovery screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing Recovery screen.");


					if (returnCode == 30) { 
                        currentScreen = new RecoveryPaymentScreen(gameState, width, height, FPS);
                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " Recovery screen at " + FPS + " fps.");
                        returnCode = frame.setScreen(currentScreen);
                        LOGGER.info("Closing RecoveryPayment screen.");
                        
                        // Checking for Recovery Feasibility and Deducting Recovery Coins.
                        if (returnCode == 51){

                            int coinnum = gameState.getCoin().getCoin();
                            
                            if (coinnum >= 30 ){
                                Coin recoveryCoin = new Coin(0, 0);
                                recoveryCoin.addCoin(coinnum);
                                recoveryCoin.minusCoin(30);
                                gameState.setCoin(recoveryCoin);
                                
                                // Continuing game in same state (Ship: default state)
						        gameState.setLivesRecovery();
						        do { 
                                    currentScreen = new GameScreen(gameState,
								    gameSettings.get(gameState.getLevel()-1),
                                    enhanceManager, itemManager,
                                    width, height, FPS);

                             
                                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                    + " game screen at " + FPS + " fps.");
                                    returnCode = frame.setScreen(currentScreen);
                                    LOGGER.info("Closing game screen.");
                                    gameState = ((GameScreen) currentScreen).getGameState();
                                    BulletsRemaining = gameState.getBulletsRemaining();

                                    gameState = new GameState(gameState.getLevel()+1,
                                        gameState.getScore(),
                                        gameState.getCoin(),
                                        gameState.getLivesRemaining(),
                                        gameState.getBulletsShot(),
                                        gameState.getShipsDestroyed(),
                                        gameState.getHardCore(), 
                                        gameState.getShipColor(), 
                                        gameState.getNowSkinString(),
                                        gameState.getOwnedSkins(), 
                                        gameState.getEquippedSkins(), 
                                        99);

                                    // SubMenu | Item Store & Enhancement & Continue & Skin Store
                                    do{
                                        if (gameState.getLivesRemaining() <= 0) { break; }
                                        if (BulletsRemaining <= 0) {break;}
                                        if (!boxOpen){
                                            currentScreen = new RandomBoxScreen(gameState, width, height, FPS, enhanceManager);
								            returnCode = frame.setScreen(currentScreen);
								            boxOpen = true;
                                            String getRewardTypeString = ((RandomBoxScreen) currentScreen).getRewardTypeString();
								            currentScreen = new RandomRewardScreen(gameState, width, height, FPS, ((RandomBoxScreen) currentScreen).getRandomRes(), getRewardTypeString);
								            returnCode = frame.setScreen(currentScreen);
                                        }
                                        if (isInitMenuScreen || currentScreen.returnCode == 5) {
                                            currentScreen = new SubMenuScreen(width, height, FPS);
                                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                    + " subMenu screen at " + FPS + " fps.");
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                            isInitMenuScreen = false;
                                        }
                                        if (currentScreen.returnCode == 6 || currentScreen.returnCode == 35 || currentScreen.returnCode == 36 || currentScreen.returnCode == 37 || currentScreen.returnCode == 38) {
                                            currentScreen = new StoreScreen(width, height, FPS, gameState, enhanceManager, itemManager);
                                            enhanceManager = ((StoreScreen) currentScreen).getEnhanceManager();
                                            gameState = ((StoreScreen)currentScreen).getGameState();
                                            
                                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                + " store screen at " + FPS + " fps.");
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                        }
                                        if (currentScreen.returnCode == 7 || currentScreen.returnCode == 8 || currentScreen.returnCode == 9 || currentScreen.returnCode == 14) {
                                            currentScreen = new EnhanceScreen(enhanceManager, gameSettings, gameState, width, height, FPS);
                                            gameSettings = ((EnhanceScreen) currentScreen).getGameSettings();
                                            enhanceManager = ((EnhanceScreen) currentScreen).getEnhanceManager();
                                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                + " enhance screen at " + FPS + " fps.");
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                        }
                                        if (currentScreen.returnCode == 86 || currentScreen.returnCode == 15) {
                                            currentScreen = new SkinStoreScreen(width, height, FPS, gameState, enhanceManager);
                                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                     + "skin store screen at " + FPS + " fps.");
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                        }
                                    } while (currentScreen.returnCode != 2); {
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                        
                                        } while (currentScreen.returnCode != 2);
                                        boxOpen = false;
                                        isInitMenuScreen = true;
                                } while (gameState.getLivesRemaining() > 0
                                            && gameState.getLevel() <= NUM_LEVELS && BulletsRemaining > 0);

                                if (returnCode == 1) { // Quit during the game
                                    currentScreen = new TitleScreen(width, height, FPS);
                                    frame.setScreen(currentScreen);
                                    break;
                                }
                            } else { 
                                // If there is an insufficient number of coins required for recovery 
                                returnCode = 1; }
					    }
                    }

                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " score screen at " + FPS + " fps, with a score of "
                            + gameState.getScore() + ", "
                            + gameState.getLivesRemaining() + " lives remaining, "
                            + gameState.getBulletsShot() + " ship bullets shot and "
                            + gameState.getShipsDestroyed() + " ships destroyed.");
                    currentScreen = new ScoreScreen(width, height, FPS, gameState, difficulty);
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing score screen.");
                    break;
                case 3:

                    // High scores.
                    currentScreen = new ScoreMenuScreen(width, height, FPS);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT + " high score menu screen at " + FPS + " fps.");
                    int scorescreen = frame.setScreen(currentScreen);
                    if(scorescreen == 31)
                    {
                        currentScreen = new HighScoreScreen(width, height, FPS);
                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " high score screen at " + FPS + " fps.");
                        returnCode = frame.setScreen(currentScreen);
                        LOGGER.info("Closing high score screen.");
                        break;
                    }
                    else if(scorescreen == 32)
                    {
                        currentScreen = new TwoPlayHighScoreScreen(width, height, FPS);
                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " Two Play high score screen at " + FPS + " fps.");
                        returnCode = frame.setScreen(currentScreen);
                        LOGGER.info("Closing Two Play high score screen.");
                        break;
                    }
                    else
                        returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing high score menu screen.");
                    break;

                    /**
                    currentScreen = new HighScoreScreen(width, height, FPS);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " high score screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing high score screen.");
                    break;
                    **/

                case 4:
                    currentScreen = new SelectScreen(width, height, FPS, 0); // Difficulty Selection
                    LOGGER.info("Select Difficulty");
                    difficulty = frame.setScreen(currentScreen);
                    if (difficulty == 4) {
                        returnCode = 1;
                        LOGGER.info("Go Main");
                        break;
                    } else {
                        gameSettings = new ArrayList<GameSettings>();
                        if (difficulty == 3)
                            gameState_2P.setHardCore();
                        LOGGER.info("Difficulty : " + difficulty);
                        SETTINGS_LEVEL_1.setDifficulty(difficulty);
                        SETTINGS_LEVEL_2.setDifficulty(difficulty);
                        SETTINGS_LEVEL_3.setDifficulty(difficulty);
                        SETTINGS_LEVEL_4.setDifficulty(difficulty);
                        SETTINGS_LEVEL_5.setDifficulty(difficulty);
                        SETTINGS_LEVEL_6.setDifficulty(difficulty);
                        SETTINGS_LEVEL_7.setDifficulty(difficulty);
                        SETTINGS_LEVEL_8.setDifficulty(difficulty);
                        gameSettings.add(SETTINGS_LEVEL_1);
                        gameSettings.add(SETTINGS_LEVEL_2);
                        gameSettings.add(SETTINGS_LEVEL_3);
                        gameSettings.add(SETTINGS_LEVEL_4);
                        gameSettings.add(SETTINGS_LEVEL_5);
                        gameSettings.add(SETTINGS_LEVEL_6);
                        gameSettings.add(SETTINGS_LEVEL_7);
                        gameSettings.add(SETTINGS_LEVEL_8);
                    }

                    LOGGER.info("select Level"); // Stage(Level) Selection
                    currentScreen = new StageSelectScreen(width, height, FPS, gameSettings.toArray().length, 1);
                    stage = frame.setScreen(currentScreen);

                    if (stage == 0) {
                        returnCode = 4;
                        LOGGER.info("Go Difficulty Select");
                        break;
                    }
                    LOGGER.info("Closing Level screen.");
                    gameState_2P.setLevel(stage);

                    outgame_bgm.OutGame_bgm_stop(); //게임 대기 -> 시작으로 넘어가면서 outgame bgm 종료

                    // Game & score.
                    do {
                        currentScreen = new GameScreen_2P(gameState_2P,
                                gameSettings.get(gameState_2P.getLevel() - 1),
                                enhanceManager, itemManager,
                                width, height, FPS);
                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " game screen at " + FPS + " fps.");
                        returnCode = frame.setScreen(currentScreen);
                        LOGGER.info("Closing game screen.");

                        gameState_2P = ((GameScreen_2P) currentScreen).getGameState();
                        BulletsRemaining_1p = gameState_2P.getBulletsRemaining_1p();
                        BulletsRemaining_2p = gameState_2P.getBulletsRemaining_2p();

                        gameState_2P = new GameState_2P(gameState_2P.getLevel() + 1,
                                gameState_2P.getScore_1P(),
                                gameState_2P.getScore_2P(),
                                gameState_2P.getCoin(),
                                gameState_2P.getLivesRemaining_1P(),
                                gameState_2P.getLivesRemaining_2P(),
                                gameState_2P.getBulletsShot_1P(),
                                gameState_2P.getBulletsShot_2P(),
                                gameState_2P.getShipsDestroyed(),
                                gameState_2P.getHardCore(),
                                50, 50);

                        // SubMenu | Item Store & Enhancement & Continue & Skin Store
                        do{
                            if (gameState_2P.getLivesRemaining_1P() <= 0 && gameState_2P.getLivesRemaining_2P() <= 0) { break; }
                            if (BulletsRemaining_1p <= 0 && BulletsRemaining_2p <= 0) { break; }
                            if (!boxOpen){
                                currentScreen = new RandomBoxScreen_2P(gameState_2P, width, height, FPS, enhanceManager);
                                returnCode = frame.setScreen(currentScreen);
                                boxOpen = true;
                                String getRewardTypeString = ((RandomBoxScreen_2P) currentScreen).getRewardTypeString();
                                currentScreen = new RandomRewardScreen_2P(gameState_2P, width, height, FPS, ((RandomBoxScreen_2P) currentScreen).getRandomRes(), getRewardTypeString);
                                returnCode = frame.setScreen(currentScreen);
                            }
                            if (isInitMenuScreen || currentScreen.returnCode == 5) {
                                currentScreen = new SubMenuScreen_2P(width, height, FPS);
                                LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                        + " subMenu screen at " + FPS + " fps.");
                                returnCode = frame.setScreen(currentScreen);
                                LOGGER.info("Closing subMenu screen.");
                                isInitMenuScreen = false;
                            }
                            if (currentScreen.returnCode == 6 || currentScreen.returnCode == 35 || currentScreen.returnCode == 36 || currentScreen.returnCode == 37 || currentScreen.returnCode == 38) {
                                currentScreen = new StoreScreen_2P(width, height, FPS, gameState_2P, enhanceManager, itemManager);
                                enhanceManager = ((StoreScreen_2P) currentScreen).getEnhanceManager();
                                gameState_2P = ((StoreScreen_2P)currentScreen).getGameState();
                                LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                        + " store screen at " + FPS + " fps.");
                                returnCode = frame.setScreen(currentScreen);
                                LOGGER.info("Closing subMenu screen.");
                            }
                            if (currentScreen.returnCode == 7 || currentScreen.returnCode == 8 || currentScreen.returnCode == 9 || currentScreen.returnCode == 14) {
                                currentScreen = new EnhanceScreen_2P(enhanceManager, gameSettings, gameState_2P, width, height, FPS);
                                gameSettings = ((EnhanceScreen_2P) currentScreen).getGameSettings();
                                enhanceManager = ((EnhanceScreen_2P) currentScreen).getEnhanceManager();
                                LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                        + " enhance screen at " + FPS + " fps.");
                                returnCode = frame.setScreen(currentScreen);
                                LOGGER.info("Closing subMenu screen.");
                            }
                        } while (currentScreen.returnCode != 2);
                        boxOpen = false;
                        isInitMenuScreen = true;
                    } while (gameState_2P.getLevel() <= NUM_LEVELS
                            && ((gameState_2P.getLivesRemaining_1P() > 0 && BulletsRemaining_1p > 0)
                            || (gameState_2P.getLivesRemaining_2P() > 0 && BulletsRemaining_2p > 0)));


                    // Recovery | Default State & Exit

                    currentScreen = new RecoveryScreen(width, height, FPS);
                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " Recovery screen at " + FPS + " fps.");
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing Recovery screen.");


                    if (returnCode == 30) {
                        currentScreen = new RecoveryPaymentScreen_2P(gameState_2P, width, height, FPS);
                        LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                + " Recovery screen at " + FPS + " fps.");
                        returnCode = frame.setScreen(currentScreen);
                        LOGGER.info("Closing RecoveryPayment screen.");

                        // Checking for Recovery Feasibility and Deducting Recovery Coins.
                        if (returnCode == 51){

                            int coinnum = gameState_2P.getCoin().getCoin();

                            if (coinnum >= 30 ){
                                Coin recoveryCoin = new Coin(0, 0);
                                recoveryCoin.addCoin(coinnum);
                                recoveryCoin.minusCoin(30);
                                gameState_2P.setCoin(recoveryCoin);

                                // Continuing game in same state (Ship: default state)
                                gameState_2P.setLivesRecovery();
                                do {
                                    currentScreen = new GameScreen_2P(gameState_2P,
                                            gameSettings.get(gameState_2P.getLevel() - 1),
                                            enhanceManager, itemManager,
                                            width, height, FPS);
                                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                            + " game screen at " + FPS + " fps.");
                                    returnCode = frame.setScreen(currentScreen);
                                    LOGGER.info("Closing game screen.");

                                    gameState_2P = ((GameScreen_2P) currentScreen).getGameState();
                                    BulletsRemaining_1p = gameState_2P.getBulletsRemaining_1p();
                                    BulletsRemaining_2p = gameState_2P.getBulletsRemaining_2p();

                                    gameState_2P = new GameState_2P(gameState_2P.getLevel() + 1,
                                            gameState_2P.getScore_1P(),
                                            gameState_2P.getScore_2P(),
                                            gameState_2P.getCoin(),
                                            gameState_2P.getLivesRemaining_1P(),
                                            gameState_2P.getLivesRemaining_2P(),
                                            gameState_2P.getBulletsShot_1P(),
                                            gameState_2P.getBulletsShot_2P(),
                                            gameState_2P.getShipsDestroyed(),
                                            gameState_2P.getHardCore(),
                                            50, 50);

                                    // SubMenu | Item Store & Enhancement & Continue & Skin Store
                                    do{
                                        if (gameState_2P.getLivesRemaining_1P() <= 0 && gameState_2P.getLivesRemaining_2P() <= 0) { break; }
                                        if (BulletsRemaining_1p <= 0 && BulletsRemaining_2p <= 0) { break; }
                                        if (!boxOpen){
                                            currentScreen = new RandomBoxScreen_2P(gameState_2P, width, height, FPS, enhanceManager);
                                            returnCode = frame.setScreen(currentScreen);
                                            boxOpen = true;
                                            String getRewardTypeString = ((RandomBoxScreen) currentScreen).getRewardTypeString();
                                            currentScreen = new RandomRewardScreen_2P(gameState_2P, width, height, FPS, ((RandomBoxScreen) currentScreen).getRandomRes(), getRewardTypeString);
                                            returnCode = frame.setScreen(currentScreen);
                                        }
                                        if (isInitMenuScreen || currentScreen.returnCode == 5) {
                                            currentScreen = new SubMenuScreen_2P(width, height, FPS);
                                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                    + " subMenu screen at " + FPS + " fps.");
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                            isInitMenuScreen = false;
                                        }
                                        if (currentScreen.returnCode == 6 || currentScreen.returnCode == 35 || currentScreen.returnCode == 36 || currentScreen.returnCode == 37 || currentScreen.returnCode == 38) {
                                            currentScreen = new StoreScreen_2P(width, height, FPS, gameState_2P, enhanceManager, itemManager);
                                            enhanceManager = ((StoreScreen_2P) currentScreen).getEnhanceManager();
                                            gameState_2P = ((StoreScreen_2P)currentScreen).getGameState();
                                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                    + " store screen at " + FPS + " fps.");
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                        }
                                        if (currentScreen.returnCode == 7 || currentScreen.returnCode == 8 || currentScreen.returnCode == 9 || currentScreen.returnCode == 14) {
                                            currentScreen = new EnhanceScreen_2P(enhanceManager, gameSettings, gameState_2P, width, height, FPS);
                                            gameSettings = ((EnhanceScreen_2P) currentScreen).getGameSettings();
                                            enhanceManager = ((EnhanceScreen_2P) currentScreen).getEnhanceManager();
                                            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                                                    + " enhance screen at " + FPS + " fps.");
                                            returnCode = frame.setScreen(currentScreen);
                                            LOGGER.info("Closing subMenu screen.");
                                        }
                                    } while (currentScreen.returnCode != 2);
                                    boxOpen = false;
                                    isInitMenuScreen = true;
                                } while (gameState_2P.getLevel() <= NUM_LEVELS
                                        && ((gameState_2P.getLivesRemaining_1P() > 0 && BulletsRemaining_1p > 0)
                                        || (gameState_2P.getLivesRemaining_2P() > 0 && BulletsRemaining_2p > 0)));

                                if (returnCode == 1) { // Quit during the game
                                    currentScreen = new TitleScreen(width, height, FPS);
                                    frame.setScreen(currentScreen);
                                    break;
                                }
                            } else {
                                // If there is an insufficient number of coins required for recovery
                                returnCode = 1; }
                        }
                    }

                    LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
                            + " score screen at " + FPS + " fps, with a score of "
                            + gameState_2P.getScore_1P() + ", "
                            + gameState_2P.getScore_2P() + ", "
                            + gameState_2P.getLivesRemaining_1P() + " Ship_1P lives remaining, "
                            + gameState_2P.getLivesRemaining_2P() + " Ship_2P lives remaining, "
                            + gameState_2P.getBulletsShot_1P() + " Ship_1P bullets shot and "
                            + gameState_2P.getBulletsShot_2P() + " Ship_2P bullets shot and "
                            + gameState_2P.getShipsDestroyed() + " ships destroyed.");
                    currentScreen = new TwoPlayScoreScreen(width, height, FPS, gameState_2P, difficulty);
                    returnCode = frame.setScreen(currentScreen);
                    LOGGER.info("Closing score screen.");
                    break;
                default:
                    break;
            }

        } while (returnCode != 0);

        fileHandler.flush();
        fileHandler.close();
        System.exit(0);
    }

    /**
     * Constructor, not called.
     */
    private Core() {

    }

    /**
     * Controls access to the logger.
     *
     * @return Application logger.
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Controls access to the drawing manager.
     *
     * @return Application draw manager.
     */
    public static DrawManager getDrawManager() {
        return DrawManager.getInstance();
    }

    /**
     * Controls access to the input manager.
     *
     * @return Application input manager.
     */
    public static InputManager getInputManager() {
        return InputManager.getInstance();
    }

    /**
     * Controls access to the file manager.
     *
     * @return Application file manager.
     */
    public static FileManager getFileManager() {
        return FileManager.getInstance();
    }
    /**
     * Controls creation of new cooldowns.
     *
     * @param milliseconds Duration of the cooldown.
     * @return A new cooldown.
     */
    public static Cooldown getCooldown(final int milliseconds) {
        return new Cooldown(milliseconds);
    }

    /**
     * Controls creation of new cooldowns with variance.
     *
     * @param milliseconds Duration of the cooldown.
     * @param variance     Variation in the cooldown duration.
     * @return A new cooldown with variance.
     */
    public static Cooldown getVariableCooldown(final int milliseconds,
                                               final int variance) {
        return new Cooldown(milliseconds, variance);
    }
}