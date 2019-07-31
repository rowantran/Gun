package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Door extends Entity {

    private Hitboxes hitbox;
    private int direction;
    private Vector2 originalPosition;

    public Door(Vector2 position, Vector2 size, int direction) {
        super(position, size);
        originalPosition = position.cpy();

        this.direction = direction;

        hitbox = new Hitboxes(position);

        RectangularHitbox box = new RectangularHitbox(position, size);
        RectangularHitbox box2 = new RectangularHitbox(position, size);

        switch(direction) {
            case 1:
                box.setPosition(new Vector2(position.x, position.y + 64));
                break;
            case 2:
                box.setPosition(new Vector2(position.x, position.y - 16));
                box2.setPosition(new Vector2(position.x, position.y + 48));
                break;
            case 3:
                box.setPosition(new Vector2(position.x - 16, position.y));
                box2.setPosition(new Vector2(position.x + 48, position.y));
                break;
            case 4:
                box.setPosition(new Vector2(position.x + 64, position.y));
                break;
            default:
                Gdx.app.log("Door", "Illegal direction found");
        }

        hitbox.addHitbox("main", box);
        hitbox.addHitbox("off", box2);

        hitbox.generateCorrectOffsets();
        hitbox.setActive(true);
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
