package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;

public class RoomScreen1 extends ScreenAdapter {
    GunGame game;
    Renderer renderer;

    GunWorld world;

    RoomScreen1(GunGame game) {
        this.game = game;

        world = new GunWorld(game.player);

        renderer = new Renderer(game.batch, world);

        world.bullets.add(new Bullet(50, 50, 0, game.world));
    }


    @Override
    public void show() {
        System.out.println("Setting");
        game.world.setContactListener(new GunContactListener());
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.draw(game.world);
        game.doPhysicsStep(delta);
    }
}
