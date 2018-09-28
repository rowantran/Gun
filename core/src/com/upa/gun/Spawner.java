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

    EnemyFactory<Slime> slimeFactory;
    EnemyFactory<StrongSlime> strongSlimeFactory;

    Spawner(GunWorld gunWorld, World world) {
        this.gunWorld = gunWorld;
        this.world = world;

        slimesKilled = 0;
        slimesKilledSinceLastBoss = 0;

        timeAccumulated = 0f;

        maxSpawnTime = generateRandomSpawnTime();
        maxSpawnTimeMax = 5.0f;

        bossAlive = false;

        bossThreshold = 5;

        bossHealth = 30;

        slimeFactory = new SlimeFactory();
        strongSlimeFactory = new StrongSlimeFactory();
    }

    float generateRandomSpawnTime() {
        return (float) Math.random() * maxSpawnTimeMax / Settings.PERCENT_SPAWN_CHANCE;
    }

    void createSpawn(SpawnIndicator spawn) {
        spawn.createSpawn(world, gunWorld);
    }

    void spawnSlime() {
        float spawnX = (((float)Math.random() * 1051) + 113);
        float spawnY = (((float)Math.random() * 600) + 100);
        int slimeType = (int) (Math.random() * 4);
        if (slimeType == 0) {
            gunWorld.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, strongSlimeFactory));
        } else {
            gunWorld.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, slimeFactory));
        }
    }

    void spawnBossSlime() {
        TextureRegion bossSlimeHurt = Assets.bossSlimeAnimations.get(SpriteState.HURT).get(Direction.LEFT).getKeyFrame(0);
        float spawnX = ((Settings.RESOLUTION.x - (float)bossSlimeHurt.getRegionWidth()) / 2f);
        float spawnY = Settings.RESOLUTION.y;

        BossSlimeFactory factory = new BossSlimeFactory();
        BossSlime slime = factory.makeBossSlime(bossHealth, spawnX, spawnY, world, gunWorld);
        BossSlimeEntrance entrance = new BossSlimeEntrance(slime);
        entrance.start();
        gunWorld.sequences.add(entrance);
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
