package com.upa.gun;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Renderer {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    World world;

    Renderer(SpriteBatch batch, World world) {
        this.batch = batch;
        this.world = world;

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

    private void drawBullets() {
        batch.enableBlending();
        batch.begin();

        for (Bullet bullet : world.bullets) {
            batch.draw(Assets.bulletBasic, bullet.bounds.x, bullet.bounds.y, bullet.bounds.width,
                    bullet.bounds.height);
        }

        batch.end();
    }

    void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawBackground();
        drawBullets();
    }
}
