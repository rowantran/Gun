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
    public void render(float delta) {
        game.world.update(delta);
        game.world.spawner.update(delta);

        renderer.draw(game.world);

        game.world.updatePostPhysics(delta);
    }
}
