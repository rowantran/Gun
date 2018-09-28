package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spawner {
    GunWorld world;
    public int slimesKilled;
    int slimesKilledSinceLastBoss;

    float timeAccumulated;

    float maxSpawnTime;
    float maxSpawnTimeMax;

    boolean bossAlive;

    int bossThreshold;

    int bossHealth;

    EnemyFactory slimeFactory;
    EnemyFactory strongSlimeFactory;

    Spawner(GunWorld world) {
        this.world = world;

        slimesKilled = 0;
        slimesKilledSinceLastBoss = 0;

        timeAccumulated = 0f;

        maxSpawnTime = generateRandomSpawnTime();
        maxSpawnTimeMax = 5.0f;

        bossAlive = false;

        bossThreshold = 5;

        bossHealth = 30;

        slimeFactory = new EnemyFactory(new Slime(0, 0));
        strongSlimeFactory = new EnemyFactory(new StrongSlime(0, 0));
    }

    private float generateRandomSpawnTime() {
        return (float) Math.random() * maxSpawnTimeMax / Settings.PERCENT_SPAWN_CHANCE;
    }

    void createSpawn(SpawnIndicator spawn) {
        spawn.createSpawn();
    }

    private void spawnSlime() {
        float spawnX = (((float)Math.random() * 1051) + 113);
        float spawnY = (((float)Math.random() * 600) + 100);
        int slimeType = (int) (Math.random() * 4);
        if (slimeType == 0) {
            world.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, strongSlimeFactory));
        } else {
            world.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, slimeFactory));
        }
    }

    private void spawnBossSlime() {
        TextureRegion bossSlimeHurt = Assets.bossSlimeAnimations.get(SpriteState.HURT).get(Direction.LEFT).getKeyFrame(0);
        float spawnX = ((Settings.RESOLUTION.x - (float)bossSlimeHurt.getRegionWidth()) / 2f);
        float spawnY = Settings.RESOLUTION.y;

        BossSlimeFactory factory = new BossSlimeFactory();
        BossSlime slime = factory.makeBossSlime(bossHealth, spawnX, spawnY);
        BossSlimeEntrance entrance = new BossSlimeEntrance(slime);
        entrance.start();
        world.sequences.add(entrance);
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
