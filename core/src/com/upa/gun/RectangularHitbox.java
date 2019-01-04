package com.upa.gun;

public class RectangularHitbox extends Hitbox {
    float x, y, width, height;
    public RectangularHitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean colliding(RectangularHitbox other) {
        return x+width > other.x && x < other.x+other.width && y < other.y+other.height && y+height > other.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
