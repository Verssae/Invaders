package entity;

import engine.DrawManager;

import java.awt.*;

public class BossBeam extends Entity{

    public BossBeam(final int Position_X, final int Position_Y) {
        super(Position_X, Position_Y, 32*2, 383*2, Color.YELLOW);
        setSprite();
    }

    public void move(final int positionX) {
        this.setPositionX(this.positionX = positionX);
    }

    public void setSprite() {
        this.spriteType = DrawManager.SpriteType.BossBeam;
    }
}
