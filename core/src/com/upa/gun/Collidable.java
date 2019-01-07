package com.upa.gun;

public interface Collidable {
    boolean colliding(Collidable other);
    boolean colliding(RectangularHitbox other);
}
