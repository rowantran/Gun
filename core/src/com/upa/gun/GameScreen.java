package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameScreen extends ScreenAdapter {
    GunGame game;
    Renderer renderer;
    private Box2DDebugRenderer drenderer;
    int spawnChance;

    GunWorld world;

    GameScreen(GunGame game) {
        this.game = game;

        world = new GunWorld(game.player, game.world);

        renderer = new Renderer(game.batch, world);

        if (Settings.DEV_MODE) {
            drenderer = new Box2DDebugRenderer();
        }
    }

    @Override
    public void show() {
        System.out.println("Binding contact listener");
        game.world.setContactListener(new GunContactListener(world, game.world));
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.draw(game.world);
        if (Settings.DEV_MODE) {
            drenderer.render(game.world, renderer.camera.combined);
        }
        game.doPhysicsStep(delta);
        world.updatePostPhysics(delta);
        
        int genSlime = (int)(Math.random() * 100);
        if (genSlime <= Settings.PERCENT_SPAWN_CHANCE) {

            int spawnPoint = (int)(Math.random() * 2);
            if(spawnPoint == 0) {
                world.enemies.add(new Slime(318, 760, game.world, world));
            }
            if(spawnPoint == 1) {
                world.enemies.add(new Slime(1026, 40, game.world, world));
            }
        }




    }
}
