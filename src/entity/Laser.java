package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a laser that moves vertically up or down.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class Laser extends Entity {

    /**
     * Speed of the Laser, positive or negative depending on direction -
     * positive is down.
     */
    private boolean activated;

    /**
     * Constructor, establishes the laser's properties.
     *
     * @param Position_X
     *            Initial position of the laser in the X axis.
     * @param Position_Y
     *            Initial position of the laser in the Y axis.
     * @param act
     *            Activated Laser
     */
    public Laser(final int Position_X, final int Position_Y, final boolean act) {
        super(Position_X, Position_Y, 1*2, 448, Color.RED);
        this.activated = act;
        setSprite();
    }

    /**
     * Sets correct sprite for the laser, based on speed.
     */
    public final void setSprite() {
        this.spriteType = SpriteType.Laser;
    }

    public final void launch() {
    }

    public final void Activate(final boolean act) {this.activated = act;}
    public final boolean isActivated() {
        return this.activated;
    }

}
