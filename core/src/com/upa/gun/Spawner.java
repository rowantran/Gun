package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Spawner {
    GunWorld gunWorld;
    World world;
    public int slimesKilled;
    int slimesKilledSinceLastBoss;

    float timeAccumulated;

    float maxSpawnTime;
    float maxSpawnTimeMax;

    boolean bossAlive;

    int bossThreshold;

    int bossHealth;

    Spawner(GunWorld gunWorld, World world) {
        this.gunWorld = gunWorld;
        this.world = world;

        slimesKilled = 0;
        slimesKilledSinceLastBoss = 0;

        timeAccumulated = 0f;

        maxSpawnTime = generateRandomSpawnTime();
        maxSpawnTimeMax = 5.0f;

        bossAlive = false;

        bossThreshold = 30;

        bossHealth = 30;
    }

    float generateRandomSpawnTime() {
        return (float) Math.random() * maxSpawnTimeMax / Settings.PERCENT_SPAWN_CHANCE;
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

    void spawnBossSlime() {
        TextureRegion bossSlimeHurt = Assets.bossSlimeAnimations.get(ActionState.HURT).get(Direction.LEFT).getKeyFrame(0);
        int spawnX = (int) (Settings.RESOLUTION.x - bossSlimeHurt.getRegionWidth()) / 2;
        int spawnY = (int) (Settings.RESOLUTION.y - bossSlimeHurt.getRegionHeight() / 2);

        BossSlimeFactory factory = new BossSlimeFactory();
        gunWorld.enemies.add(factory.makeBossSlime(bossHealth, spawnX, spawnY, world, gunWorld));
    }

    void update(float delta) {
        timeAccumulated += delta;
        if (slimesKilledSinceLastBoss == bossThreshold && !bossAlive) {
            spawnBossSlime();
            bossAlive = true;
            slimesKilledSinceLastBoss = 0;
            bossHealth += 10;
            maxSpawnTimeMax *= 0.75f;
        }

        if (timeAccumulated >= maxSpawnTime && !bossAlive) {
            spawnSlime();
            maxSpawnTime = generateRandomSpawnTime();
            timeAccumulated = 0;
        }
    }

}
