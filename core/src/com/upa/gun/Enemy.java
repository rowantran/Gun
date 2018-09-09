package com.upa.gun;

public abstract class Enemy {
    public float timeElapsed;
    public boolean shooting;


    Enemy() {
        timeElapsed = 0.0f;
        shooting = false;
    }

    public void update(float delta) {
        timeElapsed += delta;
    }
}
