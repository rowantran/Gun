package com.upa.gun;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GunWorld {
    private static GunWorld gunWorld = null;

    Player player;
    List<Bullet> bullets;
    List<Enemy> enemies;
    List<Crate> crates;
    List<SpawnIndicator> indicators;
    List<ScriptedEventSequence> sequences;
    Spawner spawner;

    boolean cinematicHappening;

    private GunWorld() {
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        crates = new ArrayList<Crate>();
        indicators = new ArrayList<SpawnIndicator>();
        sequences = new ArrayList<ScriptedEventSequence>();

        spawner = new Spawner(this);

        for(int i = 0; i < 14; i++) {
            crates.add(new Crate(((float)i * 64f + 32f), 29f, Assets.crate));
        }

        for(int i = 17; i < 19; i++) {
            crates.add(new Crate(((float)i * 64f + 32f), 29f, Assets.crate));
        }
    }

    static GunWorld getInstance() {
        if (gunWorld == null) {
            gunWorld = new GunWorld();
        }

        return gunWorld;
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
            handleInput(delta);
            player.update(delta);

            for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
                Bullet bullet = iterator.next();
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

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Settings.KEY_ROLL)) {
            player.roll();
        }
    }

    public void updatePostPhysics(float delta) {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
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
