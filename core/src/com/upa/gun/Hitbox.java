package com.upa.gun;

public interface Hitbox {
    boolean colliding(Hitbox other);
    boolean collidingVisit(RectangularHitbox other);
}
