package com.upa.gun;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class RoomScreen1 extends ScreenAdapter {
    GunGame game;
    OrthographicCamera camera;
    Player player;
    Bullet bullet;

    public RoomScreen1(GunGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        bullet = new Bullet(10, 10);
        player = new Player(10, 10);
    }

    void drawBackground() {
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.backgroundRoom1, (Settings.RESOLUTION.x - Assets.backgroundRoom1.getWidth()) / 2,
                0, Assets.backgroundRoom1.getWidth(), Assets.backgroundRoom1.getHeight());
        game.batch.end();
    }

    public void draw() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        drawBackground();
        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Assets.bulletBasic, bullet.bounds.x, bullet.bounds.y, bullet.bounds.width,
                bullet.bounds.height);
        game.batch.draw(Assets.playerBasic, player.bounds.x, player.bounds.y, player.bounds.width,
                player.bounds.height);
        game.batch.end();
    }

    public void update(float delta) {
        player.update(delta);
        bullet.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }
}
