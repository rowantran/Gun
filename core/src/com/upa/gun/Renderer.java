package com.upa.gun;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class Renderer {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private World world;

    private ShapeRenderer sr;

    Renderer(SpriteBatch batch, World world) {
        this.batch = batch;
        this.world = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        sr = new ShapeRenderer();
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
        Assets.playerIdleSprite.setAlpha(world.player.opacity);
        TextureRegion currentFrame = Assets.playerIdleSprite;
        if (world.player.dying || world.player.fading) {
            Assets.playerIdleSprite.draw(batch);
        } else {
            if (world.player.moving) {
                currentFrame = Assets.playerAnimation.getKeyFrame(world.player.timeElapsed);
            }

            batch.draw(currentFrame, world.player.bounds.x, world.player.bounds.y,
                    world.player.bounds.width, world.player.bounds.height);
        }

        batch.end();

        if (Settings.SHOW_HITBOXES) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            sr.rect(world.player.bounds.x, world.player.bounds.y, world.player.bounds.width,
                    world.player.bounds.height);
            sr.end();
        }
    }

    private void drawBullets() {
        batch.enableBlending();
        sr.setProjectionMatrix(camera.combined);
        sr.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        for (Bullet bullet : world.bullets) {
            batch.begin();
            batch.draw(Assets.bulletBasic, bullet.getX(), bullet.getY(), bullet.getWidth(),
                    bullet.getHeight());
            batch.end();
            if (Settings.SHOW_HITBOXES) {
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
                sr.end();
            }
        }
    }

    void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawBackground();
        drawPlayer();
        drawBullets();
    }
}
