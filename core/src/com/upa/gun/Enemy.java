package com.upa.gun;

public abstract class Enemy {
    public float timeElapsed;
    public boolean shooting;
    public GunWorld gunWorld;

    Enemy(GunWorld gunWorld) {
        timeElapsed = 0.0f;
        shooting = false;
        this.gunWorld = gunWorld;
    }

    public void update(float delta) {
        timeElapsed += delta;
    }
}
