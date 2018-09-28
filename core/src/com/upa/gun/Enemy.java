package com.upa.gun;

public abstract class Enemy extends Entity {
    float timeElapsed;
    float timeSinceAttack;
    boolean dying;
    boolean markedForDeletion;
    AttackRotation rotation;

    Enemy(float x, float y, float width, float height) {
        super(x, y, width, height);
        timeElapsed = 20.0f;
        dying = false;
        markedForDeletion = false;
    }

    abstract Enemy spawnEnemy(float x, float y);

    public void update(float delta) {
        timeElapsed += delta;
        rotation.cycle(delta);
    }
}
