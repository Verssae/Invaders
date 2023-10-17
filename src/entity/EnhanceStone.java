package entity;

import java.awt.Color;

public class EnhanceStone extends Entity{
    private int valEnhanceArea;
    private int valEnhanceAttack;

    public EnhanceStone(int positionX, int positionY, int width, int height, Color color) {
        super(positionX, positionY, width, height, color);
        
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
