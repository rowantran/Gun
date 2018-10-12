package com.upa.gun;

public interface Hitbox {
    boolean colliding(Hitbox other);
    boolean colliding(RectangularHitbox other);

    float getX();
    float getY();
    void setX(float x);
    void setY(float y);

    float getX();
    float getY();

    float getWidth();
    float getHeight();
}