package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameScreen extends ScreenAdapter {
    GunGame game;
    Renderer renderer;
    private Box2DDebugRenderer drenderer;

    GameScreen(GunGame game) {
        this.game = game;

        renderer = new Renderer(game.batch, game.world);

        if (Settings.DEV_MODE) {
            drenderer = new Box2DDebugRenderer();
        }
    }

    @Override
    public void show() {
        System.out.println("Binding contact listener");
        game.world.world.setContactListener(new GunContactListener(game.world));
    }

    @Override
    public void render(float delta) {
        game.world.update(delta);
        game.world.spawner.update(delta);

        renderer.draw(game.world);
        if (Settings.DEV_MODE) {
            drenderer.render(game.world.world, renderer.camera.combined);
        }
        game.doPhysicsStep(delta);
        game.world.updatePostPhysics(delta);
    }
}
