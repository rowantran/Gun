package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

/**
 * A hitbox with rectangular boundaries
 */
public class RectangularHitbox extends Hitbox {

    private Vector2 size;

    public RectangularHitbox(Vector2 position, Vector2 size) {
        super(true, position, new Vector2(0, 0));
        this.size = size.cpy();
    }

    @Override
    /**
     * Sends colliding test to Collidable object to test collision with a RectangularHitbox
     */
    public boolean colliding(Collidable other) {
        return isActive() && other.colliding(this);
    }

    @Override
    /**
     * Checks whether rectangular hitboxes are overlapping
     */
    public boolean colliding(RectangularHitbox other) {
        Vector2 position = getPosition();
        Vector2 size = getSize();
        Vector2 otherPosition = other.getPosition();
        Vector2 otherSize = other.getSize();
        return position.x+size.x > otherPosition.x && position.x < otherPosition.x+otherSize.x && position.y < otherPosition.y+otherSize.y &&
                position.y+size.y > otherPosition.y;
    }

    @Override
    /**
     * Checks whether a  point is inside the hitbox
     */
    public boolean colliding(Vector2 other) {
        Vector2 position = getPosition();
        Vector2 size = getSize();
        return position.x < other.x && position.x + size.x > other.x && position.y < other.y && position.y + size.y > other.y;
    }

    public Vector2 getSize() { return size; }
    @Override
    public float getWidth() { return getSize().x; }
    @Override
    public float getHeight() { return getSize().y; }
}
