package com.upa.gun;

public interface Hitbox {
    boolean colliding(Hitbox other);
    boolean colliding(RectangularHitbox other);

    void setX(float x);
    void setY(float y);

    float getX();
    float getY();

    float getWidth();
    float getHeight();

    boolean isActive();
    void setActive(boolean active);
}