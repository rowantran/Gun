package com.upa.gun;

import box2dLight.RayHandler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GunWorld {
    Player player;
    List<Bullet> bullets;
    List<Enemy> enemies;
    List<Crate> crates;
    List<SpawnIndicator> indicators;
    World world;
    Spawner spawner;

    GunGame game;

    GunWorld(GunGame game) {
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        crates = new ArrayList<Crate>();
        indicators = new ArrayList<SpawnIndicator>();

        world = new World(new Vector2(0, 0), true);
        spawner = new Spawner(this, world);

        player = new Player(2, 2, game, world);

        for(int i = 0; i < 14; i++) {
            crates.add(new Crate(((float)i * 64f + 32f)/Settings.PPM, 29f/Settings.PPM, Assets.crate, world));
        }

        for(int i = 17; i < 19; i++) {
            crates.add(new Crate(((float)i * 64f + 32f)/Settings.PPM, 29f/Settings.PPM, Assets.crate, world));
        }

        this.game = game;
    }


    public void update(float delta) {
        player.update(delta);

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
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

    public void updatePostPhysics(float delta) {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if (bullet.markedForDeletion) {
                world.destroyBody(bullet.body);
                iterator.remove();
            }
        }

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            if (enemy.dying) {
                enemy.body.setActive(false);
            }

            if (enemy.markedForDeletion) {
                world.destroyBody(enemy.body);
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
