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
    private final int speed = 4;
    private final int PROPORTION_SEPERATE_LINE = 10;
    private final Cooldown floatingCool = Core.getCooldown(5000);
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
        item_dx = Math.random() > 0.5 ? 1 : -1;
        item_dy = 1;
        this.floatingCool.reset();
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
    public final void update(int width, int height) {
        boolean isRightBorder = (this.getWidth() / 2 + this.getPositionX()) > width - 2;
        boolean isLeftBorder = (this.getWidth() / 2 + this.getPositionX()) < 2;
        boolean isTopBorder = (this.getHeight() / 2 + this.getPositionY()) < height / PROPORTION_SEPERATE_LINE;
        boolean isBottomBorder = (this.getHeight() / 2 + this.getPositionY()) > height;

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
     * check is item floating time end.
     * @return temp.checkFinished();
     */
    public final boolean isFloatingEnd(){return floatingCool.checkFinished();}

    /**
     * when reuse item, reset floating cooltime.
     */
    public final void CoolReset(){this.floatingCool.reset();}
}
