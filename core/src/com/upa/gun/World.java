package com.upa.gun;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {
    private static World world = new World();

    static Player player;
    static List<Enemy> enemies;
    static List<Bullet> enemyBullets;
    static List<Bullet> playerBullets;
    static List<Crate> crates;
    static List<SpawnIndicator> indicators;
    static List<ScriptedEventSequence> sequences;
    Spawner spawner;
    private CollisionChecker collisionChecker;

    boolean cinematicHappening;

    private World() {
        enemyBullets = new ArrayList<Bullet>();
        playerBullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        crates = new ArrayList<Crate>();
        indicators = new ArrayList<SpawnIndicator>();
        sequences = new ArrayList<ScriptedEventSequence>();

        spawner = new Spawner(this);
        collisionChecker = new CollisionChecker();

        for(int i = 0; i < 14; i++) {
            crates.add(new Crate(((float)i * 64f + 32f), 29f, Assets.crate));
        }

        for(int i = 17; i < 19; i++) {
            crates.add(new Crate(((float)i * 64f + 32f), 29f, Assets.crate));
        }
    }

    static World getInstance() {
        return world;
    }

    void setGunGame(GunGame game) {
        player = new Player(200, 200, game);
    }

    public void update(float delta) {
        cinematicHappening = false;
        for (ScriptedEventSequence sequence : sequences) {
            sequence.update(delta, this);
            if (sequence.cinematic && sequence.active) {
                cinematicHappening = true;
            }
        }

        if (!cinematicHappening) {
            handleInput();
            player.update(delta);

            collisionChecker.update(delta);

            for (Bullet bullet : playerBullets) {
                bullet.update(delta);
            }

            for (Bullet bullet : enemyBullets) {
                bullet.update(delta);
            }

            for (Enemy enemy : enemies) {
                enemy.update(delta);
            }

            for (SpawnIndicator spawn : indicators) {
                spawn.update(delta);
                if (spawn.shouldSpawn()) {
                    spawner.createSpawn(spawn);
                    spawn.markedForDeletion = true;
                }
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Settings.KEY_ROLL)) {
            player.roll();
        }
    }

    void deleteMarkedForDeletion() {
        for (Iterator<Bullet> iterator = playerBullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if (bullet.markedForDeletion) {
                iterator.remove();
            }
        }

        for (Iterator<Bullet> iterator = enemyBullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if (bullet.markedForDeletion) {
                iterator.remove();
            }
        }

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();

            if (enemy.markedForDeletion) {
                iterator.remove();
            }
        }

        for (Iterator<SpawnIndicator> iterator = indicators.iterator(); iterator.hasNext();) {
            SpawnIndicator spawn = iterator.next();
            if (spawn.markedForDeletion) {
                iterator.remove();
            }
        }
    }
}
