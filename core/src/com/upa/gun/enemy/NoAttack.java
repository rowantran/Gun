package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;

public class NoAttack implements Attack {
    private float length;
    private boolean mobile;

    NoAttack(float length, boolean mobile) {
        this.length = length;
        this.mobile = mobile;
    }

    @Override
    public void attack(Vector2 position) {}

    @Override
    public float length() {
        return length;
    }

    @Override
    public float interval() {
        return length;
    }

    @Override
    public boolean isMobile() {
        return mobile;
    }
}
