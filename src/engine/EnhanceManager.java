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
    /** Current Level of Enhanced Attack. */
    private int lvEnhanceAttack;
    /** Current Level of Enhanced Area. */
    private int lvEnhanceArea;
    /** Current Damage of Attack */
    private int attackDamage;
    
    /** The number List of Ehanced Stones required according to level. (about attackdamage) */
    ArrayList<Integer> numRequiredEnhanceAttackStoneList = new ArrayList<>(Arrays.asList(1, 2, 4, 7, 10, 15, 0));
    /** The number List of Ehanced Stones required according to level. (about area damage) */
    ArrayList<Integer> numRequiredEnhanceAreaStoneList = new ArrayList<>(Arrays.asList(1, 3, 0));
    /** The value List of Attack Damage to be enhanced according to level. */
    ArrayList<Integer> valEnhanceAttackList = new ArrayList<>(Arrays.asList(1, 1, 3, 5, 8, 12, 0));

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
     * @return lvEnhanceAttack
     */
    public int getlvEnhanceAttack() {
        return this.lvEnhanceAttack;
    }
    
    /**
     * Return level of enhancement of area.
     * 
     * @return lvEnhanceArea
     */
    public int getlvEnhanceArea() {
        return this.lvEnhanceArea;
    }
    
    
    /**
     * Return number of enhanced stone (attack).
     * 
     * @return numEnhanceStoneAttack
     */
    public int getNumEnhanceStoneAttack() {
        return this.numEnhanceStoneAttack;
    }
    
    /**
     * Return number of enhanced stone (area).
     * 
     * @return numEnhanceStoneArea
     */
    public int getNumEnhanceStoneArea() {
        return this.numEnhanceStoneArea;
    }

    /**
     * Return Value of enhanced damage according to level
     * 
     * @return valEnhanceAttack
     */
    public int getValEnhanceAttack() {
        return valEnhanceAttackList.get(this.lvEnhanceAttack);
    }    

    /**
     * Return number of required Enhance-Stone-Attack
     * 
     * @return numRequiredEnhanceAttackStone
     */
    public int getRequiredNumEnhanceStoneAttack() {
        return numRequiredEnhanceAttackStoneList.get(this.lvEnhanceAttack);
    }  

    /**
     * Return number of required Enhance-Stone-Attack
     * 
     * @return numRequiredEnhanceAreaStone
     */
    public int getRequiredNumEnhanceStoneArea() {
        return numRequiredEnhanceAreaStoneList.get(this.lvEnhanceArea);
    }    
    
    /**
     * Set number of Purple-Enhance-Attack-Stone
     */    
    public void PlusNumEnhanceStoneAttack(final int num) {
        this.numEnhanceStoneAttack += num;
    }

    /**
     * Set number of Blue-Enhance-Area-Stone
     */
    public void PlusNumEnhanceStoneArea(final int num) {
        this.numEnhanceStoneArea += num;
    }
}
