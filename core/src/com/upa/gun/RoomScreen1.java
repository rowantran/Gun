package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;

public class RoomScreen1 extends ScreenAdapter {
    GunGame game;
    Renderer renderer;

    World world;

    RoomScreen1(GunGame game) {
        this.game = game;

        world = new World(game.player);

        renderer = new Renderer(game.batch, world);

        world.bullets.add(new Bullet(200, 300, Math.PI/6));
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.draw();
    }
}
