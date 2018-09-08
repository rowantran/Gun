package com.upa.gun;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class Renderer {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private World world;

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

    private void drawPlayer() {
        batch.enableBlending();
        batch.begin();
        TextureRegion currentFrame;
        if (world.player.moving) {
            currentFrame = Assets.playerAnimation.getKeyFrame(world.player.timeElapsed);
        } else {
            currentFrame = Assets.playerAtlas.findRegion("playerFront-idle");
        }
        batch.draw(currentFrame, world.player.bounds.x, world.player.bounds.y,
                world.player.bounds.width, world.player.bounds.height);
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
        drawPlayer();
        drawBullets();
    }
}
