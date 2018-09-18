package com.upa.gun;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class Enemy {
    float timeElapsed;
    float timeSinceAttack;
    boolean shooting;
    boolean dying;
    boolean markedForDeletion;
    Body body;
    GunWorld gunWorld;
    AttackRotation rotation;

    Enemy(GunWorld gunWorld) {
        timeElapsed = 20.0f;
        shooting = false;
        dying = false;
        markedForDeletion = false;
        this.gunWorld = gunWorld;
    }

    public void update(float delta) {
        timeElapsed += delta;
        rotation.cycle(delta);
    }
}
