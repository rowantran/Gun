package com.upa.gun;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class Enemy {
    public float timeElapsed;
    public boolean shooting;
    public boolean dying;
    public boolean markedForDeletion;
    public Body body;
    public GunWorld gunWorld;

    Enemy(GunWorld gunWorld) {
        timeElapsed = 0.0f;
        shooting = false;
        dying = false;
        markedForDeletion = false;
        this.gunWorld = gunWorld;
    }

    public void update(float delta) {
        timeElapsed += delta;
    }
}
