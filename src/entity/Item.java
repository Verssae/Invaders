package entity;

import engine.Cooldown;
import engine.Core;
import java.awt.Color;
import engine.DrawManager.SpriteType;

public class Item extends Entity {

    /** Movement of the Item for each unit of time. */
    private final int speed = 3;
    /** Living-Time of Item */
    private final Cooldown livingTime = Core.getCooldown(20000);
    /** Movement direction X of item for each unit of time. */
    public int item_dx;
    /** Movement direction Y of item for each unit of time. */
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
        super(positionX, positionY, 3 * 2, 3 * 2, Color.BLUE);
        this.livingTime.reset();
        item_dx = Math.random() > 0.5 ? 1 : -1;
        item_dy = Math.random() > 0.5 ? 1 : -1;
        setSprite();
    }

    /**
     * Set Sprite dot image.
     */
    public final void setSprite() {
        this.spriteType = SpriteType.Item1;
    }

    /**
     *
     * @param width
     *          width of GameScreen
     * @param height
     *          height of GameScreen
     * @param SEPERATE_LINE
     *          height of SEPERATE_LINE
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
     * @return temp.checkFinished();
     */
    public final boolean islivingTimeEnd(){return livingTime.checkFinished();}

    /**
     * when reuse item, reset livingTime.
     */
    public final void CoolReset(){this.livingTime.reset();}

}
