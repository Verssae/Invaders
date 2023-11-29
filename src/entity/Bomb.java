package entity;

import engine.DrawManager.*;

import java.awt.Color;

public class Bomb extends Entity {

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

    public Bomb(final int positionX, final int positionY, final int speed) {
        super(positionX, positionY,5*2,5*2, Color.WHITE);
        this.speed = speed;
        setSprite();
    }

    public final void setSprite() {
        this.spriteType = SpriteType.Bomb;
    }

    public final void update() {
        this.positionY += this.speed;
    }

    public final void setSpeed(final int speed) {
        this.speed = speed;
    }

}
