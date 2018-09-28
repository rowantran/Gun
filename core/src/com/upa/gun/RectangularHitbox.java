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
        return x+width > other.x && x < other.x+other.width && y < other.y+other.height && y+height > other.y;
    }
}
