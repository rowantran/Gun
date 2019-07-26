package com.upa.gun;

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
        hitbox.addHitbox("main", box);

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
