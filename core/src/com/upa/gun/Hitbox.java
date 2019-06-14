package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

/**
 * Individual hitboxes that make up the group of hitboxes for one object
 */
public abstract class Hitbox implements Collidable {
    private boolean active;
    private Vector2 position;
    private Vector2 offset;

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

    public void adjustPosition(float adjustX, float adjustY) {
        position.set(new Vector2(position.x + adjustX, position.y + adjustY));
    }

    public void fixPosition(Vector2 realPosition) {
        position.set(new Vector2(realPosition.x + offset.x, realPosition.y + offset.y));
    }

    public void setOffset(Vector2 offset) {
        this.offset.set(offset);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract boolean colliding(Collidable other);

    public abstract boolean colliding(RectangularHitbox other);

}