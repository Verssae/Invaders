package entity;

import java.awt.Color;

public class EnhanceStone extends Entity{
    /** Current Value of Enhancement Area. */
    private int valEnhanceArea;
    /** Current Value of Enhancement Attack. */
    private int valEnhanceAttack;

 	/**
	 * Constructor, establishes the entity's generic properties.
	 * 
	 * @param positionX
	 *                  Initial position of the entity in the X axis.
	 * @param positionY
	 *                  Initial position of the entity in the Y axis.
	 * @param width
	 *                  Width of the entity.
	 * @param height
	 *                  Height of the entity.
	 * @param color
	 *                  Color of the entity.
	 */   
    public EnhanceStone(int positionX, int positionY, int width, int height, Color color) {
        super(positionX, positionY, width, height, color);
        
        this.valEnhanceArea = 1;
        this.valEnhanceAttack = 1;
    }

	/**
	 * Getter for the value of enhanced area damage.
	 * 
	 * @return Area Damage 
	 */    
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
