package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

interface Attack {
    void attack(Vector2 position);
    float length();
    float interval();
    boolean isMobile();
}
