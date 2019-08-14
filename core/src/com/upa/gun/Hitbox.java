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

    /**
     * Increments the position of this hitbox
     * @param adjustX - The amount to increment the x value of this hitbox
     * @param adjustY - The amount to increment the y value of this hitbox
     */
    public void adjustPosition(float adjustX, float adjustY) {
        position.set(new Vector2(position.x + adjustX, position.y + adjustY));
    }

    @Override
    public abstract boolean colliding(Collidable other);
    @Override
    public abstract boolean colliding(RectangularHitbox other);
    public abstract boolean colliding(Vector2 other);

    public boolean isActive() {
        return active;
    }
    public float getX() {
        return position.x;
    }
    public float getY() {
        return position.y;
    }
    public abstract float getWidth();
    public abstract float getHeight();
    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getOffset() {
        return offset;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setOffset(float x, float y) {
        offset.x = x;
        offset.y = y;
    }
}