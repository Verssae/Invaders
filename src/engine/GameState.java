package engine;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	/** Current game level. */
	private int level;
	/** Current score. */
	private int score;
	/** Lives currently remaining. */
	private double livesRemaining;
	/** Bullets shot until now. */
	private int bulletsShot;
	/** Ships destroyed until now. */
	private int shipsDestroyed;
	/** HardCore(Only One life) */
	private boolean hardcore;



	/**
	 * Constructor.
	 * 
	 * @param level
	 *            Current game level.
	 * @param score
	 *            Current score.
	 * @param livesRemaining
	 *            Lives currently remaining.
	 * @param bulletsShot
	 *            Bullets shot until now.
	 * @param shipsDestroyed
	 *            Ships destroyed until now.
	 * @param hardcore
	 *            Hardcore mode, Only one coin.
	 */
	public GameState(final int level, final int score,
			final double livesRemaining, final int bulletsShot,
			final int shipsDestroyed, final boolean hardcore) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.hardcore = hardcore;

	}

	/**
	 * @return the level
	 */
	public final int getLevel() {
		return level;
	}

	/**
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * @return the livesRemaining
	 */
	public final double getLivesRemaining() {
		return livesRemaining;
	}

	/**
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}

	/**
	 * @return the shipsDestroyed
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * @return the hardcore
	 */
	public final boolean getHardCore() {
		return this.hardcore;
	}

	//public final int getBulletsCount() { return this.BulletsCount;}

	/**
	 * Set HardCore
	 */
	public final void setHardCore() {
		this.livesRemaining = 1;
		this.hardcore = true;
	}

	/**
	 * Set Level
	 */
	public final void setLevel(int i) {
		this.level = i;
	}
}
