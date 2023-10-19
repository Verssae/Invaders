package engine;

import entity.Coin;
import entity.Entity;
import entity.Ship;
import screen.Screen;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage; // monster animation on a loading box
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

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
	private static Font fontSmall;
	/** Normal sized font properties. */
	private static FontMetrics fontSmallMetrics;
	/** Normal sized font. */
	private static Font fontRegular;
	/** Normal sized font properties. */
	private static FontMetrics fontRegularMetrics;
	/** Big sized font. */
	private static Font fontBig;
	/** Big sized font properties. */
	private static FontMetrics fontBigMetrics;

	private  static Font fontVeryBig;
	public Cooldown endTimer = new Cooldown(3000);
	public long ghostTImer;
	public int ghostPostionX;
	public int ghostPostionY;
	public Color ghostColor = new Color(1, 1, 1);
	/** Cooldown timer for background animation. */
	private Cooldown bgTimer = new Cooldown(100);  // Draw bg interval
	private int brightness = 0;  // Used as RGB values for changing colors
	private int lighter = 1;  // For color to increase then decrease
	private Cooldown bgTimer_init = new Cooldown(3000);  // For white fade in at game start
	private Cooldown bgTimer_lines = new Cooldown(100);  // For bg line animation
	private int lineConstant = 0;  // For bg line animation

	/** Sprite types mapped to their images. */
	private static Map<SpriteType, boolean[][]> spriteMap;

	public int timercount = 0;


	//BufferedImage img1, img2, img3, img4;


	public int vector_x= 200, vector_y= 200, directionX = new Random().nextBoolean() ? 1 : -1,
			directionY = new Random().nextBoolean() ? 1 : -1;
	public Cooldown pump = new Cooldown(1000);

	boolean isFirst = true;

	int bigger = 36, direction = 1;
	public String getRandomCoin;

	/** Sprite types. */
	public static enum SpriteType {
		/** Player ship. */
		ShipA,
		ShipB,
		ShipC,
		/** Destroyed player ship. */
		ShipADestroyed,
		ShipBDestroyed,
		ShipCDestroyed,
		/** Player bullet. */
		Bullet,
		/** Player bulletY. */
		BulletY,
		/** Enemy bullet. */
		EnemyBullet,
		/** Enemy bullet goes left diag. */
		EnemyBulletLeft,
		/** Enemy bullet goes right diag. */
		EnemyBulletRight,
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
		/** Reinforced third enemy ship - first form. */
		EnemyShipSC1,
		/** Reinforced third enemy ship - second form. */
		EnemyShipSC2,
		/** Forth enemy ship - first form. */
		EnemyShipD1,
		/** Forth enemy ship - second form. */
		EnemyShipD2,
		/** Forth enemy ship (hit 1) - third form. */
		EnemyShipD3,
		/** Forth enemy ship (hit 1) - forth form. */
		EnemyShipD4,
		/** Forth enemy ship (hit 2) - fifth form. */
		EnemyShipD5,
		/** Forth enemy ship (hit 2)- sixth form. */
		EnemyShipD6,
		/** Bonus ship1. */
		EnemyShipSpecial1,
		/** Bonus ship2. */
		EnemyShipSpecial2,
		/** Bonus ship3. */
		EnemyShipSpecial3,
		/** Bonus ship4. */
		EnemyShipSpecial4,
		/** Boss ship - first form. */
		BossA1,
		/** Boss ship - second form. */
		BossA2,
		/** Destroyed enemy ship. */
		Explosion,

		/** random sprit**/
		Trash1,
		Trash2,
		Trash3,
		Trash4,

		BulletLine,
		/** Destroyed enemy ship2. */
		Explosion2,
		/** Destroyed enemy ship3. */
		Explosion3,
		/** Destroyed enemy ship4. */
		Explosion4,
		/** Buff_item dummy sprite*/
		Buff_Item,
		/** Debuff_item dummy sprite */
		Debuff_Item,
		/** Laser */
		Laser,
		/** Laserline */
		LaserLine,
		Coin,
		BlueEnhanceStone,
		PerpleEnhanceStone,
		ShipAShileded,
		ShipBShileded,
		ShipCShileded,
        EnhanceStone,
		//ShipCShileded,
		gravestone,
		Ghost;
	};


	/**
	 * Private constructor.
	 */
	private DrawManager() {
		fileManager = Core.getFileManager();
		logger = Core.getLogger();
		logger.info("Started loading resources.");
		try {
			Random random = new Random();
			int Trash_enemyA = random.nextInt(3);
			spriteMap = new LinkedHashMap<SpriteType, boolean[][]>();
			spriteMap.put(SpriteType.ShipA, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipB, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipC, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipADestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipBDestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipCDestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.Bullet, new boolean[3][5]);
			spriteMap.put(SpriteType.BulletY, new boolean[5][7]);
			spriteMap.put(SpriteType.EnemyBullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBulletLeft, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBulletRight, new boolean[3][5]);
			if (Trash_enemyA == 0){
				spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
				spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash1, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash2, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash3, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash4, new boolean[12][8]);
			}
			else if (Trash_enemyA == 1){
				spriteMap.put(SpriteType.Trash1, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash2, new boolean[12][8]);
				spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
				spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash3, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash4, new boolean[12][8]);
			}
			else{
				spriteMap.put(SpriteType.Trash1, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash2, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash3, new boolean[12][8]);
				spriteMap.put(SpriteType.Trash4, new boolean[12][8]);
				spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
				spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
			}
			spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipD1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipD2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipD3, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipD4, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipD5, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipD6, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSpecial1, new boolean[16][7]);
			spriteMap.put(SpriteType.EnemyShipSpecial2, new boolean[16][7]);
			spriteMap.put(SpriteType.EnemyShipSpecial3, new boolean[16][7]);
			spriteMap.put(SpriteType.EnemyShipSpecial4, new boolean[16][7]);
			spriteMap.put(SpriteType.Explosion, new boolean[13][7]);
			spriteMap.put(SpriteType.BulletLine, new boolean[1][160]);
			spriteMap.put(SpriteType.Explosion2, new boolean[13][7]);
			spriteMap.put(SpriteType.Explosion3, new boolean[12][8]);
			spriteMap.put(SpriteType.Buff_Item, new boolean[9][9]);
			spriteMap.put(SpriteType.Debuff_Item, new boolean[9][9]);
			spriteMap.put(SpriteType.BlueEnhanceStone, new boolean[8][8]);
			spriteMap.put(SpriteType.PerpleEnhanceStone, new boolean[8][8]);
			spriteMap.put(SpriteType.BossA1, new boolean[22][13]);
			spriteMap.put(SpriteType.BossA2, new boolean[22][13]);
			spriteMap.put(SpriteType.Laser, new boolean[2][240]);
			spriteMap.put(SpriteType.LaserLine, new boolean[1][240]);
			spriteMap.put(SpriteType.Coin, new boolean[7][7]);
			spriteMap.put(SpriteType.ShipAShileded, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipBShileded, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipCShileded, new boolean[13][8]);
			spriteMap.put(SpriteType.Explosion4, new boolean[10][10]);
			spriteMap.put(SpriteType.gravestone, new boolean[13][9]);
			spriteMap.put(SpriteType.Ghost, new boolean[9][11]);
			fileManager.loadSprite(spriteMap);
			logger.info("Finished loading the sprites.");

			// Font loading.
			fontSmall = fileManager.loadFont(12f);
			fontRegular = fileManager.loadFont(14f);
			fontBig = fileManager.loadFont(24f);
			fontVeryBig = fileManager.loadFont(40f);
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

		fontSmallMetrics = backBufferGraphics.getFontMetrics(fontSmall);
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
			final int positionY, final double width, final double height,
			final Color color) {
		boolean[][] image = spriteMap.get(SpriteType);
		Graphics2D g2 = (Graphics2D) backBufferGraphics;
		g2.setColor(color);

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

	private Color levelColor(final int level) {
		if (level == 1)
			return Color.WHITE;
		if (level == 2)
			return new Color(206, 255, 210);
		if (level == 3)
			return new Color(151, 255, 158);
		if (level == 4)
			return new Color(88, 255, 99);
		if (level == 5)
			return new Color(50, 255, 64);
		if (level == 6)
			return new Color(0, 255, 17);
		if (level == 7)
			return new Color(0,250,13);
		else
			return new Color(0,250,10);
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

	public void drawLevel(final Screen screen, final int level){
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.setColor(levelColor(level));
		backBufferGraphics.drawString(Integer.toString(level), 150, 25);
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

	/**
	 * Draws current score on screen.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param coin
	 *               Current score.
	 */
	public void drawCoin(final Screen screen, final Coin coin, final int drawCoinOption) {
		if (drawCoinOption == 0) {
			this.drawEntity(SpriteType.Coin, screen.getWidth() - 179, 13, 2, 2, Color.green);
			backBufferGraphics.setFont(fontBig);
			backBufferGraphics.setColor(Color.WHITE);
			String coinString = String.format("%03d", coin.getCoin());
			backBufferGraphics.drawString(coinString, screen.getWidth() - 160, 28);
		}
		else if (drawCoinOption == 1) {
			this.drawEntity(SpriteType.Coin, 20, 13, 2, 2, Color.green);
			backBufferGraphics.setFont(fontBig);
			backBufferGraphics.setColor(Color.WHITE);
			String coinString = String.format("%03d", coin.getCoin());
			backBufferGraphics.drawString(coinString, 40, 28);
		}
	}

    public void BulletsCount(final Screen screen, final int BulletsCount) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		String text = "Remaining Bullets: " + String.format("%02d", BulletsCount);
		backBufferGraphics.drawString(text, screen.getWidth() - 180, 65);
	}

	/**
	 * Draws number of remaining lives on screen.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param lives
	 *               Current lives.
	 */

	public void drawLivesbar(final Screen screen, final double lives) {
		// Calculate the fill ratio based on the number of lives (assuming a maximum of 3 lives).
		double fillRatio = lives / 3.0;

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
		int textY = 6 + 20 / 2 + g2d.getFontMetrics().getAscent() / 2;

		// Draw the "lives" text in the center of the rectangle.
		g2d.drawString("Lives", textX, textY);
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
	 * Draws a circle line.
	 *
	 * @param screen
	 *                  Screen to draw on.
	 * @param positionX
	 *                  X coordinate of the line.
	 * @param positionY
	 *                  Y coordinate of the line.
	 * @param width
	 *                  Y coordinate of the line.
	 * @param height
	 *                  Y coordinate of the line.
	 * @param graphicOption
	 *                  if option 0, use backBufferGraphics Object. Otherwise use graphics Object.
	 */
	public void drawCircleLine(final Screen screen, final int positionX, final int positionY, final int width, final int height, final int graphicOption) {
		backBufferGraphics.setColor(Color.GREEN);
		((Graphics2D) backBufferGraphics).setStroke(new BasicStroke(2));
		if (graphicOption == 0){
			backBufferGraphics.drawOval(positionX, positionY, width, height);
		}
		else {
			graphics.drawOval(positionX, positionY, width, height);
		}
	}
	/**
	 * Draws a circle filled.
	 *
	 * @param screen
	 *                  Screen to draw on.
	 * @param positionX
	 *                  X coordinate of the line.
	 * @param positionY
	 *                  Y coordinate of the line.
	 * @param width
	 *                  Y coordinate of the line.
	 * @param height
	 *                  Y coordinate of the line.
	 */
	public void drawCircleFill(final Screen screen, final int positionX, final int positionY, final int width, final int height) {
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillOval(positionX, positionY, width, height);
	}

	/**
	 * Creates blinking colors like an arcade screen.
	 * [Clean Code Team] This method was created by highlees.
	 *
	 *
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
	 * Create slowly changing colors.
	 * Can be applied to multiple screens in the game.
	 * [Clean Code Team] This method was created by highlees.
	 *
	 *
	 */

	private Color slowlyChangingColors(String color) {
		String sec = Integer.toString(LocalTime.now().getSecond());
		char c = sec.charAt(sec.length() - 1);
		if (color == "GREEN") {
			if (c == '0') return new Color(0, 75, 0);
			if (c == '1') return new Color(0, 100, 0);
			if (c == '2') return new Color(0, 125, 0);
			if (c == '3') return new Color(0, 150, 0);
			if (c == '4') return new Color(0, 175, 0);
			if (c == '5') return new Color(0, 205, 0);
			if (c == '6') return new Color(0, 225, 0);
			if (c == '7') return new Color(0, 254, 0);
			if (c == '8') return new Color(0, 55, 0);
			if (c == '9') return new Color(0, 65, 0);
		}
		if (color == "GRAY") {
			if (c == '0') return new Color(75, 75, 75);
			if (c == '1') return new Color(85, 85, 85);
			if (c == '2') return new Color(105, 105, 105);
			if (c == '3') return new Color(130, 130, 130);
			if (c == '4') return new Color(155, 155, 155);
			if (c == '5') return new Color(180, 180, 180);
			if (c == '6') return new Color(205, 205, 205);
			if (c == '7') return new Color(225, 225, 225);
			if (c == '8') return new Color(55, 55, 55);
			if (c == '9') return new Color(65, 65, 65);
		}
		if (color == "RAINBOW") {
			if (c == '0') return new Color(254, 254, 0);
			if (c == '1') return new Color(135, 254, 0);
			if (c == '2') return new Color(0, 254, 0);
			if (c == '3') return new Color(0, 254, 254);
			if (c == '4') return new Color(0, 135, 254);
			if (c == '5') return new Color(0, 0, 254);
			if (c == '6') return new Color(135, 0, 205);
			if (c == '7') return new Color(254, 0, 224);
			if (c == '8') return new Color(254, 0, 135);
			if (c == '9') return new Color(220, 200, 254);
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
		String storeString1 = "S T O R E"; 

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
		if (option == 6)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, storeString1, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 8);
	}

	public void drawRandomBox(final Screen screen, final int option) {
		String introduceString1 = "SELECT ONE OF THE THREE BOXES";
		String introduceString2 = "FOR A RANDOM REWARD";
		String oneString = "1";
		String twoString = "2";
		String threeString = "3";
		try {
			BufferedImage image1 = ImageIO.read(new File("res/giftbox1.png"));
			BufferedImage greenImage1 = image1; // 먼저 초록색으로 처리할 이미지를 원래 이미지로 초기화
			if (option == 10) { // 옵션에 따라 이미지를 초록색으로 변환
				RescaleOp greenFilter = new RescaleOp(new float[]{0f, 1f, 0f, 1f}, new float[]{0f, 0f, 0f, 0f}, null);
				greenImage1 = greenFilter.filter(image1, null);
				int randomCoin = (int) (Math.random() * 11) * 5;
    			getRandomCoin = Integer.toString(randomCoin);
			}
			backBufferGraphics.drawImage(greenImage1, screen.getWidth() / 4 - 27, screen.getHeight() / 2 + 20, 60, 60, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			BufferedImage image2 = ImageIO.read(new File("res/giftbox1.png"));
			BufferedImage greenImage2 = image2;
			if (option == 7) {
				RescaleOp greenFilter = new RescaleOp(new float[]{0f, 1f, 0f, 1f}, new float[]{0f, 0f, 0f, 0f}, null);
				greenImage2 = greenFilter.filter(image2, null);
				int randomCoin = (int) (Math.random() * 11) * 5;
    			getRandomCoin = Integer.toString(randomCoin);
			}
			backBufferGraphics.drawImage(greenImage2, screen.getWidth() * 2 / 4 - 25, screen.getHeight() / 2 + 20, 60, 60, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			BufferedImage image3 = ImageIO.read(new File("res/giftbox1.png"));
			BufferedImage greenImage3 = image3;
			if (option == 2) {
				RescaleOp greenFilter = new RescaleOp(new float[]{0f, 1f, 0f, 1f}, new float[]{0f, 0f, 0f, 0f}, null);
				greenImage3 = greenFilter.filter(image3, null);
				int randomCoin = (int) (Math.random() * 11) * 5;
    			getRandomCoin = Integer.toString(randomCoin);
			}
			backBufferGraphics.drawImage(greenImage3, screen.getWidth() * 3 / 4 - 25, screen.getHeight() / 2 + 20, 60, 60, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// backBufferGraphics.setColor(slowlyChangingColors("RAINBOW"));
		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, introduceString1, screen.getHeight() / 8);
		drawCenteredRegularString(screen, introduceString2, screen.getHeight() / 6);
		if (option == 10)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		backBufferGraphics.drawString(oneString, screen.getWidth() / 4, screen.getHeight() * 3 / 4);

		if (option == 7)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		backBufferGraphics.drawString(twoString, screen.getWidth() * 2 / 4, screen.getHeight() * 3 / 4);
		
		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		backBufferGraphics.drawString(threeString, screen.getWidth() * 3 / 4, screen.getHeight() * 3 / 4);
	}
	
	public void drawRandomReward(final Screen screen, final int option) {
		
		String introduceString = "RANDOM REWARD";
		String nextString = "N E X T";
	
		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, introduceString, screen.getHeight() / 8);
		drawCenteredRegularString(screen, getRandomCoin, screen.getHeight() / 2);
		backBufferGraphics.setColor(blinkingColor("GREEN"));
		backBufferGraphics.drawString(nextString, (screen.getWidth() - fontRegularMetrics.stringWidth(nextString)) / 2, screen.getHeight() * 3 / 4);
	}

	/**
	 * Draws sub menu.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param option
	 *               Option selected.
	 */
	public void drawSubMenu(final Screen screen, final int option) {
		String SelectString = "Select difficulty with W + S, confirm with SPACE.";
		String itemStoreString = "I T E M S T O R E";
		String ehanceString = "E N H A N C E M E N T";
		String playString = "C O N T I N U E";
		String skinStoreString = "S K I N S T O R E";

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
		if (option == 86)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, skinStoreString, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 6);	
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
		String recoveryString = " R E C O V E R Y ";
		String recovdefaultString = "D E F A U L T   S T A T E";
		String exitString = "E X I T";

		backBufferGraphics.setColor(blinkingColor("YELLOW"));
		drawCenteredBigString(screen, recoveryString, screen.getHeight() / 5);
		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, SelectString, screen.getHeight() / 3);
		
		if (option == 30)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, recovdefaultString,
				screen.getHeight() / 3 * 2);
		if (option == 31)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, exitString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2);

	}

	/**
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
	 * Draws Select menu.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param option
	 *            Stage(level) selected.
	 * If the number of Levels is changed, this page is also automatically changed the same as it.
	 */
	public void drawStageSelect(final Screen screen, final int option, final int stages) {
		String SelectString = "Select Level with WASD, confirm with Space,";
		String SelectString_2 = "cancel with ESC.";
		backBufferGraphics.setColor(blinkingColor("GRAY"));
		drawCenteredRegularString(screen, SelectString,screen.getHeight() / 8);
		drawCenteredRegularString(screen, SelectString_2,screen.getHeight() / 8 + screen.getHeight() / 16);
		String[] Stage = new String[stages];
		backBufferGraphics.setFont(fontBig);
		for (int i = 0; i < stages; i++) {
			Stage[i] = String.valueOf(i+1);
			if (option == i)
				backBufferGraphics.setColor(blinkingColor("GREEN"));
			else
				backBufferGraphics.setColor(blinkingColor("WHITE"));
			backBufferGraphics.drawString(Stage[i], screen.getWidth() / 2
					- (screen.getWidth()/10) * (2-(i%5)),
					screen.getHeight() / 5 * 2 + fontRegularMetrics.getHeight() * (2*((i/5)-1)));
		}
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
			final double livesRemaining, final int shipsDestroyed, final int difficulty,
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

		backBufferGraphics.setColor(slowlyChangingColors("GRAY"));
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

		backBufferGraphics.setColor(slowlyChangingColors("GREEN"));
		drawCenteredRegularString(screen, newRecordString, screen.getHeight()
				/ 4 + fontRegularMetrics.getHeight() * 10);
		backBufferGraphics.setColor(slowlyChangingColors("GRAY"));
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
				backBufferGraphics.setColor(slowlyChangingColors("GREEN"));
			else
				backBufferGraphics.setColor(slowlyChangingColors("GRAY"));

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

		backBufferGraphics.setColor(slowlyChangingColors("GREEN"));
		drawCenteredBigString(screen, gameOverString, screen.getHeight()
				/ height - fontBigMetrics.getHeight() * 2);

		if (acceptsInput)
			backBufferGraphics.setColor(slowlyChangingColors("GREEN"));
		else
			backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, continueOrExitString,
				screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
	}

	/**
	 * Draws Pause notification during game
	 *
	 * @param screen
	 *            Screen to draw on.
	 */
	public void drawPaused(final Screen screen) {
		String Paused = "Press ENTER to continue.";
		String Quit = "Press BackSpace to quit.";
		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, Paused, screen.getHeight() * 3 / 4);
		drawCenteredBigString(screen, Quit, screen.getHeight() * 5 / 6);
	}


	public void drawDiffScore(final Screen screen, final int difficulty) {
		String EasyString = "EASY";
		String NormalString = "NORMAL";
		String HardString = "HARD";
		String HardCoreString = "HARDCORE";

		backBufferGraphics.setFont(fontRegular);
		if (difficulty == 0)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("GRAY"));
		backBufferGraphics.drawString(EasyString, screen.getWidth() / 8
				- fontRegularMetrics.stringWidth(EasyString) / 2, screen.getHeight() * 2/7);
		if (difficulty == 1)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("GRAY"));
		backBufferGraphics.drawString(NormalString, screen.getWidth() * 3 / 8
				- fontRegularMetrics.stringWidth(NormalString) / 2, screen.getHeight() * 2/7);
		if (difficulty == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("GRAY"));
		backBufferGraphics.drawString(HardString, screen.getWidth() * 5 / 8
				- fontRegularMetrics.stringWidth(HardString) / 2, screen.getHeight() * 2/7);
		if (difficulty == 3)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("GRAY"));
		backBufferGraphics.drawString(HardCoreString, screen.getWidth() * 7 / 8
				- fontRegularMetrics.stringWidth(HardCoreString) / 2, screen.getHeight() * 2/7);
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
		String rank[] = {"1st", "2nd", "3th", "4th", "5th"};

		for (Score score : highScores) {
			scoreString = String.format("%s        %s        %04d", rank[i], score.getName(),
					score.getScore());
			drawCenteredRegularString(screen, scoreString, screen.getHeight()
					/ 3 + fontRegularMetrics.getHeight() * (i + 1) * 2);
			i++;
			if (i > 5)
				break;
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
		backBufferGraphics.setColor(Color.GREEN);

		if (number >= 4)
			if (!bonusLife) {
				pumpingLevel(screen, "Level " + level,screen.getHeight() / 2
						+ fontBigMetrics.getHeight() / 3);
			} else {
				drawCenteredBigString(screen, "Level " + level
						+ " - Bonus life!",
						screen.getHeight() / 2
								+ fontBigMetrics.getHeight() / 3);
			}
		else if (number != 0) {
			if (isFirst)
				drawLoading(screen.getHeight() / 6, screen.getHeight() / 3, screen);
			else {
				drawLoadingNeon(screen, "Loading...",
						screen.getHeight() / 2
								+ fontBigMetrics.getHeight() / 3, number);
				timercount++;
			}
		} else {
			drawGo(screen, "GO!", screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
			isFirst = false;
			timercount = 0;
		}
	}




	public void pumpingLevel(Screen screen,String string,int height){
		Font font = fontBig;
		try {
			font = fileManager.loadFont(bigger);
			if (bigger >= 40 || bigger <= 25 ) direction *= -1;
		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}

		Graphics2D g2 = (Graphics2D)backBufferGraphics;
		g2.setColor(pumpColor());
		g2.setFont(font);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawString(string,screen.getWidth() / 2 - g2.getFontMetrics().stringWidth(string) / 2, height);

		bigger+=direction;
	}

	public Color pumpColor(){
		int r = new Random().nextInt(5);
		if(r == 1) return new Color(147, 227, 83, 234);
		else if (r==2) return new Color(26, 255, 0, 255);
		else if (r==3) return new Color(45, 255, 167, 245);
		else if (r==4) return new Color(0, 255, 0, 77);
		else return new Color(27, 215, 136, 245);

 	}


	public void drawGo(final Screen screen, final String string, final int height){
		Font font = fontBig;
		try {
			font = fileManager.loadFont(30);
		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}

		Graphics2D g2 = (Graphics2D)backBufferGraphics;
		g2.setFont(font);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		float[] fractions = new float[30];
		Color[] colors = new Color[30];
		for (int i = 0; i < colors.length; i++) {
			fractions[i] = ((float)i) / 30;
			float hue = fractions[i];
			colors[i] = Color.getHSBColor(hue, 1f, 1f);
		}
		//Paint p = new LinearGradientPaint(0, 0, 80, 0, fractions, colors);
		//g2.setPaint(p);

		GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(),string);
		Shape shape = gv.getOutline();
		g2.setStroke(new BasicStroke(1.6f));
		g2.translate(screen.getWidth() / 2 - fontBigMetrics.stringWidth(string) / 2 - 5, height);
		g2.draw(shape);

	}

	/**
	 * Draw a Loading String like neon sign.
	 * [Clean-Code Team] This method was created by dodo_kdy.
	 *
	 * @param screen
	 * @param string
	 * @param height
	 * @param number
	 */
	public void drawLoadingNeon(final Screen screen, final String string, final int height, int number) {
		Font font1 = fontBig;
		try {
			font1 = fileManager.loadFont(33f);
		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}

		Graphics2D g2d = (Graphics2D)backBufferGraphics;
		g2d.setFont(font1);
		g2d.setColor(new Color(26, 255, 0));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if ((25 + 20 * (3 - number) < timercount && timercount < 40 + 20 * (3 - number)))
			g2d.setColor(new Color(0, 0, 0,0));


		GlyphVector gv = font1.createGlyphVector(g2d.getFontRenderContext(),string);
		Shape shape = gv.getOutline();
		g2d.setStroke(new BasicStroke(1.6f));
		g2d.translate(screen.getWidth() / 2 - fontBigMetrics.stringWidth(string) / 2 - 5, height);
		g2d.draw(shape);
	}



	public void drawItemStore(final Screen screen, final int option) {
		// Coin coinInstance = new Coin();
		// int coinValue = coinInstance.getCoin(); 
		String itemStoretxt = " * I T E M S T O R E * ";
		String txt = " C O N T I N U E";
		String buyString = " B U Y";
		String addcoinString = " P L U S C O I N";
		// String coinString = " C O I N : " + coinValue;
		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(Color.green);
		drawCenteredBigString(screen, itemStoretxt,	screen.getHeight()/4 - 97);
		backBufferGraphics.setColor(Color.YELLOW);
		backBufferGraphics.setFont(fontRegular);
		// backBufferGraphics.drawString(coinString, (screen.getWidth() - fontRegularMetrics.stringWidth(coinString)) - 10, screen.getHeight()/8 - 8);
		drawHorizontalLine(screen, screen.getHeight()/14);
		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, txt,
				screen.getHeight() / 3 * 2);
		if (option == 14)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, buyString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2);
		if (option == 15)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, addcoinString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 4);
	}
	
	/**
	 * Draws  skin store.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param option
	 *               Option selected.
	 */

	public void drawSkinStore(final Screen screen, final int option) {
		// Coin coinInstance = new Coin();
		// int coinValue = coinInstance.getCoin();

		String skinStoreTxt = " S K I N S T O R E";
		String buyString = " B U Y";
		// String coinString = " C O I N : " + coinValue;
		String gameAgain = " C O N T I N U E";

		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredRegularString(screen, skinStoreTxt,	screen.getHeight()/4 - 80);
		backBufferGraphics.setColor(Color.YELLOW);
		// backBufferGraphics.drawString(coinString, (screen.getWidth() - fontRegularMetrics.stringWidth(coinString)) / 2, screen.getHeight()/8+10);

		if (option == 16)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, buyString,
				screen.getHeight() / 3* 2 + fontRegularMetrics.getHeight() * 2);
		
		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, gameAgain,
				screen.getHeight() / 3* 2 + fontRegularMetrics.getHeight() * 4);
		
	}

	/**
	 * Draws String on Enhance screen.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param enhanceString
	 *               String to draw on.
	 * @param positionX
	 *               X coordinate of the line.
	 * @param positionY
	 *               Y coordinate of the line.
	 * @param color
	 *               Color of the String on Enhance Screen.
	 * @param fontSizeOption
	 *               Option of font size.
	 */

	public void drawEnhanceStoneString(final Screen screen, final String enhanceString, 
										final int positionX, final int positionY, 
										final Color color, int fontSizeOption) {
		
		if (fontSizeOption == 0)
			backBufferGraphics.setFont(fontSmall);
		else if (fontSizeOption == 1)
			backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(color);

		int textWidth = backBufferGraphics.getFontMetrics().stringWidth(enhanceString);
		int centerX = positionX - textWidth / 2;

		backBufferGraphics.drawString(enhanceString, centerX, positionY);
	}

	public void drawEnhanceSprite(final Screen screen,
								  final int leftCircleX, final int rightCircleX, final int sideCircleY, 
								  final int sideCircleWidth, final int sideCircleHeight) {
		// backBufferGraphics.setFont(fontRegular);
		// backBufferGraphics.setColor(Color.WHITE);
		// backBufferGraphics.drawString("Reinforced Stone: " + Integer.toString(enhanceStone), 20, 25);
		// Ship dummyShip = new Ship(0, 0);
		// drawEntity(dummyShip, 40 + 35, 10);
		// BlueEnhanceStoneArea = new Entity.EnhanceStone()
		// PerpleEnhanceStoneAttack = spriteMap.get(SpriteType.PerpleEnhanceStone);
		SpriteType BlueEnhanceAreaStone = SpriteType.BlueEnhanceStone;
		SpriteType PerpleEnhanceAttackStone = SpriteType.PerpleEnhanceStone;
					
		this.drawEntity(BlueEnhanceAreaStone, leftCircleX + sideCircleWidth / 4 - 2, sideCircleY + sideCircleHeight / 4 - 2, 5, 5, Color.magenta);							
		this.drawEntity(PerpleEnhanceAttackStone, rightCircleX + sideCircleWidth / 4 - 2, sideCircleY + sideCircleHeight / 4 - 2, 5, 5, Color.BLUE);							
		// this.drawEntity(, 100, 100);
		// this.drawEntity(, 50, 50);
	}

	/**
	 * Draws String on Enhance Menu.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param option
	 *               Option selected.
	 * @param valEnhanceArea
	 *               Current Value of Enhanced Area Range.
	 * @param valEnhanceDamage
	 *               Current Value of Enhanced Damage.
	 * @param lvEnhanceArea
	 *               Current Level of Enhanced Area Range.
	 * @param lvEnhanceDamage
	 *               Current Level of Enhanced Damage.
	 */

	public void drawEnhanceMenu(final Screen screen, final int option, 
								int numEnhanceArea, int numEnhanceDamage, 
								int lvEnhanceArea, int lvEnhanceDamage) {

		String subMenuString = "S U B M E N U";
		String itemStoreString = "I T E M S T O R E";
		String playString = "C O N T I N U E";
		String lvEnhanceAreaString = "Area Lv" + Integer.toString(lvEnhanceArea) + " > "
				+ Integer.toString(lvEnhanceArea + 1);
		String lvEnhanceDamageString = "Damage Lv" + Integer.toString(lvEnhanceDamage) + " > "
				+ Integer.toString(lvEnhanceDamage + 1);
		String valEnhanceAreaString =  "1/" + Integer.toString(numEnhanceArea);
		String valEnhanceDamageString = "1/" + Integer.toString(numEnhanceDamage);

    	/** Height of the interface separation line. */
    	int SEPARATION_LINE_HEIGHT = 40;

		int screenWidth = screen.getWidth();
		int centeredCircleWidth = 170;
        int centeredCircleHeight = 170;
		int centeredCircleX = (screenWidth - 170) / 2;
        int centeredCircleY = SEPARATION_LINE_HEIGHT * 2;
        int sideCircleWidth = 70;
        int sideCircleHeight = 70;
        int leftCircleX = (screenWidth - 220) / 2;
        int rightCircleX = screenWidth - (screenWidth - 220) / 2 - 70;
        int sideCircleY = SEPARATION_LINE_HEIGHT * 5;	

		backBufferGraphics.setColor(Color.GREEN);



		if (option == 8){
			drawEnhanceStoneString(screen, valEnhanceAreaString,
				leftCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 60,
				Color.GREEN, 1);
			drawEnhanceStoneString(screen, lvEnhanceAreaString,
				centeredCircleX + centeredCircleWidth / 2, centeredCircleY + centeredCircleHeight * 4 / 5 - 30,
				Color.lightGray, 0);
		}
		else{
			drawEnhanceStoneString(screen, valEnhanceAreaString,
				leftCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 60,
				Color.WHITE, 1);
			drawEnhanceStoneString(screen, lvEnhanceAreaString,
				centeredCircleX + centeredCircleWidth / 2, centeredCircleY + centeredCircleHeight * 4 / 5 - 30,
				Color.GRAY, 0);
		}
		if (option == 9){
			drawEnhanceStoneString(screen, valEnhanceDamageString,
				rightCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 60,
				Color.GREEN, 1);
			drawEnhanceStoneString(screen, lvEnhanceDamageString,
				centeredCircleX + centeredCircleWidth / 2, centeredCircleY + centeredCircleHeight * 4 / 5 - 10,
				Color.lightGray, 0);
		}
		else{
			drawEnhanceStoneString(screen, valEnhanceDamageString,
				rightCircleX + sideCircleWidth / 2, sideCircleY + sideCircleHeight + 60,
				Color.WHITE, 1);
			drawEnhanceStoneString(screen, lvEnhanceDamageString,
				centeredCircleX + centeredCircleWidth / 2, centeredCircleY + centeredCircleHeight * 4 / 5 - 10,
				Color.GRAY, 0);
		}
		if (option == 5)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, subMenuString,
				screen.getHeight() / 3 * 2 + 45);
		if (option == 6)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, itemStoreString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2 + 45);
		if (option == 2)
			backBufferGraphics.setColor(blinkingColor("GREEN"));
		else
			backBufferGraphics.setColor(blinkingColor("WHITE"));
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 4 + 45);
	}

	/**
	 * Creates a loading string with blink effect on the loading box.
	 *
	 * [Clean Code Team] This method was created by dodo_kdy.
	 *
	 *
	 */
	public void drawLoadingString(int x, int y, String string) {
		backBufferGraphics.setColor(Color.white);
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(string, x, y);

		if (timercount % 25 == 0) backBufferGraphics.setColor(new Color(253, 253, 253));
		else backBufferGraphics.setColor(new Color(255, 255, 255, 55));

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
	public void loadingProgress(float startX, float startY, float endX, float endY, Graphics2D g2) {
		Color endColor = Color.green;
		Color startColor = Color.yellow;

		GradientPaint gradient = new GradientPaint(startX, startY, startColor,  endX, endY , endColor);
		g2.setPaint(gradient);
		g2.fill(new Rectangle2D.Double(startX, startY, endX - startX, endY - startY));

		g2.setColor(Color.black);
		g2.fill(new Rectangle2D.Double(startX, startY, endX - startX , endY - startY  - timercount));
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
		float box1_W = screen.getWidth() / 2, box1_H = box1_W / 2;
		Graphics2D g2 = (Graphics2D) backBufferGraphics;

		/* Background Box */
		g2.setColor(new Color(0, 255, 0, 230));
		g2.fill(new Rectangle2D.Double(x, y, box1_W, box1_H));
		drawLoadingString((int) (x + box1_W / 5), (int) (y + box1_H * 0.85), "LOADING");

		/* Loading Box */
		float box2_x = x + box1_W + screen.getWidth() / 30, box2_W = box1_W / 5;
		g2.setColor(new Color(0, 255, 0, 222));
		g2.fill(new Rectangle2D.Double(box2_x, y, box2_W, box1_H));

		float dx = box2_W / 7;
		g2.setColor(Color.black);
		g2.fill(new Rectangle2D.Double(box2_x + dx, y + dx, box2_W - 2 * dx, box1_H - 2 * dx));

		/* Loading progress bar */
		float startX = box2_x + dx + dx/2 , startY = y + dx + dx/2,
				endX = startX + box2_W - 3*dx, endY = startY + box1_H - 3 * dx;
		loadingProgress(startX, startY, endX, endY, g2);

		/* Animation box*/
		g2.setColor(Color.black);
		g2.fill(new Rectangle2D.Double(x + box1_W * 0.075, y + box1_W * 0.075, box1_W * 0.85, box1_H * 0.45));
		animateLoading((int) (x + (box1_W * 3) / 44), (int) (y + (box1_W * 3) / 44));

		/* Box border */
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.white);
		g2.draw(new Rectangle2D.Double(x - 1, y - 1, box1_W + 2, box1_H + 2));
		g2.setColor(new Color(255, 255, 255, 222));

		g2.draw(new Rectangle2D.Double(box2_x - 1, y - 1, box2_W + 1, box1_H + 1));

		timercount++;
	}

	public void gameOver(final Screen screen, boolean levelFinished, double lives){
		if(levelFinished){
			if(lives == 0){
				backBufferGraphics.setColor(animateColor(new Color(0, 0, 0, 0), Color.black, 3000, endTimer));
				backBufferGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

				backBufferGraphics.setFont(fontBig);
				backBufferGraphics.setColor(Color.red);
				backBufferGraphics.drawString("Game Over", screen.getWidth() / 2 - fontBigMetrics.stringWidth("Game Over") / 2, screen.getHeight() / 2);
			}
			else {
				backBufferGraphics.setFont(fontBig);
				backBufferGraphics.setColor(Color.white);
				backBufferGraphics.drawString("Stage Clear", screen.getWidth() / 2 - fontBigMetrics.stringWidth("Stage Clear") / 2, screen.getHeight() / 2);
			}
		}
	}

	public void changeGhostColor(boolean levelFinished, double lives){
		if(levelFinished && lives == 0) {
			int ghostColorValue;
			if (225 < (255 - ((int)(System.currentTimeMillis() - ghostTImer) / 10)))
				ghostColorValue = 225;
			else if (0 < (255 - ((int)(System.currentTimeMillis() - ghostTImer) / 10)))
				ghostColorValue = (255 - ((int)(System.currentTimeMillis() - ghostTImer) / 10));
			else
				ghostColorValue = 0;
			ghostColor = new Color(ghostColorValue, ghostColorValue, ghostColorValue, 0);
			//backBufferGraphics.setColor(ghostColor);
		}
	}
	public void drawGhost(Ship ship, boolean levelFinished, double lives){
		if(levelFinished && lives == 0) {
			boolean timer = (System.currentTimeMillis() - ghostTImer) % 2 == 0;
			backBufferGraphics.setColor(ghostColor);
			if(timer){
				if(System.currentTimeMillis() - ghostTImer < 1000)
					this.drawEntity(SpriteType.Ghost, ghostPostionX--, ghostPostionY--, 2, 2, Color.white);
				else if (System.currentTimeMillis() - ghostTImer < 2000)
					this.drawEntity(SpriteType.Ghost, ghostPostionX++, ghostPostionY--, 2, 2, Color.white);
				else
					this.drawEntity(SpriteType.Ghost, ghostPostionX--, ghostPostionY--, 2, 2, Color.white);
			}
			else {
				if(System.currentTimeMillis() - ghostTImer < 1000)
					this.drawEntity(SpriteType.Ghost, ghostPostionX, ghostPostionY, 2, 2, Color.white);
				else if (System.currentTimeMillis() - ghostTImer < 2000)
					this.drawEntity(SpriteType.Ghost, ghostPostionX, ghostPostionY, 2, 2, Color.white);
				else
					this.drawEntity(SpriteType.Ghost, ghostPostionX, ghostPostionY, 2, 2, Color.white);
			}
		}
	}

	/**
	 * Creates an animation of monster.
	 * [Clean Code Team] This method was created by dodo_kdy.
	 *
	 * @param x
	 * @param y
	 */
	public void animateLoading(int x, int y){
			int y1 = y+7, x1 = x;
			if ( (30 <timercount && timercount<50) || (110 <timercount && timercount<130) ) y1 -=5;
			else if (70<timercount && timercount <90) x1+=5;

			this.drawEntity(SpriteType.values()[12],x1+15,y1+10,2.3,2.3, Color.white);
			this.drawEntity(SpriteType.values()[14],x1+60,y1+10,2.4,2.4, Color.white);
			this.drawEntity(SpriteType.values()[18],x1+100,y1+10,3,2.4, Color.white);
			this.drawEntity(SpriteType.values()[25],x1+145,y1+13,2,2, Color.white);
		}


	/**
	 * Draws basic gradient background that animates between colors.
	 * [Clean Code Team] This method was created by alicek0.
	 * @param screen
	 * @param separationLineHeight
	 * @param lives
	 */
	public void drawBackground(final Screen screen, int separationLineHeight, int lives){
		int height = screen.getHeight();
		int width = screen.getWidth();

		if (bgTimer.checkFinished()){
			brightness+= lighter;
			if (brightness >= 70) lighter *= -1;
			else if (brightness <= 0) lighter *= -1;
			bgTimer.reset();
		}

		Graphics2D g2 = (Graphics2D)backBufferGraphics;
		GradientPaint gp = new GradientPaint(0, separationLineHeight, new Color(31, 0, 0, 216), 0, height, new Color(brightness,brightness/2,100+brightness/2,230));
		g2.setPaint(gp);
		g2.fill(new Rectangle(0, separationLineHeight, width, height));

		if (lives <= 3) {
			backBufferGraphics.setColor(new Color(10, 0,  0, 200 - (lives * 50)));
			backBufferGraphics.fillRect(0, separationLineHeight, width, height);
		}
	}

	/**
	 * Draws background that fades from white to black at game start.
	 * [Clean Code Team] This method was created by alicek0.
	 * @param screen
	 * 			Screen to draw on. Used for dimensions.
	 * @param separationLineHeight
	 * 			To determine where the background should start.
	 */
	public void drawBackgroundStart(final Screen screen, int separationLineHeight){
		int height = screen.getHeight();
		int width = screen.getWidth();
		backBufferGraphics.setColor(animateColor(Color.white, new Color(0, 0, 0, 0), 3000, bgTimer_init));
		backBufferGraphics.fillRect(0, separationLineHeight, width, height);
	}

	/**
	 * Draws transparent red background that increases in opacity when Special Enemy appears.
	 * [Clean Code Team] This method was created by alicek0.
	 * @param screen
	 * @param separationLineHeight
	 */
	public void drawBackgroundSpecialEnemy(final Screen screen, int separationLineHeight){
		int height = screen.getHeight();
		int width = screen.getWidth();
		backBufferGraphics.setColor(new Color(50, 50, 0, brightness));
		backBufferGraphics.fillRect(0, separationLineHeight, width, height);
	}



	/**
	 *	Returns color between two colors over duration. Used to animate color.
	 * [Clean Code Team] This method was created by alicek0.
	 * @param color1
	 * @param color2
	 * @param duration
	 * 			How long it should take to go from c1 to c2 in milliseconds.
	 * @return
	 */
	public Color animateColor(Color color1, Color color2, int duration, Cooldown timer){
		int red, green, blue, alpha;
		float ratio = (float)timer.timePassed()/duration;
		if (ratio >=1) ratio = 1;
		red = (int)((float)color1.getRed() * (1-ratio) + (float)color2.getRed() * ratio);
		green = (int)((float)color1.getGreen() * (1-ratio) + (float)color2.getGreen() * ratio);
		blue = (int)((float)color1.getBlue() * (1-ratio) + (float)color2.getBlue() * ratio);
		alpha = (int)((float)color1.getAlpha() * (1-ratio) + (float)color2.getAlpha() * ratio);
		return new Color(red, green, blue, alpha);
	}

	/**
	 * Draws green glow behind player sprite.
	 * [Clean Code Team] This method was created by alicek0.
	 * @param screen
	 * @param separationLineHeight
	 * @param playerX
	 * @param playerY
	 * @param playerWidth
	 * @param playerHeight
	 */
	public void drawBackgroundPlayer(final Screen screen, int separationLineHeight, int playerX, int playerY, int playerWidth, int playerHeight){
		Point2D center = new Point2D.Float(playerX + playerWidth/2, playerY + playerHeight/2);
		float radius = 90;
		float[] dist = {0.0f, 0.2f, 1.0f};
		Color[] colors = {new Color(178,245,149,brightness), new Color(178,245,149,brightness+20), new Color(0,0,0,0)};
		RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
		Graphics2D g2 = (Graphics2D) backBufferGraphics;
		g2.setPaint(p);
		g2.fillRect(0, separationLineHeight, screen.getWidth(), screen.getHeight());
	}

	/**
	 * Draws background lines.
	 * [Clean Code Team] This method was created by alicek0.
	 * @param screen
	 * @param separationLineHeight
	 */
	public void drawBackgroundLines(final Screen screen, int separationLineHeight){

		int xPaddingTop = 140;
		int lineCount = 30;
		int xPaddingBottom = -500;

		// Vertical Lines
		backBufferGraphics.setColor(new Color(255,255,255,70));
		Graphics2D gr2 = (Graphics2D)backBufferGraphics;
		Stroke defaultStroke=gr2.getStroke();
		gr2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,  BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f,9.0f,3.0f,9.0f},10.0f));
		for (int i = 0; i<lineCount; i++){
			gr2.drawLine(xPaddingTop + (screen.getWidth()-xPaddingTop-xPaddingTop)/(lineCount-1) * i , separationLineHeight, xPaddingBottom + (screen.getWidth()-xPaddingBottom-xPaddingBottom)/(lineCount-1) * i, screen.getHeight());
		}

		// Gradient to fade top part of vertical lines.
		GradientPaint gp = new GradientPaint(0, separationLineHeight, new Color(10, 0, 0, 255), 0, screen.getHeight(), new Color(255,255,255,0));
		gr2.setPaint(gp);
		gr2.fillRect(0, separationLineHeight, screen.getWidth(), screen.getHeight());

		// Horizontal Lines
		gr2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,  BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f, 5.0f, 5.0f, 5.0f},10.0f));

		backBufferGraphics.setColor(new Color(255,255,255,70));
		for (int i = 0; i<lineCount; i++){
			int max_opacity = 130;
			int opacity = (i*10<=max_opacity?i*10:max_opacity);
			backBufferGraphics.setColor(new Color(255,255,255,opacity));
			backBufferGraphics.drawLine(0, separationLineHeight+lineConstant+i*30, screen.getWidth(), separationLineHeight+lineConstant+i*30);
		}

		((Graphics2D) backBufferGraphics).setStroke(defaultStroke);

		if (bgTimer_lines.checkFinished()) {
			lineConstant++;
			bgTimer_lines.reset();
		}
		if (lineConstant >= 30) lineConstant = 0;
	}

	/**
	 * Resets background timer.
	 * [Clean Code Team] This method was created by alicek0.
	 */
	public void initBackgroundTimer(final Screen screen, int separationLineHeight){
		bgTimer_init.reset();
		bgTimer_lines.reset();
	}

	public void ComboCount(final Screen screen, final int ComboCount) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		String text = String.format("%d", ComboCount)+" Combo" ;
		backBufferGraphics.drawString(text, screen.getWidth() - 90, 80);
	}
}