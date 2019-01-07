package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.enemy.UnrecognizedHitboxTypeException;

public class CrateTop extends Entity {
    Sprite crateTopSprite;

    CrateTop(Vector2 position) {
        super(position, new Vector2(64, 64)); //width and height are dimensions of Assets.crateTop, offset is height of Assets.crateSide

        crateTopSprite = new Sprite(Assets.crateTop);
        crateTopSprite.setScale(1);

        try {
            createHitbox("rectangular", new Vector2(64, 64));
        } catch(UnrecognizedHitboxTypeException e) {
            //do nothing I guess
        }
    }

    private void createHitbox(String hitboxType, Vector2 size) throws UnrecognizedHitboxTypeException {
        if (hitboxType.equals("rectangular")) {
            Hitbox hitbox = new RectangularHitbox(getPosition(), size);
            hitbox.setOffset(new Vector2(0, -27)); // offset is height of Assets.crateSide
            hitbox.setPosition(getPosition()); // immediately take offset into account

            hitboxes.addHitbox("hitbox", hitbox);
        } else {
            throw new UnrecognizedHitboxTypeException(hitboxType);
        }
    }
}
