package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.upa.gun.enemy.UnrecognizedHitboxTypeException;

public class CrateTop extends Entity {

    private Hitbox hitbox;

    public float x;
    public float y;

    Sprite crateTopSprite;

    CrateTop(float x, float y) {

        super(x, y, 64, 64, 0, -27); //width and height are dimensions of Assets.crateTop, offset is height of Assets.crateSide

        crateTopSprite = new Sprite(Assets.crateTop);
        crateTopSprite.setScale(1);

        this.x = x;
        this.y = y;

        try {
            createHitbox("rectangular", 64, 64);
        } catch(UnrecognizedHitboxTypeException e) {
            //do nothing I guess
        }
    }

    public Hitbox getHitbox() { return hitbox; }

    private void createHitbox(String hitboxType, int width, int height) throws UnrecognizedHitboxTypeException {
        if (hitboxType.equals("rectangular")) {
            hitbox = new RectangularHitbox(getPosition().x, getPosition().y-27, width, height);
        } else {
            throw new UnrecognizedHitboxTypeException(hitboxType);
        }
    }
}
