package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Crate extends Entity {

    private boolean displaySide;

    Sprite crateSideSprite;
    Sprite crateTopSprite;

    private Hitboxes hitbox;

    public Crate(Vector2 position) {

        super(position, new Vector2(64f, 64f)); //width and height are dimensions of Assets.crateTop, offset is height of Assets.crateSide

        hitbox = new Hitboxes();
        displaySide = true;

        crateSideSprite = new Sprite(Assets.crateSide);
        crateSideSprite.setScale(1);
        crateTopSprite = new Sprite(Assets.crateTop);
        crateTopSprite.setScale(1);

        RectangularHitbox leftEdge = new RectangularHitbox(new Vector2(position.x, position.y), new Vector2(1f, 64f));
        RectangularHitbox rightEdge = new RectangularHitbox(new Vector2(position.x + getSize().x - 1, position.y), new Vector2(1f, 64f));
        RectangularHitbox topEdge = new RectangularHitbox(new Vector2(position.x, position.y + getSize().y-1), new Vector2(64f, 1f));
        RectangularHitbox botEdge = new RectangularHitbox(new Vector2(position.x, position.y), new Vector2(64f, 1f));

        hitbox.addHitbox("leftEdge", leftEdge);
        hitbox.addHitbox("rightEdge", rightEdge);
        hitbox.addHitbox("topEdge", topEdge);
        hitbox.addHitbox("botEdge", botEdge);
        hitbox.setActive(true);

    }

    public void displaySide() {
        displaySide = true;
    }

    public void hideSide() {
        displaySide = false;
    }

    public boolean getDisplaySide() {
        return displaySide;
    }

    public Hitboxes getHitbox() {
        return hitbox;
    }

}
