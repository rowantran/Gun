package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.cutscene.ScriptedEventSequence;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.EnemyFactory;

import com.upa.gun.enemy.PowerupFactory; //these should not be in enemy... need to fix later.
import com.upa.gun.enemy.Powerup;

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
    public static List<Powerup> powerups;

    public static List<Bullet> enemyBullets;
    static List<Bullet> playerBullets;
    public static List<SpawnIndicator> indicators;

    public static List<ScriptedEventSequence> sequences;

    public static Spawner spawner;

    private CollisionChecker collisionChecker;

    boolean cinematicHappening;

    private EnemyFactory enemyFactory;
    private PowerupFactory powerupFactory;
    private MapFactory mapFactory;

    public static MapLayout currentMap; //may move
    public static MapLayout[][] fullMap;

    private World() {
        enemies = new ArrayList<Enemy>();
        powerups = new ArrayList<Powerup>();

        enemyBullets = new ArrayList<Bullet>();
        playerBullets = new ArrayList<Bullet>();


        indicators = new ArrayList<SpawnIndicator>();

        sequences = new ArrayList<ScriptedEventSequence>();

        spawner = new Spawner(this);

        collisionChecker = new CollisionChecker();

        enemyFactory = new EnemyFactory("enemies.json");
        powerupFactory = new PowerupFactory("powerups.json");
        mapFactory = new MapFactory("maps.json");

        fullMap = new MapLayout[2][1];

        int m = 0;
        for(int i = 0; i < fullMap.length; i++) {
            for(int j = 0; j < fullMap[i].length; j++) {
                fullMap[i][j] = mapFactory.createMap(m);
                m++;
            }
        }

        currentMap = fullMap[0][0];

    }

    public void reset() {
        player.reset();

        enemies.clear();
        powerups.clear();

        enemyBullets.clear();
        playerBullets.clear();

        indicators.clear();

        sequences.clear();

        spawner.reset();
    }

    public static World getInstance() {
        return world;
    }

    void setGunGame(GunGame game) {
        player = new Player(new Vector2(200, 200), game);
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


            collisionChecker.update(delta);

            player.update(delta);

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

        for (Iterator<Powerup> iterator = powerups.iterator(); iterator.hasNext();) {
            Powerup powerup = iterator.next();
            if (powerup.markedForDeletion) {
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
    public PowerupFactory getPowerupFactory() { return powerupFactory;}
}
