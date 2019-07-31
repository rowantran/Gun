package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Door extends Entity {

    private Hitboxes hitbox;
    private int direction;
    private Vector2 originalPosition;
    private boolean displaySide;

    Sprite doorSideSprite;
    Sprite doorTopSprite;

    public Door(Vector2 position, Vector2 size, int direction) {
        super(position, size);
        originalPosition = position.cpy();

        this.direction = direction;

        doorSideSprite = new Sprite(Assets.doorSide);
        doorTopSprite = new Sprite(Assets.doorTop);
        doorSideSprite.setScale(1);
        doorTopSprite.setScale(1);

        hitbox = new Hitboxes(position);

        RectangularHitbox box = new RectangularHitbox(position, size);
        RectangularHitbox box2 = new RectangularHitbox(position, size);

        switch(direction) {
            case 1:
                box.setPosition(new Vector2(position.x, position.y + 64));
                box2.setPosition(new Vector2(position.x, position.y + 48));
                displaySide = true;
                break;
            case 2:
                box.setPosition(new Vector2(position.x, position.y - 16));
                displaySide = true;
                break;
            case 3:
                box.setPosition(new Vector2(position.x - 16, position.y));
                displaySide = false;
                doorTopSprite.rotate(90f);
                break;
            case 4:
                box.setPosition(new Vector2(position.x + 64, position.y));
                box2.setPosition(new Vector2(position.x + 48, position.y));
                displaySide = false;
                doorTopSprite.rotate(90f);
                break;
            default:
                Gdx.app.log("Door", "Illegal direction found");
        }

        hitbox.addHitbox("main", box);
        hitbox.addHitbox("off", box2);

        hitbox.generateCorrectOffsets();
        hitbox.setActive(true);
    }

    public boolean getDisplaySide() {
        return displaySide;
    }

    public int getDirection() {
        return direction;
    }

    public Hitboxes getHitbox() {
        return hitbox;
    }

    public void resetPosition() {
        setPosition(originalPosition);
        hitbox.setPosition(originalPosition);
    }

}
