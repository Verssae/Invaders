package entity;

import engine.DrawManager;
import java.awt.Color;
import engine.DrawManager.SpriteType;

public class EnhanceStone extends Item{
    /** Current Level of Enhancement Area. */
    private int lvEnhanceArea;
    /** Current Level of Enhancement Attack. */
    private int lvEnhanceAttack;
    /** Current Value of Enhanced Area. */
    private int attackDamage;
    /** Current Value of Enhanced Attack. */
    private int areaDamage;

    /**
     * Constructor, establishes the Item's properties.
     * and Set sprite dot image which can find what Item it ts.
     *
     * @param positionX Initial position of the Item in the X axis.
     * @param positionY Initial position of the Item in the Y axis.
     */
    public EnhanceStone(final int positionX, final int positionY,
                        final int lvEnhanceArea, final int lvEnhanceAttack,
                        final int attackDamage) {
        super(positionX, positionY);

        this.lvEnhanceArea = lvEnhanceArea;
        this.lvEnhanceAttack = lvEnhanceAttack;
        this.attackDamage = attackDamage;
    }
    

    /**
     * Return level of enhancement of attack.
     * 
     * lvEnhanceAttack
     */
    public int getlvEnhanceAttack() {
        return this.lvEnhanceAttack;
    }
    
    /**
     * Return level of enhancement of area.
     * 
     * lvEnhanceArea
     */
    public int getlvEnhanceArea() {
        return this.lvEnhanceArea;
    }
    
    /**
     * Return damage of attack.
     * 
     * @return attackDamage
     */
    public int getAttackDamage() {
        return this.attackDamage;
    }
    
    /**
     * Return damage of area.
     * 
     * @return areaDamage
     */
    public int getAreaDamage() {
        return this.areaDamage;
    }    

    /**
	 * Getter for the value of enhanced attack damage.
	 * 
	 * @return Attack Damage
	 */
    public void setValEnhanceAttack(final int valEnhanceAttack) {
        this.attackDamage += valEnhanceAttack;
    }

}
