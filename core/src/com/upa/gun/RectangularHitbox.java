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
        return other.colliding(this);
    }

    @Override
    public boolean colliding(RectangularHitbox other) {
        return x+width > other.x && x < other.x+other.width && y < other.y+other.height && y+height > other.y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
