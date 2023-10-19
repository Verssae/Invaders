package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private int lvEnhanceAttack;
    private int lvEnhanceArea;
    private int attackDamage;

    ArrayList<Integer> numRequiredEnhanceAreaStoneList = new ArrayList<>(Arrays.asList(1, 3, 7));
    ArrayList<Integer> numRequiredEnhanceAttackStoneList = new ArrayList<>(Arrays.asList(1, 2, 4, 7, 10, 15));
    ArrayList<Integer> valEnhanceAttackList = new ArrayList<>(Arrays.asList(1, 1, 3, 5, 8, 12));

    /**
	 * Constructor.
	 */
    public EnhanceManager(final int numEnhanceStoneArea, final int numEnhanceStoneAttack, 
                          final int lvEnhanceArea, final int lvEnhanceAttack,
                          final int attackDamage) {    
        this.enhanceStone = new EnhanceStone(0, 0, lvEnhanceArea, lvEnhanceAttack, attackDamage);

        this.numEnhanceStoneArea = numEnhanceStoneArea;
        this.numEnhanceStoneAttack = numEnhanceStoneAttack;
        this.lvEnhanceAttack = enhanceStone.getlvEnhanceAttack();
        this.lvEnhanceArea = enhanceStone.getlvEnhanceArea();
        this.attackDamage = enhanceStone.getAttackDamage();
    }    

    /**
	 * Enhance attack damage using Enhance stone.
	 */
    public void enhanceAttackDamage() {
        int numrequiredEnhanceStone = this.getRequiredNumEnhanceStoneAttack();
        if (numEnhanceStoneAttack >= numrequiredEnhanceStone) {
            this.attackDamage += this.getValEnhanceAttack();
            this.numEnhanceStoneAttack -= numrequiredEnhanceStone;
            this.lvEnhanceAttack += 1;
        }
    }
    
    /**
     * Enhance area damage using Enhance stone.
     */
    public void enhanceAreaDamage() {
        int numRequiredEnhanceStone = this.getRequiredNumEnhanceStoneArea();
        if (numEnhanceStoneArea >= numRequiredEnhanceStone) {
            this.numEnhanceStoneArea -= numRequiredEnhanceStone;
            this.lvEnhanceArea += 1;
        }
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
     * Return Value of enhanced damage according to level
     */
    public int getValEnhanceAttack() {
        return valEnhanceAttackList.get(this.lvEnhanceAttack);
    }    

    /**
     * Return number of required Enhance-Stone-Attack
     */
    public int getRequiredNumEnhanceStoneAttack() {
        return numRequiredEnhanceAttackStoneList.get(this.lvEnhanceAttack);
    }  

    /**
     * Return number of required Enhance-Stone-Attack
     */
    public int getRequiredNumEnhanceStoneArea() {
        return numRequiredEnhanceAreaStoneList.get(this.lvEnhanceArea);
    }    

    /**
     * Set number of Blue-Enhance-Area-Stone
     */
    public void setNumBlueEnhanceAreaStone(final int number) {
        this.numEnhanceStoneArea += number;
    }  
     
    /**
     * Set number of Perple-Enhance-Area-Stone
     */
    public void setNumPerpleEnhanceAttackStone(final int number) {
        this.numEnhanceStoneAttack += number;
    }    
}
