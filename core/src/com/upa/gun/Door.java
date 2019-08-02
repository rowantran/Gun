package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Door extends Terrain {

    private int direction;

    Sprite doorSideSprite;
    Sprite doorTopSprite;

    public Door(Vector2 position, Vector2 size, int direction) {

        super(position, size);

        this.direction = direction;

        doorSideSprite = new Sprite(Assets.doorSide);
        doorTopSprite = new Sprite(Assets.doorTop);
        doorSideSprite.setScale(1);
        doorTopSprite.setScale(1);

        RectangularHitbox open = new RectangularHitbox(position, size);
        RectangularHitbox closed = new RectangularHitbox(position, size);

        switch(direction) {
            case 1:
                open.setPosition(new Vector2(position.x, position.y + 64));
                closed.setPosition(new Vector2(position.x, position.y + 48));
                displaySide = true;
                break;
            case 2:
                open.setPosition(new Vector2(position.x, position.y - 16));
                displaySide = true;
                break;
            case 3:
                open.setPosition(new Vector2(position.x - 16, position.y));
                displaySide = false;
                doorTopSprite.rotate(90f);
                break;
            case 4:
                open.setPosition(new Vector2(position.x + 64, position.y));
                closed.setPosition(new Vector2(position.x + 48, position.y));
                displaySide = false;
                doorTopSprite.rotate(90f);
                break;
            default:
                Gdx.app.log("Door", "Illegal direction found");
        }

        hitbox.addHitbox("open", open);
        hitbox.addHitbox("closed", closed);

        hitbox.generateCorrectOffsets();
        hitbox.setActive(true);
    }

    public int getDirection() {
        return direction;
    }
}
