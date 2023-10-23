package engine;

import entity.Coin;

/**
 * Implements an object that stores the state of the game between levels.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class GameState_2P {

    /** Current game level. */
    private int level;
    /** Current score. */
    private int score_1P;
    private int score_2P;
    /** Current coin. */
    private Coin coin;
    /** Lives currently remaining. */
    private double livesRemaining;
    private double livesRemaining_2p;
    /** Bullets shot until now. */
    private int bulletsShot_1P;
    private int bulletsShot_2P;
    /** Ships destroyed until now. */
    private int shipsDestroyed;
    /** HardCore(Only One life) */
    private boolean hardcore;

    private int BulletsRemaining_1p;
    private int BulletsRemaining_2p;




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
    public GameState_2P(final int level, final int score_1P, final int score_2P, final Coin coin,
                     final double livesRemaining, final int bulletsShot_1P, final int bulletShot_2P,
                     final int shipsDestroyed, final boolean hardcore,final double livesRemaining_2p, final int BulletsRemaining_1p,final int BulletsRemaining_2p) {
        this.level = level;
        this.score_1P = score_1P;
        this.score_2P = score_2P;
        this.coin = coin;
        this.livesRemaining = livesRemaining;
        this.livesRemaining_2p = livesRemaining_2p;
        this.bulletsShot_1P = bulletsShot_1P;
        this.bulletsShot_2P = bulletsShot_2P;
        this.shipsDestroyed = shipsDestroyed;
        this.hardcore = hardcore;
        this.BulletsRemaining_1p = BulletsRemaining_1p;
        this.BulletsRemaining_2p = BulletsRemaining_2p;

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
    public final int getScore_1P() {
        return score_1P;
    }
    public final int getScore_2P() {
        return score_2P;
    }

    /**
     * @return the score
     */
    public final Coin getCoin() {
        return coin;
    }

    /**
     * @return the score
     */
    public final Coin setCoin(final Coin coin) {
        return this.coin = coin;
    }

    /**
     * @return the livesRemaining
     */
    public final double getLivesRemaining() {
        return livesRemaining;
    }
    public final double getLivesRemaining_2p() {
        return livesRemaining_2p;
    }
    /**
     * @return the bulletsShot
     */
    public final int getBulletsShot_1P() {return bulletsShot_1P;
    }
    public final int getBulletsShot_2P() {
        return bulletsShot_2P;
    }

    public final int getBulletsRemaining_1p() { return BulletsRemaining_1p;}
    public final int getBulletsRemaining_2p() { return BulletsRemaining_2p;}

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

    /**
     * Set LivesRecovery
     */
    public final void setLivesRecovery() {
        if(hardcore){
            this.score_1P = getScore_1P() + 100;
            this.score_2P = getScore_2P() + 100;
            this.level = getLevel() - 1;
            this.livesRemaining = 1;
            this.livesRemaining_2p = 1;
        }
        else {
            this.score_1P = getScore_1P() + 100; // keeping score
            this.score_2P = getScore_2P() + 100;
            this.level = getLevel() - 1;
            this.livesRemaining = 3;
            this.livesRemaining_2p = 3;
        }
    }
}
