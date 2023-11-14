package entity;

import java.awt.Color;
import java.util.Set;
import java.util.logging.Logger;

import effect.BulletEffect;
import engine.DrawManager.SpriteType;

/**
 * Implements a bullet that moves vertically up or down.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class SpecialBullet extends Entity {

    /**
     * Speed of the bullet, positive or negative depending on direction -
     * positive is down.
     */
    private int speed;
    private BulletEffect bulletEffect;
    private int effectBullet;
    /** Damage of the bullet of Special ship. */
    private int damage;
    /** Type of the bullet of Special ship. */
    private final int type;
    /** code of EMP Emergency Escape */
    private int EmerCode;
    /** The number of code */
    private int count;

    /** Activation of the Special bullet. */
    private boolean activate;
    /**
     * Constructor, establishes the bullet's properties.
     *
     * @param positionX
     *            Initial position of the bullet in the X axis.
     * @param positionY
     *            Initial position of the bullet in the Y axis.
     * @param speed
     *            Speed of the bullet, positive or negative depending on
     *            direction - positive is down.
     * @param attackDamage
     *            Enhanced Damage of Attack. (on EnhanceScreen)
     */
    public SpecialBullet(final int positionX, final int positionY, final int speed
            , final int attackDamage, final Color color, final int type) {
        super(positionX, positionY, 3 * 2, 5 * 2, color);
        this.speed = speed;
        this.effectBullet = 0;
        this.damage = attackDamage;
        this.type = type;
        this.activate = false;
        setSprite();
    }
    /**
     * Sets correct sprite for the bullet, based on speed.
     */
    public final void setSprite() {
        this.spriteType = SpriteType.EnemyBullet;
    }

    public final void setActivate() {
        if (this.type == 0) {
            this.width = 11;
            this.height = 8;
            this.spriteType = SpriteType.Blaze_1;
            setColor(Color.RED);
            this.activate = true;
        }
        else {
            this.spriteType = null;
            this.count = 4;
            this.EmerCode = (int)(Math.random()*9);
            this.count--;
            this.activate = true;
        }
    }

    public final void ChangePos(int x, int y) {
        this.positionX = x;
        this.positionY = y;
        this.width = 88;
    }

    public boolean getActivate() {
        return this.activate;
    }

    /**
     * Sets sprite for the enemy bullet, left or right.
     */
    public final void setSprite(SpriteType bulletType) {
        this.spriteType = bulletType;
    }

    /**
     * Sets damage of attack.
     */
    public final void setDamage(final int attackDamage) {
        this.damage = attackDamage;
    }


    /**
     * Updates the bullet's position.
     */
    public final void update() {
        this.positionY += this.speed;
    }

    /**
     * Updates the bullet's color.
     */
    public final void update_sprite() {
        if (this.spriteType == SpriteType.Blaze_1)
            this.spriteType = SpriteType.Blaze_2;
        else
            this.spriteType = SpriteType.Blaze_1;
    }

    public final boolean CountDown() {
        if (this.count > 0) {
            int last = this.EmerCode;
            while (last == this.EmerCode)
                this.EmerCode = (int)(Math.random() * 9);
            this.count--;
            return true;
        }
        else
            return false;
    }

    public int getEmerCode() {
        return this.EmerCode;
    }


    /**
     * Setter of the speed of the bullet.
     *
     * @param speed
     *            New speed of the bullet.
     */
    public final void setSpeed(final int speed) {
        this.speed = speed;
    }

    /**
     * Getter for the speed of the bullet.
     *
     * @return Speed of the bullet.
     */
    public final int getSpeed() {
        return this.speed;
    }

    public final void splash(Set<Bullet> bullets) {
        bulletEffect.splashEffect(bullets);
    }

    public final int getDamage() { return this.damage; }
    public final int getType() { return this.type; }
    public final int isEffectBullet() {return this.effectBullet; }
    public void setEffectBullet(int n) {this.effectBullet = n;}
}
