package engine;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage; // monster animation on a loading box
import java.io.File;
import java.io.IOException;
import java.time.LocalTime; // blinkingColor(String color)
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import screen.Screen;
import entity.Entity;
import entity.Ship;

import javax.imageio.ImageIO;

/**
 * Manages screen drawing.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public final class DrawManager {

	/** Singleton instance of the class. */
	private static DrawManager instance;
	/** Current frame. */
	private static Frame frame;
	/** FileManager instance. */
	private static FileManager fileManager;
	/** Application logger. */
	private static Logger logger;
	/** Graphics context. */
	private static Graphics graphics;
	/** Buffer Graphics. */
	private static Graphics backBufferGraphics;
	/** Buffer image. */
	private static BufferedImage backBuffer;
	/** Normal sized font. */
	private static Font fontRegular;
	/** Normal sized font properties. */
	private static FontMetrics fontRegularMetrics;
	/** Big sized font. */
	private static Font fontBig;
	/** Big sized font properties. */
	private static FontMetrics fontBigMetrics;

	/** Sprite types mapped to their images. */
	private static Map<SpriteType, boolean[][]> spriteMap;

	public int timercount = 0;

	BufferedImage img1, img2, img3, img4;

	boolean isFirst = true;

	/** Sprite types. */
	public static enum SpriteType {
		/** Player ship. */
		Ship,
		/** Destroyed player ship. */
		ShipDestroyed,
		/** Player bullet. */
		Bullet,
		/** Enemy bullet. */
		EnemyBullet,
		/** First enemy ship - first form. */
		EnemyShipA1,
		/** First enemy ship - second form. */
		EnemyShipA2,
		/** Second enemy ship - first form. */
		EnemyShipB1,
		/** Second enemy ship - second form. */
		EnemyShipB2,
		/** Third enemy ship - first form. */
		EnemyShipC1,
		/** Third enemy ship - second form. */
		EnemyShipC2,
		/** Bonus ship. */
		EnemyShipSpecial,
		/** Boss ship */
		Boss,
		/** Destroyed enemy ship. */
		Explosion,

		BulletLine;
	};

	/**
	 * Private constructor.
	 */
	private DrawManager() {
		fileManager = Core.getFileManager();
		logger = Core.getLogger();
		logger.info("Started loading resources.");

		try {
			spriteMap = new LinkedHashMap<SpriteType, boolean[][]>();

			spriteMap.put(SpriteType.Ship, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipDestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.Bullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSpecial, new boolean[16][7]);
			spriteMap.put(SpriteType.Explosion, new boolean[13][7]);
			spriteMap.put(SpriteType.BulletLine, new boolean[1][160]);

			fileManager.loadSprite(spriteMap);
			logger.info("Finished loading the sprites.");

			// Font loading.
			fontRegular = fileManager.loadFont(14f);
			fontBig = fileManager.loadFont(24f);
			logger.info("Finished loading the fonts.");

		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}
	}

	/**
	 * Returns shared instance of DrawManager.
	 *
	 * @return Shared instance of DrawManager.
	 */
	protected static DrawManager getInstance() {
		if (instance == null)
			instance = new DrawManager();
		return instance;
	}

	/**
	 * Sets the frame to draw the image on.
	 *
	 * @param currentFrame
	 *                     Frame to draw on.
	 */
	public void setFrame(final Frame currentFrame) {
		frame = currentFrame;
	}

	/**
	 * First part of the drawing process. Initialices buffers, draws the
	 * background and prepares the images.
	 *
	 * @param screen
	 *               Screen to draw in.
	 */
	public void initDrawing(final Screen screen) {
		backBuffer = new BufferedImage(screen.getWidth(), screen.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		graphics = frame.getGraphics();
		backBufferGraphics = backBuffer.getGraphics();

		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics
				.fillRect(0, 0, screen.getWidth(), screen.getHeight());

		fontRegularMetrics = backBufferGraphics.getFontMetrics(fontRegular);
		fontBigMetrics = backBufferGraphics.getFontMetrics(fontBig);

		// drawBorders(screen);
		// drawGrid(screen);
	}

	/**
	 * Draws the completed drawing on screen.
	 *
	 * @param screen
	 *               Screen to draw on.
	 */
	public void completeDrawing(final Screen screen) {
		graphics.drawImage(backBuffer, frame.getInsets().left,
				frame.getInsets().top, frame);
	}

	/**
	 * Draws an entity, using the apropiate image.
	 *
	 * @param entity
	 *                  Entity to be drawn.
	 * @param positionX
	 *                  Coordinates for the left side of the image.
	 * @param positionY
	 *                  Coordinates for the upper side of the image.
	 */
	public void drawEntity(final Entity entity, final int positionX,
			final int positionY) {
		boolean[][] image = spriteMap.get(entity.getSpriteType());

		backBufferGraphics.setColor(entity.getColor());
		for (int i = 0; i < image.length; i++)
			for (int j = 0; j < image[i].length; j++)
				if (image[i][j])
					backBufferGraphics.drawRect(positionX + i * 2, positionY
							+ j * 2, 1, 1);
	}

	/**
	 * Entity can be drawn more precise size.
	 *
	 * [Clean Code Team] This method was created by dodo_kdy.
	 *
	 *
	 * @param SpriteType
	 * @param positionX
	 * @param positionY
	 * @param width
	 * @param height
	 */
	public void drawEntity(final SpriteType SpriteType, final int positionX,
			final int positionY, final double width, final double height) {
		boolean[][] image = spriteMap.get(SpriteType);
		Graphics2D g2 = (Graphics2D) backBufferGraphics;
		g2.setColor(Color.white);

		for (int i = 0; i < image.length; i++)
			for (int j = 0; j < image[i].length; j++)
				if (image[i][j])
					g2.fill(new Rectangle2D.Double(positionX + i * width, positionY + j * height, width, height));
	}

	/**
	 * For debugging purpouses, draws the canvas borders.
	 *
	 * @param screen
	 *               Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawBorders(final Screen screen) {
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(0, 0, screen.getWidth() - 1, 0);
		backBufferGraphics.drawLine(0, 0, 0, screen.getHeight() - 1);
		backBufferGraphics.drawLine(screen.getWidth() - 1, 0,
				screen.getWidth() - 1, screen.getHeight() - 1);
		backBufferGraphics.drawLine(0, screen.getHeight() - 1,
				screen.getWidth() - 1, screen.getHeight() - 1);
	}

	/**
	 * For debugging purpouses, draws a grid over the canvas.
	 *
	 * @param screen
	 *               Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawGrid(final Screen screen) {
		backBufferGraphics.setColor(Color.DARK_GRAY);
		for (int i = 0; i < screen.getHeight() - 1; i += 2)
			backBufferGraphics.drawLine(0, i, screen.getWidth() - 1, i);
		for (int j = 0; j < screen.getWidth() - 1; j += 2)
			backBufferGraphics.drawLine(j, 0, j, screen.getHeight() - 1);
	}

	/**
	 * The color changes slightly depending on the score.
	 * [Clean Code Team] This method was created by highlees.
	 *
	 * @param score
	 */
	private Color scoreColor(final int score) {
		if (score < 800)
			return Color.WHITE;
		if (score >= 800 && score < 1600)
			return new Color(206, 255, 210);
		if (score >= 1600 && score < 2400)
			return new Color(151, 255, 158);
		if (score >= 2400 && score < 3200)
			return new Color(88, 255, 99);
		if (score >= 3200 && score < 4000)
			return new Color(50, 255, 64);
		if (score >= 4000 && score < 4800)
			return new Color(0, 255, 17);
		else
			return blinkingColor("HIGH_SCORES");
	}

	/**
	 * The emoji changes slightly depending on the score.
	 * [Clean Code Team] This method was created by highlees.
	 *
	 * @param screen
	 * @param score
	 *
	 */
	public void scoreEmoji(final Screen screen, final int score) {
		backBufferGraphics.setFont(fontRegular);
		if (score >= 800 && score < 1600) {
			backBufferGraphics.setColor(scoreColor(800));
			backBufferGraphics.drawString(" Z...z..    ( _ . _ )", screen.getWidth() - 250, 25);
		}
		if (score >= 1600 && score < 2400) {
			backBufferGraphics.setColor(scoreColor(1600));
			backBufferGraphics.drawString("  ??...?..    ( o . o )", screen.getWidth() - 240, 25);
		}
		if (score >= 2400 && score < 3200) {
			backBufferGraphics.setColor(scoreColor(2400));
			backBufferGraphics.drawString("         !!...!..  ) O . O )", screen.getWidth() - 240, 25);
		}
		if (score >= 3200 && score < 4000) {
			backBufferGraphics.setColor(scoreColor(3200));
			backBufferGraphics.drawString("            (_/ 0 ^ 0 )_/", screen.getWidth() - 250, 25);
		}
		if (score >= 4000 && score < 4800) {
			backBufferGraphics.setColor(scoreColor(4000));
			backBufferGraphics.drawString("             \\_( 0 ^ 0 )_/", screen.getWidth() - 240, 25);
		}
		if (score >= 4800) {
			backBufferGraphics.setColor(blinkingColor("HIGH_SCORES"));
			backBufferGraphics.drawString("             \\_( 0 ^ 0 )_/", screen.getWidth() - 240, 25);
		}
	}



	/**
	 * Draws current score on screen.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param score
	 *               Current score.
	 */
	public void drawScore(final Screen screen, final int score) {
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.setColor(scoreColor(score));
		String scoreString = String.format("%04d", score);
		backBufferGraphics.drawString(scoreString, screen.getWidth() - 80, 28);
	}

	public void drawLevel(final Screen screen, final int level){
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString(Integer.toString(level), 150, 25);
	}


	/**
	 * Draws number of remaining lives on screen.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param lives
	 *               Current lives.
	 */
	public void drawLives(final Screen screen, final int lives) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString(Integer.toString(lives), 20, 25);
		Ship dummyShip = new Ship(0, 0);
		for (int i = 0; i < lives; i++)
			drawEntity(dummyShip, 40 + 35 * i, 10);
	}

	public void drawLivesbar(final Screen screen, final int lives) {
		// Calculate the fill ratio based on the number of lives (assuming a maximum of 3 lives).
		double fillRatio = (double) lives / 3.0;

		// Determine the width of the filled portion of the rectangle.
		int filledWidth = (int) (120 * fillRatio);

		// Create a gradient paint that transitions from green to yellow.
		GradientPaint gradient = new GradientPaint(8, 8, Color.GREEN, 8 + filledWidth, 8, Color.YELLOW);

		// Cast Graphics to Graphics2D for gradient painting.
		Graphics2D g2d = (Graphics2D) backBufferGraphics;

		// Draw the outline of the rectangle.
		g2d.setColor(Color.WHITE);
		g2d.drawRect(8, 8, 120, 20);

		// Set the paint to the gradient and fill the left portion of the rectangle.
		g2d.setPaint(gradient);
		g2d.fillRect(8, 8, filledWidth, 20);

		// Set color for the "lives" text.
		g2d.setColor(Color.WHITE);

		// Calculate the position to center the "lives" text.
		int textX = (120 - fontRegularMetrics.stringWidth("Lives")) / 2;
		int textY = 8 + 20 / 2 + g2d.getFontMetrics().getAscent() / 2;

		// Draw the "lives" text in the center of the rectangle.
		g2d.drawString("lives", textX, textY);
	}

	/**
	 * Draws a thick line from side to side of the screen.
	 *
	 * @param screen
	 *                  Screen to draw on.
	 * @param positionY
	 *                  Y coordinate of the line.
	 */
	public void drawHorizontalLine(final Screen screen, final int positionY) {
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
		backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(),
				positionY + 1);
	}

	/**
	 * Creates blinking colors like an arcade screen.
	 * [Clean Code Team] This method was created by highlees.
	 *
	 * @param screen
	 */

	private Color blinkingColor(String color) {
		if (color == "HIGH_SCORES") {
			int R = (int) (Math.pow(Math.random() * (15 - 0), 2));
			int G = (int) (Math.random() * (255 - 0));
			int B = (int) 3.3 * LocalTime.now().getSecond();
			Color title = new Color(R, G, B);
			return title;
		}
		if (color == "GREEN") {
			Color green = new Color(0, (int) (Math.random() * (255 - 155) + 155), 0);
			return green;
		}
		if (color == "WHITE") {
			int RGB = (int) (Math.random() * (255 - 155) + 155);
			Color white = new Color(RGB, RGB, RGB);
			return white;
		}
		if (color == "GRAY") {
			int RGB = (int) (Math.random() * (160 - 100) + 100);
			Color gray = new Color(RGB, RGB, RGB);
			return gray;
		}
		return Color.WHITE;
	}

	/**
	 * Draws game title.
	 *
	 * @param screen
	 *               Screen to draw on.
	 */
	public void drawTitle(final Screen screen) {
		String titleString = "I N V A D E R S";
		String instructionsString = "Select with W + S, confirm with SPACE.";

		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 2);

		backBufferGraphics.setColor(blinkingColor("GREEN"));
		drawCenteredBigString(screen, titleString, screen.getHeight() / 3);
	}

	/**
	 * Draws main menu.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param option
	 *               Option selected.
	 */
	public void drawMenu(final Screen screen, final int option) {
		String playString = "P L A Y";
		String twoplayString = "2 P  P L A Y";
		String highScoresString = "H I G H  S C O R E S";
		String exitString = "E X I T";

		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 3 * 2);
		if (option == 4)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, twoplayString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2);
		if (option == 3)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, highScoresString, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 4);
		if (option == 0)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, exitString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 6);
	}

	public void drawSubMenu(final Screen screen, final int option) {
		String SelectString = "Select difficulty with W + S, confirm with SPACE.";
		String itemStoreString = "I T E M S T O R E";
		String ehanceString = "E N H A N C E M E N T";
		String playString = "C O N T I N U E";

		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, SelectString, screen.getHeight() / 8);
		if (option == 6)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, itemStoreString,
				screen.getHeight() / 3 * 2);
		if (option == 7)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, ehanceString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2);
		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, playString, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 4);
	}

	/**
	 * Draws Recovery menu.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param option
	 *               Option selected.
	 */

	public void drawRecoveryMenu(final Screen screen, final int option) {
		String SelectString = "Select state with W + S, confirm with SPACE.";
		String recoveryString = " Recovery ";
		String recovdefaultString = "D E F A U L T   S T A T E";
		String exitString = "E X I T";

		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, SelectString, screen.getHeight() / 3);
		backBufferGraphics.setColor(blinkingColor(" GREEN "));
		drawCenteredBigString(screen, recoveryString, screen.getHeight() / 5);
		if (option == 8)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, recovdefaultString,
				screen.getHeight() / 3 * 2);
		if (option == 9)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, exitString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2);

	}

	/**
	 * Draws Select menu.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param option
	 *               Option selected.
	 */
	public void drawSelect(final Screen screen, final int option) {
		String SelectString = "Select difficulty with W + S, confirm with SPACE.";
		String EasyString = "E A S Y";
		String NormalString = "N O R M A L";
		String HardString = "H A R D";
		String HardCoreString = "H A R D C O R E";
		String MainString = "M A I N";

		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, SelectString, screen.getHeight() / 8);

		if (option == 0)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, EasyString,
				screen.getHeight() / 6 * 2);
		if (option == 1)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, NormalString,
				screen.getHeight() / 6 * 2 + fontRegularMetrics.getHeight() * 2);
		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, HardString, screen.getHeight()
				/ 6 * 2 + fontRegularMetrics.getHeight() * 4);
		if (option == 3)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, HardCoreString, screen.getHeight() / 6 * 2
				+ fontRegularMetrics.getHeight() * 6);
		if (option == 4)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, MainString, screen.getHeight() / 6 * 2
				+ fontRegularMetrics.getHeight() * 8);
	}

	/**
	 * Draws game results.
	 *
	 * @param screen
	 *                       Screen to draw on.
	 * @param score
	 *                       Score obtained.
	 * @param livesRemaining
	 *                       Lives remaining when finished.
	 * @param shipsDestroyed
	 *                       Total ships destroyed.
	 * @param accuracy
	 *                       Total accuracy.
	 * @param isNewRecord
	 *                       If the score is a new high score.
	 */
	public void drawResults(final Screen screen, final int score,
			final int livesRemaining, final int shipsDestroyed, final int difficulty,
			final float accuracy, final boolean isNewRecord) {
		String scoreString = String.format("score %04d", score);
		String difficultyString = "Difficulty ";
		String livesRemainingString = "lives remaining " + livesRemaining;
		String shipsDestroyedString = "enemies destroyed " + shipsDestroyed;
		String accuracyString = String
				.format("Accuracy %.2f%%", accuracy * 100);

		int height = isNewRecord ? 4 : 2;

		if (difficulty == 0)
			difficultyString = difficultyString + "EASY";
		else if (difficulty == 1)
			difficultyString = difficultyString + "NORMAL";
		else if (difficulty == 2)
			difficultyString = difficultyString + "HARD";
		else if (difficulty == 3)
			difficultyString = difficultyString + "HARDCORE";

		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, scoreString, screen.getHeight()
				/ height);
		drawCenteredRegularString(screen, difficultyString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 2);
		drawCenteredRegularString(screen, livesRemainingString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 4);
		drawCenteredRegularString(screen, shipsDestroyedString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 6);
		drawCenteredRegularString(screen, accuracyString, screen.getHeight()
				/ height + fontRegularMetrics.getHeight() * 8);
	}

	/**
	 * Draws interactive characters for name input.
	 *
	 * @param screen
	 *                         Screen to draw on.
	 * @param name
	 *                         Current name selected.
	 * @param nameCharSelected
	 *                         Current character selected for modification.
	 */
	public void drawNameInput(final Screen screen, final char[] name,
			final int nameCharSelected) {
		String newRecordString = "New Record!";
		String introduceNameString = "Introduce name:";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredRegularString(screen, newRecordString, screen.getHeight()
				/ 4 + fontRegularMetrics.getHeight() * 10);
		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, introduceNameString,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 12);

		// 3 letters name.
		int positionX = screen.getWidth()
				/ 2
				- (fontRegularMetrics.getWidths()[name[0]]
						+ fontRegularMetrics.getWidths()[name[1]]
						+ fontRegularMetrics.getWidths()[name[2]]
						+ fontRegularMetrics.getWidths()[' ']) / 2;

		for (int i = 0; i < 3; i++) {
			if (i == nameCharSelected)
				backBufferGraphics.setColor(Color.GREEN);
			else
				backBufferGraphics.setColor(Color.WHITE);

			positionX += fontRegularMetrics.getWidths()[name[i]] / 2;
			positionX = i == 0 ? positionX
					: positionX
							+ (fontRegularMetrics.getWidths()[name[i - 1]]
									+ fontRegularMetrics.getWidths()[' ']) / 2;

			backBufferGraphics.drawString(Character.toString(name[i]),
					positionX,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight()
							* 14);
		}
	}

	/**
	 * Draws basic content of game over screen.
	 *
	 * @param screen
	 *                     Screen to draw on.
	 * @param acceptsInput
	 *                     If the screen accepts input.
	 * @param isNewRecord
	 *                     If the score is a new high score.
	 */
	public void drawGameOver(final Screen screen, final boolean acceptsInput,
			final boolean isNewRecord) {
		String gameOverString = "Game Over";
		String continueOrExitString = "Press SPACE to play again, ESC to exit";

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, gameOverString, screen.getHeight()
				/ height - fontBigMetrics.getHeight() * 2);

		if (acceptsInput)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, continueOrExitString,
				screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
	}

	/**
	 * Draws high score screen title and instructions.
	 *
	 * @param screen
	 *               Screen to draw on.
	 */
	public void drawHighScoreMenu(final Screen screen) {
		String highScoreString = "High Scores";
		String instructionsString = "Press SPACE to return";

		backBufferGraphics.setColor(blinkingColor("HIGH_SCORES"));
		drawCenteredBigString(screen, highScoreString, screen.getHeight() / 8);

		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 5);
	}

	/**
	 * Draws high scores.
	 *
	 * @param screen
	 *                   Screen to draw on.
	 * @param highScores
	 *                   List of high scores.
	 */
	public void drawHighScores(final Screen screen,
			final List<Score> highScores) {
		backBufferGraphics.setColor(blinkingColor("WHITE"));
		int i = 0;
		String scoreString = "";

		for (Score score : highScores) {
			scoreString = String.format("%s        %04d", score.getName(),
					score.getScore());
			drawCenteredRegularString(screen, scoreString, screen.getHeight()
					/ 4 + fontRegularMetrics.getHeight() * (i + 1) * 2);
			i++;
		}
	}

	/**
	 * Draws a centered string on regular font.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param string
	 *               String to draw.
	 * @param height
	 *               Height of the drawing.
	 */
	public void drawCenteredRegularString(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 2
				- fontRegularMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Draws a centered string on big font.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param string
	 *               String to draw.
	 * @param height
	 *               Height of the drawing.
	 */
	public void drawCenteredBigString(final Screen screen, final String string,
			final int height) {
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(string, screen.getWidth() / 2
				- fontBigMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Countdown to game start.
	 *
	 * @param screen
	 *                  Screen to draw on.
	 * @param level
	 *                  Game difficulty level.
	 * @param number
	 *                  Countdown number.
	 * @param bonusLife
	 *                  Checks if a bonus life is received.
	 */
	public void drawCountDown(final Screen screen, final int level,
			final int number, final boolean bonusLife) {
		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(Color.GREEN);

		if (number >= 4)
			if (!bonusLife) {
				drawCenteredBigString(screen, "Level " + level,
						screen.getHeight() / 2
								+ fontBigMetrics.getHeight() / 3);
			} else {
				drawCenteredBigString(screen, "Level " + level
						+ " - Bonus life!",
						screen.getHeight() / 2
								+ fontBigMetrics.getHeight() / 3);
			}
		else if (number != 0) {
			/* this if-else is modified with Clean Code (dodo_kdy) */
			if (isFirst)
				drawLoading(screen.getHeight() / 6, screen.getHeight() / 3, screen);
			else {
				if ((25 + 20 * (3 - number) < timercount && timercount < 40 + 20 * (3 - number)))
					backBufferGraphics.setColor(new Color(0, 0, 0, 222));
				drawCenteredBigString(screen, "Loading...",
						screen.getHeight() / 2
								+ fontBigMetrics.getHeight() / 3);
				timercount++;
			}
		} else {
			drawCenteredBigString(screen, "GO!", screen.getHeight() / 2
					+ fontBigMetrics.getHeight() / 3);
			isFirst = false;
			timercount = 0;
		}
	}

	public void drawItemStore(final Screen screen) {
		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(Color.GREEN);
	}

	public void drawEnhancePage(final Screen screen, final int option, int enhanceStone, int numEnhanceArea,
			int numEnhanceDamage) {
		String itemStoreString = "I T E M S T O R E";
		String playString = "C O N T I N U E";
		String enhanceAreaString = "Enhancement Area Lv\n" + Integer.toString(numEnhanceArea) + " > "
				+ Integer.toString(numEnhanceArea + 1)
				+ "(↑1)";
		String enhanceDamageString = "Enhancement Damage Lv\n" + Integer.toString(numEnhanceDamage) + " > "
				+ Integer.toString(numEnhanceDamage + 1)
				+ "(↑1)";

		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(Color.GREEN);

		if (option == 8)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, enhanceAreaString,
				screen.getHeight() / 3);
		if (option == 9)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, enhanceDamageString,
				screen.getHeight() / 3 + 50);
		if (option == 6)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, itemStoreString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2);
		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 4);
	}

	/**
	 * Creates a loading string with blink effect on the loading box.
	 *
	 * [Clean Code Team] This method was created by dodo_kdy.
	 *
	 * @param screen
	 */
	public void drawLoadingString(int x, int y, String string) {
		backBufferGraphics.setColor(Color.white);
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(string, x, y);

		if (timercount % 25 == 0)
			backBufferGraphics.setColor(new Color(253, 253, 253));
		else
			backBufferGraphics.setColor(new Color(255, 255, 255, 55));

		backBufferGraphics.drawString("...", x + fontBigMetrics.stringWidth("LOADING"), y);
	}

	/**
	 * Creates a loading progress bar/
	 *
	 * [Clean Code Team] This method was created by dodo_kdy.
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param g2
	 */
	public void loadingProgress(int startX, int startY, int endX, int endY, Graphics2D g2) {
		Color endColor = Color.green;
		Color startColor = Color.yellow;

		GradientPaint gradient = new GradientPaint(startX, startY, startColor, endX, endY + 20, endColor);
		g2.setPaint(gradient);
		g2.fill(new Rectangle(startX, startY, endX - startX, endY - startY));

		g2.setColor(Color.black);
		g2.fillRect(startX, startY, endX - startX, endY - startY - timercount);
	}

	/**
	 * Creates a loading box for 3 seconds.
	 *
	 * [Clean Code Team] This method was created by dodo_kdy.
	 *
	 * @param x
	 * @param y
	 * @param screen
	 */

	public void drawLoading(int x, int y, Screen screen) {
		int width = screen.getWidth() / 2, height = width / 2;
		Graphics2D g2 = (Graphics2D) backBufferGraphics;

		/* Background Box */
		g2.setColor(new Color(0, 255, 0, 230));
		g2.fillRect(x, y, width, height);
		drawLoadingString(x + width / 5, y + (width * 18) / 44, "LOADING");

		/* Loading Box */
		int out_x = x + width + screen.getWidth() / 30, out_width = screen.getWidth() / 10;
		g2.setColor(new Color(0, 255, 0, 222));
		g2.fillRect(out_x, y, out_width, height);

		int dx = screen.getWidth() / 65;
		g2.setColor(Color.black);
		g2.fillRect(out_x + dx, y + dx, out_width - 2 * dx, height - 2 * dx);

		/* Loading progress bar */
		int startX = out_x + dx + dx / 2, startY = y + dx + dx / 2,
				endX = startX + out_width - 2 * dx - dx, endY = startY + height - 2 * dx - dx;
		loadingProgress(startX, startY, endX, endY, g2);

		/* Animation box */
		g2.setColor(Color.black);
		g2.fillRect(x + (width * 3) / 44, y + (width * 3) / 44, (width / 44) * 38, (height / 44) * 22);
		animateLoading(x + (width * 3) / 44, y + (width * 3) / 44);

		/* Box border */
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.white);
		g2.drawRect(x - 1, y - 1, width + 2, height + 2);
		g2.setColor(new Color(255, 255, 255, 222));
		g2.drawRect(out_x - 1, y - 1, out_width + 2, height + 2);

		timercount++;
	}

	public void drawEnhanceElem(final Screen screen, int enhanceStone, int numEnhanceArea,
			int numEnhanceDamage) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString("Reinforced Stone: " + Integer.toString(enhanceStone), 20, 25);
		// Ship dummyShip = new Ship(0, 0);
		// drawEntity(dummyShip, 40 + 35, 10);
	}

	/**
	 * Creates an animation of monster.
	 *
	 * [Clean Code Team] This method was created by dodo_kdy.
	 *
	 * @param x
	 * @param y
	 */
	public int animateLoading(int x, int y){
			int y1 = y+7, x1 = x;
			if ( (30 <timercount && timercount<50) || (110 <timercount && timercount<130) ) y1 -=5;
			else if (70<timercount && timercount <90) x1+=5;
	
			this.drawEntity(SpriteType.values()[5],x1+15,y1+10,2.3,2.3);
			this.drawEntity(SpriteType.values()[6],x1+60,y1+10,2.4,2.4);
			this.drawEntity(SpriteType.values()[8],x1+100,y1+10,3,2.4);
			this.drawEntity(SpriteType.values()[10],x1+145,y1+13,2,2);
	
			return 1;
		}
	// public int animateLoading(int x, int y) {
	// 	try {
	// 		img1 = ImageIO.read(new File("res/invader_2.png"));
	// 		img2 = ImageIO.read(new File("res/invader_1.png"));
	// 		img3 = ImageIO.read(new File("res/invader_3.png"));
	// 		img4 = ImageIO.read(new File("res/invader_4.png"));
	// 	} catch (IOException exc) {
	// 		return 0;
	// 	}
		

	// 	int y1 = y + 10, y2 = y + 15, x1 = x;
	// 	if ((30 < timercount && timercount < 50) || (110 < timercount && timercount < 130))
	// 		y2 -= 5;
	// 	else if (70 < timercount && timercount < 90)
	// 		y1 -= 5;
	// 	else
	// 		x1 -= 5;

	// 	backBufferGraphics.drawImage(img1, x1 + 15, y1, 34, 34, null);
	// 	backBufferGraphics.drawImage(img2, x1 + 60, y2 - 2, 30, 24, null);
	// 	backBufferGraphics.drawImage(img3, x1 + 100, y1 - 10, 38, 55, null);
	// 	backBufferGraphics.drawImage(img4, x1 + 145, y2, 32, 27, null);
	// 	return 1;
	// }

}