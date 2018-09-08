package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;

import java.util.ArrayList;
import java.util.List;

public class RoomScreen1 extends ScreenAdapter {
    GunGame game;
    Renderer renderer;

    List<Bullet> bullets;

    RoomScreen1(GunGame game) {
        this.game = game;

        renderer = new Renderer(game.batch);

        bullets = new ArrayList<Bullet>();
        bullets.add(new Bullet(10, 10));
    }

    private void update(float delta) {
        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        renderer.draw(bullets);
    }
}
