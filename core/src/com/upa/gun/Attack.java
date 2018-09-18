package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public interface Attack {
    void shoot(GunWorld world, Vector2 position);
    float length();
}
