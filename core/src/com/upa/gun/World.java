package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.upa.gun.cutscene.ScriptedEventSequence;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.EnemyFactory;
import com.upa.gun.enemy.SpawnIndicator;
import com.upa.gun.enemy.Spawner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class World implements Updatable {
    private static World world = new World();

    public static Player player;

    public static List<Enemy> enemies;

    public static List<Bullet> enemyBullets;
    static List<Bullet> playerBullets;

    static List<Crate> crates;

    public static List<SpawnIndicator> indicators;

    public static List<ScriptedEventSequence> sequences;

    public static Spawner spawner;

    private CollisionChecker collisionChecker;

    boolean cinematicHappening;

    private EnemyFactory enemyFactory;

    private World() {
        enemies = new ArrayList<Enemy>();

        enemyBullets = new ArrayList<Bullet>();
        playerBullets = new ArrayList<Bullet>();

        crates = new ArrayList<Crate>();
        createCrates();

        indicators = new ArrayList<SpawnIndicator>();

        sequences = new ArrayList<ScriptedEventSequence>();

        spawner = new Spawner(this);

        collisionChecker = new CollisionChecker();

        enemyFactory = new EnemyFactory("enemies.json");
    }

    private void createCrates() {
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

    @Override
    public void update(float delta) {
        cinematicHappening = false;
        for (ScriptedEventSequence sequence : sequences) {
            sequence.update(delta);
            if (sequence.isCinematic() && sequence.isActive()) {
                cinematicHappening = true;
            }
        }

        if (!cinematicHappening) {
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

            spawner.update(delta);

            for (SpawnIndicator spawn : indicators) {
                spawn.update(delta);
                if (spawn.shouldSpawn()) {
                    enemies.add(spawn.createSpawn());
                    spawn.markedForDeletion = true;
                }
            }
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

        ListIterator<Enemy> enemyIterator = enemies.listIterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            enemy.getState().iterationUpdate(enemyIterator);
        }

        for (Iterator<SpawnIndicator> iterator = indicators.iterator(); iterator.hasNext();) {
            SpawnIndicator spawn = iterator.next();
            if (spawn.markedForDeletion) {
                iterator.remove();
            }
        }
    }

    public EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }
}
