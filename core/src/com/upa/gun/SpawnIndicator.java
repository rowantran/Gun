package com.upa.gun;

public class SpawnIndicator {
    float x, y;
    float timeElapsed;
    float timeUntilSpawn;
    EnemyFactory factory;
    boolean markedForDeletion;

    SpawnIndicator(float x, float y, float timeElapsed, float timeUntilSpawn, EnemyFactory factory) {
        this.x = x;
        this.y = y;
        this.timeElapsed = timeElapsed;
        this.timeUntilSpawn = timeUntilSpawn;
        this.factory = factory;
        markedForDeletion = false;
    }

    void update(float delta) {
        timeElapsed += delta;
    }

    boolean shouldSpawn() {
        return timeElapsed >= timeUntilSpawn;
    }

    void createSpawn() {
        factory.spawn(x, y);
    }
}
