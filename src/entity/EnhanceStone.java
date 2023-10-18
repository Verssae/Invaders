package entity;

import engine.DrawManager;

import java.awt.Color;

public class EnhanceStone extends Item{
    /** Current Value of Enhancement Area. */
    private int valEnhanceArea;
    /** Current Value of Enhancement Attack. */
    private int valEnhanceAttack;

    /**
     * Constructor, establishes the Item's properties.
     * and Set sprite dot image which can find what Item it ts.
     *
     * @param positionX Initial position of the Item in the X axis.
     * @param positionY Initial position of the Item in the Y axis.
     */
    public EnhanceStone(int positionX, int positionY) {
        super(positionX, positionY);
        this.valEnhanceArea = 1;
        this.valEnhanceAttack = 1;
    }
    public int getValEnhanceArea() {
        return this.valEnhanceArea;
    }

    /**
	 * Getter for the value of enhanced attack damage.
	 * 
	 * @return Attack Damage
	 */
    public int getValEnhanceAttack() {
        return this.valEnhanceAttack;
    }

	/**
	 * Setter for the value of enhanced area Damage.
	 *
	 * @param 
	 *                  how much Area Damage increases from the current area damage (valEnhanceArea).
	 */
    public void setValEnhanceArea() {
        this.valEnhanceArea += 0;
    }

	/**
	 * Setter for the value of enhanced attack Damage.
	 *
	 * @param 
	 *                  how much Attack Damage increases from the current attack damage (valEnhanceAttack).
	 */
    public void setValEnhanceAttack() {
        this.valEnhanceAttack += 0;
    }
}
