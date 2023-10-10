package entity;

import engine.DrawManager;

import java.awt.Color;

import engine.DrawManager.SpriteType;

public class Item extends Entity {

    /** Movement of the Item for each unit of time. */
    private final int speed = -2;

    /**
     * Constructor, establishes the Item's properties.
     * and Set sprite dot image which can find what Item it ts.
     *
     * @param positionX
     *            Initial position of the Item in the X axis.
     * @param positionY
     *            Initial position of the Item in the Y axis.
     */
    public Item(final int positionX, final int positionY){
        super(positionX, positionY, 9 * 2, 11 * 2, Color.BLUE);
        setSprite();
    }

    /**
     * Set Sprite dot image.
     */
    public final void setSprite() {
        this.spriteType = SpriteType.Item1;
    }

    /**
     * Updates status of the ship.
     */
    public final void update() {
        this.positionY += this.speed;
    }
}
