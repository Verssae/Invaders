package engine;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import entity.Bullet;
import entity.EnhanceStone;

public class EnhanceManager {
    private EnhanceStone enhanceStone;
    /** Current Number of Enhancement Area&Damage Stone */
    private int numEnhanceStoneArea;
    private int numEnhanceStoneAttack;

    private int lvEnhanceArea;
    private int lvEnhanceAttack;

    private int attackDamage;
    private int areaDamage;

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

    public void enhanceAttackDamage() {
        if (numEnhanceStoneAttack >= 1) {
            this.attackDamage += enhanceStone.getValEnhanceAttack();
            this.numEnhanceStoneAttack -= 1;
            this.lvEnhanceAttack += 1;
        }
    }

    public void enhanceAreaDamage() {
        if (numEnhanceStoneArea >= 1) {
            this.areaDamage += enhanceStone.getValEnhanceArea();
            this.numEnhanceStoneArea -= 1;
            this.lvEnhanceArea += 1;
        }
    }

    public int getNumEnhanceStoneArea() {
        return this.numEnhanceStoneArea;
    }

    public int getNumEnhanceStoneAttack() {
        return this.numEnhanceStoneAttack;
    }

    public int getlvEnhanceStoneArea() {
        return this.lvEnhanceArea;
    }

    public int getlvEnhanceStoneAttack() {
        return this.lvEnhanceAttack;
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public int getAreaDamage() {
        return this.areaDamage;
    }
}
