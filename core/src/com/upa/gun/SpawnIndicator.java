package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class SpawnIndicator {
    float x, y;
    float timeElapsed;
    float timeUntilSpawn;
    EnemyFactory<? extends Enemy> factory;
    boolean markedForDeletion;

    SpawnIndicator(float x, float y, float timeElapsed, float timeUntilSpawn, EnemyFactory<? extends Enemy> factory) {
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

    void createSpawn(World world, GunWorld gunWorld) {
        gunWorld.enemies.add(factory.create(x, y, world, gunWorld));
    }
}
