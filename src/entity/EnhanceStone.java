package entity;

import engine.DrawManager;

import java.awt.Color;

public class EnhanceStone extends Item{
    private int valEnhanceArea;
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
        setSprite(5);
        this.valEnhanceArea = 1;
        this.valEnhanceAttack = 1;
    }
    public int getValEnhanceArea() {
        return this.valEnhanceArea;
    }

    public int getValEnhanceAttack() {
        return this.valEnhanceAttack;
    }

    public void setValEnhanceArea() {
        this.valEnhanceArea += 0;
    }

    public void setValEnhanceAttack() {
        this.valEnhanceAttack += 0;
    }
}
