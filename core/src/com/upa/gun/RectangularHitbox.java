package com.upa.gun;

public class RectangularHitbox implements Hitbox {
    float x, y, width, height;
    RectangularHitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean colliding(Hitbox other) {
        return other.collidingVisit(this);
    }

    @Override
    public boolean collidingVisit(RectangularHitbox other) {
        boolean colliding = false;
        // TODO: collision check
        return colliding;
    }
}
