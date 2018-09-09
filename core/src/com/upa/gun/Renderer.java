package com.upa.gun;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

class Renderer {
    private SpriteBatch batch;
    OrthographicCamera camera;

    private GunWorld world;

    private ShapeRenderer sr;

    Renderer(SpriteBatch batch, GunWorld world) {
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

    private void drawPlayer(float x, float y) {
        batch.enableBlending();
        batch.begin();
        Assets.playerIdleSprites[world.player.rotation].setAlpha(world.player.opacity);
        TextureRegion currentFrame = Assets.playerIdleSprites[world.player.rotation];
        if (world.player.dying || world.player.fading) {
            Assets.playerIdleSprites[world.player.rotation].draw(batch);
        } else {
            if (world.player.moving) {
                currentFrame = Assets.playerAnimations.get(world.player.rotation).getKeyFrame(
                        world.player.timeElapsed);
            }

            batch.draw(currentFrame, (x-currentFrame.getRegionWidth()/2), (y-currentFrame.getRegionHeight()/2),
                    currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        }

        batch.end();
    }

    private void drawBullet(Bullet bullet, float x, float y) {
        batch.enableBlending();
        sr.setProjectionMatrix(camera.combined);
        sr.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        batch.begin();
        bullet.bulletSprite.setX(x-bullet.bulletSprite.getRegionWidth()/2);
        bullet.bulletSprite.setY(y-bullet.bulletSprite.getRegionHeight()/2);
        bullet.bulletSprite.draw(batch);
        batch.end();
    }

    void draw(World world) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        drawBackground();
        for (Body b : bodies) {
            Object id = b.getUserData();
            if (id != null) {
                if (id instanceof Player) {
                    drawPlayer(b.getPosition().x, b.getPosition().y);
                } else if (id instanceof Bullet) {
                    Bullet bullet = (Bullet) id;
                    drawBullet(bullet, b.getPosition().x, b.getPosition().y);
                }
            }
        }
    }
}
