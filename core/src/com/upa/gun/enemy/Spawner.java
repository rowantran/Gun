package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.upa.gun.*;
import com.upa.gun.cutscene.BossSlimeEntrance;

public class Spawner implements Updatable {
    World world;
    public int slimesKilled;
    public int slimesKilledSinceLastBoss;

    float timeAccumulated;

    float maxSpawnTime;
    float maxSpawnTimeMax;

    boolean bossAlive;

    int bossThreshold;

    int bossHealth;

    public Spawner(World world) {
        this.world = world;

        slimesKilled = 0;
        slimesKilledSinceLastBoss = 0;

        timeAccumulated = 0f;

        maxSpawnTime = generateRandomSpawnTime();
        maxSpawnTimeMax = 5.0f;

        bossAlive = false;

        bossThreshold = 5;

        bossHealth = 30;

        //slimeFactory = new EnemyFactory(new Slime(0, 0));
        //strongSlimeFactory = new EnemyFactory(new StrongSlime(0, 0));
    }

    /**
     * Reset this spawner to the default state, as if it were newly constructed.
     */
    public void reset() {
        slimesKilled = 0;
        slimesKilledSinceLastBoss = 0;

        timeAccumulated = 0f;

        maxSpawnTime = generateRandomSpawnTime();

        bossAlive = false;
        bossThreshold = 5;
        bossHealth = 30;
    }

    private float generateRandomSpawnTime() {
        return (float) Math.random() * maxSpawnTimeMax / Settings.PERCENT_SPAWN_CHANCE;
    }

    private void spawnSlime() {
        float spawnX = (((float)Math.random() * 1051) + 113);
        float spawnY = (((float)Math.random() * 600) + 100);
        int slimeType = (int) (Math.random() * 4);
        if (slimeType == 0) {
            World.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, world.getEnemyFactory(), 1));
        } else {
            World.indicators.add(new SpawnIndicator(spawnX, spawnY, 0f, 1f, world.getEnemyFactory(), 0));
        }
    }

    private void spawnBossSlime() {
        TextureRegion bossSlimeHurt = Assets.bossSlimeAnimations.get(SpriteState.HURT).get(Direction.LEFT).getKeyFrame(0);
        float spawnX = ((Settings.RESOLUTION.x - (float)bossSlimeHurt.getRegionWidth()) / 2f);
        float spawnY = Settings.RESOLUTION.y;

        Enemy boss = world.getEnemyFactory().createEnemy(2, spawnX, spawnY);
        World.enemies.add(boss);

        BossSlimeEntrance entrance = new BossSlimeEntrance(boss);
        entrance.start();
        World.sequences.add(entrance);
    }

    @Override
    public void update(float delta) {
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
