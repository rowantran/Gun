package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class Spawner {
    GunWorld gunWorld;
    World world;
    int slimesKilled;

    float timeAccumulated;

    float maxSpawnTime;

    Spawner(GunWorld gunWorld, World world) {
        this.gunWorld = gunWorld;
        this.world = world;

        slimesKilled = 0;

        timeAccumulated = 0f;

        maxSpawnTime = generateRandomSpawnTime();
    }

    float generateRandomSpawnTime() {
        return (float) Math.random() * 5.0f;
    }

    void spawnSlime() {
        int spawnPoint = (int) (Math.random() * 2);
        int spawnX, spawnY;
        if (spawnPoint == 0) {
            spawnX = 318;
            spawnY = 760;
        } else {
            spawnX = 1026;
            spawnY = 40;
        }

        int slimeType = (int) (Math.random() * 4);
        if (slimeType == 0) {
            gunWorld.enemies.add(new StrongSlime(spawnX, spawnY, world, gunWorld));
        } else {
            gunWorld.enemies.add(new Slime(spawnX, spawnY, world, gunWorld));
        }
    }

    void update(float delta) {
        System.out.println(slimesKilled);
        timeAccumulated += delta;
        if (timeAccumulated >= maxSpawnTime) {
            spawnSlime();
            maxSpawnTime = generateRandomSpawnTime();
            timeAccumulated = 0;
        }
    }

}
