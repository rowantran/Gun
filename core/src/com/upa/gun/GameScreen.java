package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameScreen extends ScreenAdapter {
    GunGame game;
    Renderer renderer;
    private Box2DDebugRenderer drenderer;

    GunWorld world;

    GameScreen(GunGame game) {
        this.game = game;

        world = new GunWorld(game.player, game.world);

        renderer = new Renderer(game.batch, world);

        world.bullets.add(new EnemyBullet(50, 50, Math.PI / 6, game.world));
        world.enemies.add(new Slime(80,80, game.world, world));

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
    }
}
