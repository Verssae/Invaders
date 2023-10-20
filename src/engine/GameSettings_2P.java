package engine;

/**
 * Implements an object that stores a single game's difficulty settings.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class GameSettings_2P {

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
    private int baseAreaDamage_1p;
    private int baseAreaDamage_2p;

    /** Damage of Attack. */
    private int baseAttackDamage_1p;
    private int baseAttackDamage_2p;

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
     * @param baseAreaDamage_1p
     *            Area Damage of the bullet of 1p_ship.
     * @param baseAreaDamage_2p
     *            Area Damage of the bullet of 2p_ship.
     * @param baseAttackDamage_1p
     * 			  Attack Damage of the bullet of 1p_ship.
     * @param baseAttackDamage_2p
     *            Attack Damage of the bullet of 2p_ship.
     */
    public GameSettings_2P(final int formationWidth, final int formationHeight,
                            final int baseSpeed, final int shootingFrecuency, final int difficulty,
                            final int baseAreaDamage_1p, final int baseAreaDamage_2p,
                            final int baseAttackDamage_1p, final int baseAttackDamage_2p) {
        this.formationWidth = formationWidth;
        this.formationHeight = formationHeight;
        this.baseSpeed = baseSpeed;
        this.shootingFrecuency = shootingFrecuency;
        this.difficulty = difficulty;
        this.isBossStage = false;
        this.baseAreaDamage_1p = baseAreaDamage_1p;
        this.baseAreaDamage_2p = baseAreaDamage_2p;
        this.baseAttackDamage_1p = baseAttackDamage_1p;
        this.baseAttackDamage_2p = baseAttackDamage_2p;
    }
    /**
     * Constructor, boss stage.
     *
     * @param baseSpeed
     *            Speed of the boss.
     * @param shootingFrecuency
     *            Frecuency of enemy shootings, +/- 30%.
     * @param difficulty
     * 			  Difficulty of Game, 0: EASY 1: NORMAL 2: HARD 3: HARDCORE(DEATHMATCH)
     */
    public GameSettings_2P(final int baseSpeed, final int shootingFrecuency, final int difficulty,
                           final int baseAreaDamage_1p, final int baseAreaDamage_2p,
                           final int baseAttackDamage_1p, final int baseAttackDamage_2p) {
        this.formationWidth = 1;
        this.formationHeight = 1;
        this.baseSpeed = baseSpeed;
        this.shootingFrecuency = shootingFrecuency;
        this.difficulty = difficulty;
        this.isBossStage = true;
        this.baseAreaDamage_1p = baseAreaDamage_1p;
        this.baseAreaDamage_2p = baseAreaDamage_2p;
        this.baseAttackDamage_1p = baseAttackDamage_1p;
        this.baseAttackDamage_2p = baseAttackDamage_2p;
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
     * @return the Enhanced Attack Damage of player 1
     */
    public final int getBaseAttackDamage_1p(){
        return this.baseAttackDamage_1p;
    }

    /**
     * @return the Enhanced Attack Damage of player 2
     */
    public final int getBaseAttackDamage_2p(){
        return this.baseAttackDamage_2p;
    }

    /**
     * @return the Enhanced Area Damage of player 1
     */
    public final int getBaseAreaDamage_1p() { return this.baseAreaDamage_1p; }

    /**
     * @return the Enhanced Area Damage of player 2
     */
    public final int getBaseAreaDamage_2p() { return this.baseAreaDamage_2p; }

    /**
     * Set Attack Damage of player 1
     */
    public final void setAttackDamage_1p(int attackDamage) {
        this.baseAttackDamage_1p = attackDamage;
    }

    /**
     * Set Attack Damage of player 2
     */
    public final void setAttackDamage_2p(int attackDamage) {
        this.baseAttackDamage_2p = attackDamage;
    }

    /**
     * Set Area Damage
     */
    public final void setAreaDamage_1p(int areaDamage) {
        this.baseAreaDamage_1p = areaDamage;
    }

    /**
     * Set Area Damage
     */
    public final void setAreaDamage_2p(int areaDamage) {
        this.baseAreaDamage_2p = areaDamage;
    }
}