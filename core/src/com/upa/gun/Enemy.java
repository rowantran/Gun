package com.upa.gun;

public abstract class Enemy {
    public float timeElapsed;

    Enemy() {
        timeElapsed = 0.0f;
    }

    public void update(float delta) {
        timeElapsed += delta;
    }
}
