package entity;

import engine.DrawManager;

import java.awt.Color;

import engine.DrawManager.SpriteType;

public class Item extends Entity {

    private final int speed = 2;

    public Item(final int positionX, final int positionY){
        super(positionX, positionY, 9 * 2, 11 * 2, Color.BLUE);
        setSprite();
    }

    public final void setSprite() {

    }

    public final void update() {
        this.positionY += this.speed;
    }
}
