package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.enemy.UnrecognizedHitboxTypeException;

public class CrateTop extends Entity {

    private Hitboxes hitbox;

    public float x;
    public float y;

    Sprite crateTopSprite;

    CrateTop(Vector2 position) {
        super(position, new Vector2(64f, 64f)); //width and height are dimensions of Assets.crateTop, offset is height of Assets.crateSide

        hitbox = new Hitboxes();

        crateTopSprite = new Sprite(Assets.crateTop);
        crateTopSprite.setScale(1);

        RectangularHitbox box = new RectangularHitbox(new Vector2(position.x, position.y - 28), new Vector2(64f, 64f));
        hitbox.addHitbox("box", box);
        hitbox.setActive(true);
    }

    public Hitboxes getHitbox() { return hitbox; }

}
