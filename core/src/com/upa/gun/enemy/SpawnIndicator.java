package com.upa.gun.enemy;

import com.upa.gun.Updatable;

public class SpawnIndicator implements Updatable {
    public float x, y;
    float timeElapsed;
    float timeUntilSpawn;
    public boolean markedForDeletion;

    private EnemyFactory factory;
    private int id;

    SpawnIndicator(float x, float y, float timeElapsed, float timeUntilSpawn, EnemyFactory factory, int id) {
        this.x = x;
        this.y = y;
        this.timeElapsed = timeElapsed;
        this.timeUntilSpawn = timeUntilSpawn;
        markedForDeletion = false;

        this.factory = factory;
        this.id = id;
    }

    @Override
    public void update(float delta) {
        timeElapsed += delta;
    }

    public boolean shouldSpawn() {
        return timeElapsed >= timeUntilSpawn;
    }

    public Enemy createSpawn() {
        return factory.createEnemy(id, (int) x, (int) y);
    }
}
