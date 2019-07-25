package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public class Door extends Entity {

    private Hitboxes hitbox;
    private int direction;

    public Door(Vector2 position, Vector2 size, int direction) {
        super(position, size);

        this.direction = direction;

        hitbox = new Hitboxes();

        RectangularHitbox box = new RectangularHitbox(position, size);
        hitbox.addHitbox("main", box);
        hitbox.setActive(true);
    }

    public int getDirection() {
        return direction;
    }

    public Hitboxes getHitbox() {
        return hitbox;
    }

}
