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
    World world;
    Spawner spawner;

    private OrthographicCamera worldCamera;

    boolean cinematicHappening;

    GunGame game;

    private GunWorld() {
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        crates = new ArrayList<Crate>();
        indicators = new ArrayList<SpawnIndicator>();
        sequences = new ArrayList<ScriptedEventSequence>();

        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, Settings.RESOLUTION.x/Settings.PPM, Settings.RESOLUTION.y/Settings.PPM);

        world = new World(new Vector2(0, 0), true);
        spawner = new Spawner(this, world);

        for(int i = 0; i < 14; i++) {
            crates.add(new Crate(((float)i * 64f + 32f)/Settings.PPM, 29f/Settings.PPM, Assets.crate, world));
        }

        for(int i = 17; i < 19; i++) {
            crates.add(new Crate(((float)i * 64f + 32f)/Settings.PPM, 29f/Settings.PPM, Assets.crate, world));
        }
    }

    public static GunWorld getInstance() {
        if (gunWorld == null) {
            gunWorld = new GunWorld();
        }

        return gunWorld;
    }

    public void setGunGame(GunGame game) {
        player = new Player(2, 2, game, world);
        this.game = game;
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
            Vector3 mousePos3 = worldCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
            player.roll();
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
