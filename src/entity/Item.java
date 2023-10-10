package entity;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;

import java.awt.Color;
import java.util.Random;
import java.util.random.RandomGenerator;

import engine.DrawManager.SpriteType;

public class Item extends Entity {

    /** Movement of the Item for each unit of time. */
    private final int speed = 12;
    private Cooldown floatingCool;
    public Cooldown updateCool;
    public int item_dx;
    public int item_dy;

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
        item_dx = (int)Math.random()*10 > 5 ? 1 : -1;
        item_dy = 1;
        this.floatingCool = Core.getCooldown(40000);
        this.updateCool = Core.getCooldown(250);
        this.floatingCool.reset();
        this.updateCool.reset();
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
    public final void update(int dx, int dy) {
        this.updateCool.reset();
        positionX += this.speed * dx;
        positionY += this.speed * dy;
    }


    /**
     * check is item floating time end.
     * @return temp.checkFinished();
     */
    public final boolean isFloatingEnd(){return floatingCool.checkFinished();}

    /**
     * when reuse item, reset floating cooltime.
     */
    public final void CoolReset(){this.floatingCool.reset();}

    /**
     * return item speed.
     * @return item.speed
     */
    public final int getSpeed(){return this.speed;}

    private boolean eatItem = false;

    /**
     * check Eat Item
     */
    public void checkEatItem() {
        eatItem = true;
    }

}
