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

    /** Singleton enhanceAreaStone instance of the class. */
    private EnhanceStone enhanceAreaStone;
    /** Singleton enhanceAttackStone instance of the class. */
    private EnhanceStone enhanceAttackStone;
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
        this.enhanceAreaStone = new EnhanceStone(0, 0);
        this.enhanceAttackStone = new EnhanceStone(0, 0);
        
        this.enhanceAreaStone.setColor(Color.BLUE);
        this.enhanceAttackStone.setColor(Color.magenta);

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
            this.attackDamage += enhanceAttackStone.getValEnhanceAttack();
            this.numEnhanceStoneAttack -= 1;
            this.lvEnhanceAttack += 1;
        }
    }
    
    /**
     * Enhance area damage using Enhance stone.
     */
    public void enhanceAreaDamage() {
        if (numEnhanceStoneArea >= 1) {
            this.areaDamage += enhanceAreaStone.getValEnhanceArea();
            this.numEnhanceStoneArea -= 1;
            this.lvEnhanceArea += 1;
        }
    }

    /**
     * Return number of enhanced stone (attack).
     */
    public EnhanceStone getEnhanceAttackStone() {
        return this.enhanceAttackStone;
    }
    
    /**
     * Return number of enhanced stone (area).
     */
    public EnhanceStone getEnhanceAreaStone() {
        return this.enhanceAreaStone;
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
