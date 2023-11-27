package entity;

import engine.DrawManager;

import java.awt.*;

public class Beam extends Entity{

    /**
     * Constructor, establishes the beam's properties.
     *
     * @param Position_X
     *            Initial position of the beam in the X axis.
     * @param Position_Y
     *            Initial position of the beam in the Y axis.
     */

    public Beam(final int Position_X, final int Position_Y) {
        super(Position_X, Position_Y, 32*2, 383*2, Color.YELLOW);
        setSprite();
    }

    /**
     * Sets correct sprite for the beam.
     */
    public void setSprite() {
        this.spriteType = DrawManager.SpriteType.Beam;
    }
}
