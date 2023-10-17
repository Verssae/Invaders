package entity;

import engine.Cooldown;
import engine.Core;
import java.awt.Color;
import engine.DrawManager.SpriteType;

public class Item extends Entity {
    /**
     * Movement of the Item for each unit of time.
     */
    private final int speed = 2;
    /**
     * Living-Time of Item
     */
    private final Cooldown livingTime = Core.getCooldown(20000);
    /**
     * Movement direction X of item for each unit of time.
     */
    public int item_dx;
    /**
     * Movement direction Y of item for each unit of time.
     */
    public int item_dy;

    private boolean isdetroyed;

    /**
     * Constructor, establishes the Item's properties.
     * and Set sprite dot image which can find what Item it ts.
     *
     * @param positionX Initial position of the Item in the X axis.
     * @param positionY Initial position of the Item in the Y axis.
     */
    public Item(final int positionX, final int positionY) {
        super(positionX, positionY, 9 * 2, 9 * 2, Color.green);
        this.livingTime.reset();
        item_dx = Math.random() > 0.5 ? 1 : -1;
        item_dy = Math.random() > 0.5 ? 1 : -1;
        this.setSprite();
        isdetroyed = false;
    }

    /**
     * Set Sprite dot image.
     */
    public void setSprite() {
        int type = (int) (Math.random() * 10);
        switch (type) {
            case 1:
                this.spriteType = SpriteType.Buff_Item;
                this.setColor(Color.GREEN);
                break;
            case 2, 3:
                this.spriteType = SpriteType.Debuff_Item;
                this.setColor(Color.darkGray);
                break;
            case 4, 5:
                this.spriteType = SpriteType.EnhanceStone;
                this.setColor(Color.pink);
                break;
            default:
                this.spriteType = SpriteType.Coin;
                this.setColor(Color.yellow);
                break;
        }
    }

    /**
     * update item in screen
     *
     * @param width         width of GameScreen
     * @param height        height of GameScreen
     * @param SEPERATE_LINE height of SEPERATE_LINE
     */
    public final void update(final int width, final int height, final int SEPERATE_LINE) {
        boolean isRightBorder = (this.getWidth() + this.getPositionX()) > width;
        boolean isLeftBorder = (this.getPositionX()) < 0;
        boolean isTopBorder = (this.getPositionY()) < SEPERATE_LINE;
        boolean isBottomBorder = (this.getHeight() + this.getPositionY()) > height;


        if (isRightBorder || isLeftBorder) {
            // 왼쪽 또는 오른쪽 경계에 부딪혔을 때는 x 방향 반대로 설정
            this.item_dx = -this.item_dx;
        }

        if (isTopBorder || isBottomBorder) {
            // 위쪽 또는 아래쪽 경계에 부딪혔을 때는 y 방향 반대로 설정
            this.item_dy = -this.item_dy;
        }
        positionX += this.speed * this.item_dx;
        positionY += this.speed * this.item_dy;
    }

    /**
     * check is item livingTime end.
     *
     * @return temp.checkFinished();
     */
    public final boolean islivingTimeEnd() {
        return livingTime.checkFinished();
    }

    public final boolean isDestroyed() {
        return this.isdetroyed;
    }

    public final void setDestroy(boolean t) {
        this.isdetroyed = t;
    }


    /**
     * when reuse item, reset livingTime.
     */
    public final void CoolReset() {
        this.livingTime.reset();
    }

    /**
     * get dropped item when stage is ended.
     */

    public final int getSpeed() {
        return this.speed;
    }

    private boolean eatItem = false;

    /**
     * check Eat Item
     */
    public void checkEatItem() {
        eatItem = true;
    }


    public final void resetItem(Ship ship) {
        this.CoolReset();
        this.item_dx = ship.getPositionX() - this.positionX > 0 ? 1 : -1;
        this.item_dy = ship.getPositionY() - this.positionY > 0 ? 1 : -1;
        positionX += this.item_dx * ((int) Math.sqrt(Math.abs(ship.getPositionX() - this.positionX)) + 1);
        positionY += this.item_dy * ((int) Math.sqrt(Math.abs(ship.getPositionY() - this.positionY)) + 1);
    }
}