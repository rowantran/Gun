package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class SpawnIndicator {
    int x, y;
    float timeElapsed;
    float timeUntilSpawn;
    Class spawnType;
    boolean markedForDeletion;

    SpawnIndicator(int x, int y, float timeElapsed, float timeUntilSpawn, Class spawnType) {
        this.x = x;
        this.y = y;
        this.timeElapsed = timeElapsed;
        this.timeUntilSpawn = timeUntilSpawn;
        this.spawnType = spawnType;
        markedForDeletion = false;
    }

    void update(float delta) {
        timeElapsed += delta;
    }

    boolean shouldSpawn() {
        return timeElapsed >= timeUntilSpawn;
    }

    void createSpawn(World world, GunWorld gunWorld) {
        try {
            Enemy enemy = (Enemy) spawnType.getConstructor(float.class, float.class, World.class, GunWorld.class).
                    newInstance(x, y, world, gunWorld);
            gunWorld.enemies.add(enemy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
