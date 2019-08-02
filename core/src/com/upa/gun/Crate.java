package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for all crates
 * Crates are impassable terrain elements that block projectiles
 */
public class Crate extends Terrain {

    Sprite crateSideSprite;
    Sprite crateTopSprite;

    public Crate(Vector2 position) {

        super(position);

        crateSideSprite = new Sprite(Assets.crateSide);
        crateTopSprite = new Sprite(Assets.crateTop);
        crateSideSprite.setScale(1);
        crateTopSprite.setScale(1);

        RectangularHitbox leftEdge = new RectangularHitbox(new Vector2(position.x, position.y+1), new Vector2(16f, 62f));
        RectangularHitbox rightEdge = new RectangularHitbox(new Vector2(position.x + getSize().x-16, position.y+1), new Vector2(16f, 62f));
        RectangularHitbox topEdge = new RectangularHitbox(new Vector2(position.x+1, position.y + getSize().y-16), new Vector2(62f, 16f));
        RectangularHitbox botEdge = new RectangularHitbox(new Vector2(position.x+1, position.y), new Vector2(62f, 16f));

        hitbox.addHitbox("leftEdge", leftEdge);
        hitbox.addHitbox("rightEdge", rightEdge);
        hitbox.addHitbox("topEdge", topEdge);
        hitbox.addHitbox("botEdge", botEdge);

        hitbox.generateCorrectOffsets();
        hitbox.setActive(true);
    }
}
