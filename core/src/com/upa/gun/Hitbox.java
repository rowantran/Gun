package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Hitbox implements Collidable {
    private boolean active;
    private Vector2 position;
    private Vector2 offset;

    public Hitbox() {
        this(true, new Vector2(0, 0), new Vector2(0, 0));
    }

    public Hitbox(boolean active, Vector2 position, Vector2 offset) {
        this.active = active;
        this.position = position.cpy();
        this.offset = offset.cpy();
    }

    boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
        this.position.add(offset);
    }

    public void setOffset(Vector2 offset) {
        this.offset.set(offset);
    }
}