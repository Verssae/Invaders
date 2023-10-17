package engine;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import entity.Bullet;
import entity.EnhanceStone;

/**
 * Manages Values Related to Enhance.
 */
public class EnhanceManager {

    /** Singleton enhanceStone instance of the class. */
    private EnhanceStone enhanceStone;
    /** Current Number of Enhancement Area Stone. */
    private int numEnhanceStoneArea;
    /** Current Number of Enhancement Attack Stone. */
    private int numEnhanceStoneAttack;
    /** Current Level of Enhancement Area. */
    private int lvEnhanceArea;
    /** Current Level of Enhancement Attack. */
    private int lvEnhanceAttack;
    /** Current Value of Enhancement  Area. */
    private int attackDamage;
    /** Current Value of Enhancement  Attack. */
    private int areaDamage;

    /**
	 * Constructor.
	 */
    public EnhanceManager(final int numEnhanceStoneArea, final int numEnhanceStoneAttack, 
                          final int lvEnhanceArea, final int lvEnhanceAttack) {    
        this.enhanceStone = new EnhanceStone(0, 0, 7, 7, Color.WHITE);
        this.numEnhanceStoneArea = numEnhanceStoneArea;
        this.numEnhanceStoneAttack = numEnhanceStoneAttack;

        this.lvEnhanceArea = lvEnhanceArea;
        this.lvEnhanceAttack = lvEnhanceAttack;

        this.attackDamage = 1;
        this.areaDamage = 1;
    }    

    /**
	 * Enhance attack damage using Enhance stone.
	 */
    public void enhanceAttackDamage() {
        if (numEnhanceStoneAttack >= 1) {
            this.attackDamage += enhanceStone.getValEnhanceAttack();
            this.numEnhanceStoneAttack -= 1;
            this.lvEnhanceAttack += 1;
        }
    }
    
    /**
     * Enhance area damage using Enhance stone.
     */
    public void enhanceAreaDamage() {
        if (numEnhanceStoneArea >= 1) {
            this.areaDamage += enhanceStone.getValEnhanceArea();
            this.numEnhanceStoneArea -= 1;
            this.lvEnhanceArea += 1;
        }
    }
    
    /**
     * Return number of enhanced stone (attack).
     */
    public int getNumEnhanceStoneAttack() {
        return this.numEnhanceStoneAttack;
    }
    
    /**
     * Return number of enhanced stone (area).
     */
    public int getNumEnhanceStoneArea() {
        return this.numEnhanceStoneArea;
    }
    
    /**
     * Return level of enhancement of attack.
     */
    public int getlvEnhanceAttack() {
        return this.lvEnhanceAttack;
    }
    
    /**
     * Return level of enhancement of area.
     */
    public int getlvEnhanceArea() {
        return this.lvEnhanceArea;
    }
    
    /**
     * Return damage of attack.
     */
    public int getAttackDamage() {
        return this.attackDamage;
    }
    
    /**
     * Return damage of area.
     */
    public int getAreaDamage() {
        return this.areaDamage;
    }
}
