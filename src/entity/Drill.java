package entity;

import engine.DrawManager;

import java.awt.*;

public class Drill extends Entity{

    /** speed of the bomb. */
    private int speed;
    /**
     * Constructor, establishes the bomb's properties.
     *
     * @param positionX
     *            Initial position of the bomb in the X axis.
     * @param positionY
     *            Initial position of the bomb in the Y axis.
     * @param speed
     *            Speed of the bomb.
     */

    public Drill(final int positionX, final int positionY, final int speed) {
        super(positionX, positionY,2*2,10*2, Color.WHITE);
        this.speed = speed;
        setSprite();
    }

    public final void setSprite() {
        this.spriteType = DrawManager.SpriteType.Drill;
    }

    public final void update() {
        this.positionY += this.speed;
    }

}
