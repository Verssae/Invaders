package entity;

import engine.DrawManager.*;

import java.awt.Color;

public class Bomb extends Entity {


    private final int INIT_BOMB_COUNT = 10;
    /** speed of the bomb. */
    private int speed;
    /** damage to the bomb of ship. */
    private int attackDamage;
    /** the number of the bomb */
    private int count;

    /**
     * Constructor, establishes the bomb's properties.
     *
     * @param positionX
     *            Initial position of the bomb in the X axis.
     * @param positionY
     *            Initial position of the bomb in the Y axis.
     * @param speed
     *            Speed of the bomb.
     * @param attackDamage
     *            Enhanced Damage to Attack. (on EnhanceScreen)
     */

    public Bomb(final int positionX, final int positionY, final int speed, final int attackDamage) {
        super(positionX, positionY,5*2,5*2, Color.WHITE);
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.count = INIT_BOMB_COUNT;
        setSprite();
    }

    private final void setSprite() {
        this.spriteType = SpriteType.Bomb;
    }

    public final void update() {
        this.positionY += this.speed;
    }

    public final int getSpeed() {
        return this.speed;
    }

    public final int getAttackDamage() {
        return this.attackDamage;
    }

    public final int getCount() {
        return this.count;
    }


}
