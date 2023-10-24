package engine;

/**
 * Implements an object that stores a single game's difficulty settings.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameSettings {

	/** Width of the level's enemy formation. */
	private int formationWidth;
	/** Height of the level's enemy formation. */
	private int formationHeight;
	/** Speed of the enemies, function of the remaining number. */
	private int baseSpeed;
	/** Frequency of enemy shootings, +/- 30%. */
	private int shootingFrecuency;
	/** Difficulty of Game */

	private int difficulty;
	/** Check if the stage is boss stage. */
	private boolean isBossStage;

	/** Damage of area. */
	private int baseAreaDamage;
	/** Damage of Attack. */
	private int baseAttackDamage;

	/**
	 * Constructor.
	 * 
	 * @param formationWidth
	 *            Width of the level's enemy formation.
	 * @param formationHeight
	 *            Height of the level's enemy formation.
	 * @param baseSpeed
	 *            Speed of the enemies.
	 * @param shootingFrecuency
	 *            Frecuency of enemy shootings, +/- 30%.
	 * @param difficulty
	 * 			  Difficulty of Game, 0: EASY 1: NORMAL 2: HARD 3: HARDCORE(DEATHMATCH)
	 * @param baseAreaDamage
	 *            Area Damage of the bullet of ship.
	 * @param baseAttackDamage
	 * 			  Attack Damage of the bullet of ship.
	 */
	public GameSettings(final int formationWidth, final int formationHeight,
			final int baseSpeed, final int shootingFrecuency, final int difficulty,
			final int baseAreaDamage, final int baseAttackDamage) {
		this.formationWidth = formationWidth;
		this.formationHeight = formationHeight;
		this.baseSpeed = baseSpeed;
		this.shootingFrecuency = shootingFrecuency;
		this.difficulty = difficulty;
		this.isBossStage = false;
		this.baseAreaDamage = baseAreaDamage;
		this.baseAttackDamage = baseAttackDamage;
	}
	/**
	 * Constructor, boss stage.
	 *
	 * @param baseSpeed
	 *            Speed of the boss.
	 * @param shootingFrecuency
	 *            Frecuency of enemy shootings, +/- 30%.
	 * @param  difficulty
	 * 			  Difficulty of Game, 0: EASY 1: NORMAL 2: HARD 3: HARDCORE(DEATHMATCH)
	 */
	public GameSettings(final int baseSpeed, final int shootingFrecuency, final int difficulty,
						final int baseAreaDamage, final int baseAttackDamage) {
		this.formationWidth = 1;
		this.formationHeight = 1;
		this.baseSpeed = baseSpeed;
		this.shootingFrecuency = shootingFrecuency;
		this.difficulty = difficulty;
		this.isBossStage = true;
		this.baseAreaDamage = baseAreaDamage;
		this.baseAttackDamage = baseAttackDamage;
	}

	/**
	 * @return the formationWidth
	 */
	public final int getFormationWidth() {
		return formationWidth;
	}

	/**
	 * @return the formationHeight
	 */
	public final int getFormationHeight() {
		return formationHeight;
	}

	/**
	 * @return the baseSpeed
	 */
	public final int getBaseSpeed() {
		if (this.difficulty == 0)
			return (int)((double)this.baseSpeed * 0.5);
		else if (this.difficulty == 1)
			return this.baseSpeed;
		else
			return (int)((double)this.baseSpeed * 2);
	}

	/**
	 * @return the shootingFrecuency
	 */
	public final int getShootingFrecuency(){
		if (this.difficulty == 0)
			return (int)((double)this.shootingFrecuency * 1.5);
		else if (this.difficulty == 1)
			return this.shootingFrecuency;
		else
			return (int)((double)this.shootingFrecuency * 0.5);
	}

	/**
	 * Set difficulty
	 */
	public final void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the difficulty
	 */
	public final double getDifficulty() { return this.difficulty; }

	/**
	 * Check if the stage is boss stage
	 * @return True if the stage is boss stage
	 */
	public final boolean checkIsBoss() {return this.isBossStage; }


	/**
	 * @return the Enhanced Attack Damage
	 */	
	public final int getBaseAttackDamage(){
		return this.baseAttackDamage;
	}

	/**
	 * @return the Enhanced Area Damage
	 */	
	public final int getBaseAreaDamage(){
		return this.baseAreaDamage;
	}

	/**
	 * Set Attack Damage
	 */	
	public final void setAttackDamage(int attackDamage) {
		this.baseAttackDamage = attackDamage;
	}

	/**
	 * Set Area Damage
	 */	
	public final void setAreaDamage(int areaDamage) {
		this.baseAreaDamage = areaDamage;
	}
}