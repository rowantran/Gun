package com.upa.gun;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

class Renderer {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    Renderer(SpriteBatch batch) {
        this.batch = batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);
    }

    private void drawBackground() {
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.backgroundRoom1, (Settings.RESOLUTION.x - Assets.backgroundRoom1.getWidth()) / 2,
                0, Assets.backgroundRoom1.getWidth(), Assets.backgroundRoom1.getHeight());
        batch.end();
    }

    private void drawBullets(List<Bullet> bullets) {
        batch.enableBlending();
        batch.begin();

        for (Bullet bullet : bullets) {
            batch.draw(Assets.bulletBasic, bullet.bounds.x, bullet.bounds.y, bullet.bounds.width,
                    bullet.bounds.height);
        }

        batch.end();
    }

    void draw(List<Bullet> bullets) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawBackground();
        drawBullets(bullets);
    }
}
