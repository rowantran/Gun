package com.upa.gun;

public interface Hitbox {
    boolean colliding(Hitbox other);
    boolean colliding(RectangularHitbox other);

    void setX(float x);
    void setY(float y);
}