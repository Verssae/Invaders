package engine;

import screen.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * Implements core game logic.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public final class Core {

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

	/**
	 * Difficulty settings for level 1.
	 */
	private static GameSettings SETTINGS_LEVEL_1 =
			new GameSettings(5, 4, 60, 2000, 1);
	/**
	 * Difficulty settings for level 2.
	 */
	private static GameSettings SETTINGS_LEVEL_2 =
			new GameSettings(5, 5, 50, 2500,1);
	/**
	 * Difficulty settings for level 3.
	 */
	private static GameSettings SETTINGS_LEVEL_3 =
			new GameSettings(6, 5, 40, 1500,1);
	/**
	 * Difficulty settings for level 4.
	 */
	private static GameSettings SETTINGS_LEVEL_4 =
			new GameSettings(6, 6, 30, 1500,1);
	/**
	 * Difficulty settings for level 5.
	 */
	private static GameSettings SETTINGS_LEVEL_5 =
			new GameSettings(7, 6, 20, 1000,1);
	/**
	 * Difficulty settings for level 6.
	 */
	private static GameSettings SETTINGS_LEVEL_6 =
			new GameSettings(7, 7, 10, 1000,1);
	/**
	 * Difficulty settings for level 7.
	 */
	private static GameSettings SETTINGS_LEVEL_7 =
			new GameSettings(8, 7, 2, 500,1);
	/**
	 * Difficulty settings for level 8(Boss).
	 */
	private static GameSettings SETTINGS_LEVEL_8 =
			new GameSettings(10, 1000,1);

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

	/**
	 * Test implementation.
	 *
	 * @param args Program args, ignored.
	 */
	public static void main(final String[] args) {
		try {
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

		GameState gameState;

		int returnCode = 1;
		do {
			gameState = new GameState(1, 0, MAX_LIVES, 0, 0, false);

			switch (returnCode) {
				case 1:
					// Main menu.
					currentScreen = new TitleScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " title screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing title screen.");
					break;
				case 2:
					currentScreen = new SelectScreen(width, height, FPS, 0);
					LOGGER.info("Select Difficulty");
					difficulty = frame.setScreen(currentScreen);
					if (difficulty == 4) {
						returnCode = 1;
						LOGGER.info("Go Main");
						break;
					}
					else {
						gameSettings = new ArrayList<GameSettings>();
						if (difficulty == 3) gameState.setHardCore();
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

					LOGGER.info("select Level");
					currentScreen = new StageSelectScreen(width, height, FPS, gameSettings.toArray().length, 1);
					int stage = frame.setScreen(currentScreen);
					if (stage == 0) {
						returnCode = 2;
						LOGGER.info("Go Difficulty Select");
						break;
					}
					LOGGER.info("Closing Level screen.");
					gameState.setLevel(stage);

					// Game & score.
					do {
						// One extra live every few levels.
						boolean bonusLife = gameState.getLevel()
								% EXTRA_LIFE_FRECUENCY == 0 && !gameState.getHardCore()
								&& gameState.getLivesRemaining() < MAX_LIVES;

						currentScreen = new GameScreen(gameState,
								gameSettings.get(gameState.getLevel() - 1),
								bonusLife, width, height, FPS);
						LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
								+ " game screen at " + FPS + " fps.");
						returnCode = frame.setScreen(currentScreen);
						LOGGER.info("Closing game screen.");

						gameState = ((GameScreen) currentScreen).getGameState();

						gameState = new GameState(gameState.getLevel() + 1,
								gameState.getScore(),
								gameState.getLivesRemaining(),
								gameState.getBulletsShot(),
								gameState.getShipsDestroyed(),
								gameState.getHardCore()
						);

					} while (gameState.getLivesRemaining() > 0
							&& gameState.getLevel() <= NUM_LEVELS);

					if (returnCode == 1) {
						currentScreen = new TitleScreen(width, height, FPS);
						returnCode = frame.setScreen(currentScreen);
						break;
					}

					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " score screen at " + FPS + " fps, with a score of "
							+ gameState.getScore() + ", "
							+ gameState.getLivesRemaining() + " lives remaining, "
							+ gameState.getBulletsShot() + " bullets shot and "
							+ gameState.getShipsDestroyed() + " ships destroyed.");
					currentScreen = new ScoreScreen(width, height, FPS, gameState, difficulty);
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing score screen.");
					break;
				case 3:
					// High scores.
					currentScreen = new HighScoreScreen(width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " high score screen at " + FPS + " fps.");
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing high score screen.");
					break;
				case 4:
					currentScreen = new SelectScreen(width, height, FPS, 0);
					LOGGER.info("Select Difficulty");
					difficulty = frame.setScreen(currentScreen);
					if (difficulty == 4) {
						returnCode = 1;
						LOGGER.info("Go Main");
						break;
					}
					else {
						gameSettings = new ArrayList<GameSettings>();
						if (difficulty == 3) gameState.setHardCore();
						LOGGER.info("Difficulty : " + difficulty);
						SETTINGS_LEVEL_1.setDifficulty(difficulty);
						SETTINGS_LEVEL_2.setDifficulty(difficulty);
						SETTINGS_LEVEL_3.setDifficulty(difficulty);
						SETTINGS_LEVEL_4.setDifficulty(difficulty);
						SETTINGS_LEVEL_5.setDifficulty(difficulty);
						SETTINGS_LEVEL_6.setDifficulty(difficulty);
						SETTINGS_LEVEL_7.setDifficulty(difficulty);
						gameSettings.add(SETTINGS_LEVEL_1);
						gameSettings.add(SETTINGS_LEVEL_2);
						gameSettings.add(SETTINGS_LEVEL_3);
						gameSettings.add(SETTINGS_LEVEL_4);
						gameSettings.add(SETTINGS_LEVEL_5);
						gameSettings.add(SETTINGS_LEVEL_6);
						gameSettings.add(SETTINGS_LEVEL_7);
					}
					// Game & score.
					do {
						// One extra live every few levels.
						boolean bonusLife = gameState.getLevel()
								% EXTRA_LIFE_FRECUENCY == 0 && !gameState.getHardCore()
								&& gameState.getLivesRemaining() < MAX_LIVES;

						currentScreen = new GameScreen_2P(gameState,
								gameSettings.get(gameState.getLevel() - 1),
								bonusLife, width, height, FPS);
						LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
								+ " game screen at " + FPS + " fps.");
						frame.setScreen(currentScreen);
						LOGGER.info("Closing game screen.");

						gameState = ((GameScreen_2P) currentScreen).getGameState();

						gameState = new GameState(gameState.getLevel() + 1,
								gameState.getScore(),
								gameState.getLivesRemaining(),
								gameState.getBulletsShot(),
								gameState.getShipsDestroyed(),
								gameState.getHardCore());

					} while (gameState.getLivesRemaining() > 0
							&& gameState.getLevel() <= NUM_LEVELS);

					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " score screen at " + FPS + " fps, with a score of "
							+ gameState.getScore() + ", "
							+ gameState.getLivesRemaining() + " lives remaining, "
							+ gameState.getBulletsShot() + " bullets shot and "
							+ gameState.getShipsDestroyed() + " ships destroyed.");
					currentScreen = new ScoreScreen(width, height, FPS, gameState, difficulty);
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