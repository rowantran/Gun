package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;

public class RoomScreen1 extends ScreenAdapter {
    GunGame game;
    Renderer renderer;

    World world;

    RoomScreen1(GunGame game) {
        this.game = game;

        world = new World();

        renderer = new Renderer(game.batch, world);

        world.bullets.add(new Bullet(10, 10));
    }

    private void update(float delta) {
        for (Bullet bullet : world.bullets) {
            bullet.update(delta);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        renderer.draw();
    }
}
