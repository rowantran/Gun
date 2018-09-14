package com.upa.gun;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

import static com.upa.gun.Direction.LEFT;

class Renderer {
    private SpriteBatch batch;
    OrthographicCamera camera;
    private OrthographicCamera hudCamera;

    private GunWorld world;

    private GlyphLayout layout;
    private BitmapFont font;

    private RayHandler rayHandler;

    Renderer(SpriteBatch batch, GunWorld world) {
        this.batch = batch;
        this.world = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (float)Settings.RESOLUTION.x/Settings.PPM, (float)Settings.RESOLUTION.y/Settings.PPM);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        layout = new GlyphLayout();
        font = new BitmapFont();
        font.getData().setScale(4);

        rayHandler = new RayHandler(world.world);
        rayHandler.setShadows(true);
        rayHandler.useCustomViewport(0, 0, (int)Settings.RESOLUTION.x, (int)Settings.RESOLUTION.y);
        DirectionalLight pl = new DirectionalLight(rayHandler, 3, new Color(1f,0f,0f,0.8f), -90);
        pl.setStaticLight(false);
        pl.setSoft(true);
        pl.setXray(true);

        PointLight pl2 = new PointLight(rayHandler, 3, new Color(1f,0f,0f,0.8f), 2, 1, 1);
        pl2.setStaticLight(false);
        pl2.setSoft(true);
        pl2.setXray(true);
    }

    private void drawBackground() {
        batch.disableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(Assets.backgroundRoom1, (Settings.RESOLUTION.x - Assets.backgroundRoom1.getWidth()) /2f/Settings.PPM,
                0, (float)Assets.backgroundRoom1.getWidth()/Settings.PPM, (float)Assets.backgroundRoom1.getHeight()/Settings.PPM);
    }

    private void drawPlayer(Player player) {
        batch.enableBlending();
        Animation<TextureRegion> currentAnimation = Assets.playerAnimations.get(player.getState()).get(player.direction);
        TextureRegion currentFrame = currentAnimation.getKeyFrame(world.player.timeElapsed);

        batch.setColor(1.0f, 1.0f, 1.0f, player.opacity);
        batch.draw(currentFrame, (player.body.getPosition().x - (float)currentFrame.getRegionWidth()/2f/Settings.PPM),
                (player.body.getPosition().y - (float)currentFrame.getRegionHeight()/2f/Settings.PPM), 0, 0,
                (float)currentFrame.getRegionWidth()/Settings.PPM, (float)currentFrame.getRegionHeight()/Settings.PPM,
                1, 1, world.player.rotation);

    }

    private void drawHealth(int health) {
        float startX = 50f/Settings.PPM;
        float incrementX = 40f/Settings.PPM;
        float startY = 72f/Settings.PPM;
        if (health > 0) {
            for (int i = 1; i <= health; i++) {
                batch.draw(Assets.heart, startX, startY, (float)Assets.heart.getWidth()*2f/Settings.PPM,
                        (float)Assets.heart.getHeight()*2f/Settings.PPM);
                startX += incrementX;
            }
        }
    }

    private void drawSlime(Slime slime, float x, float y, Map<ActionState, Map<Direction, Animation<TextureRegion>>> animationMap) {
        batch.enableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, slime.opacity);

        ActionState state = slime.getState();
        Animation<TextureRegion> currentAnimation = animationMap.get(state).get(LEFT);
        TextureRegion currentFrame;

        if (state == ActionState.ATTACKING) {
            currentFrame = currentAnimation.getKeyFrame(slime.attackTimeElapsed);
        } else {
            currentFrame = currentAnimation.getKeyFrame(slime.timeElapsed);
        }

        batch.draw(currentFrame, (x-(float)currentFrame.getRegionWidth()/2f/Settings.PPM),
                (y-(float)currentFrame.getRegionHeight()/2f/Settings.PPM),
                (float)currentFrame.getRegionWidth()/Settings.PPM, (float)currentFrame.getRegionHeight()/Settings.PPM);
    }

    private void drawBossSlime(BossSlime bossSlime, float x, float y) {
        batch.enableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, bossSlime.opacity);

        ActionState state = bossSlime.getState();
        Animation<TextureRegion> currentAnimation = Assets.bossSlimeAnimations.get(state).get(LEFT);
        TextureRegion currentFrame;

        if (state == ActionState.ATTACKING) {
            currentFrame = currentAnimation.getKeyFrame(bossSlime.attackTimeElapsed);
        } else {
            currentFrame = currentAnimation.getKeyFrame(bossSlime.timeElapsed);
        }

        currentFrame.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        batch.draw(currentFrame, x-(float)currentFrame.getRegionWidth()*4f/Settings.PPM,
                y-(float)currentFrame.getRegionHeight()*4f/Settings.PPM,
                (float)currentFrame.getRegionWidth()*8f/Settings.PPM,
                (float)currentFrame.getRegionHeight()*8f/Settings.PPM);
    }

    private void drawBullet(Bullet bullet, float x, float y) {
        batch.enableBlending();

        bullet.bulletSprite.setX(x-(float)bullet.bulletSprite.getRegionWidth()/2f);
        bullet.bulletSprite.setY(y-(float)bullet.bulletSprite.getRegionHeight()/2f);
        bullet.bulletSprite.draw(batch);
    }

    private void drawCrate(Crate crate, float x, float y) {
        batch.enableBlending();

        crate.crateSprite.setX(x);
        crate.crateSprite.setY(y);
        crate.crateSprite.draw(batch);
    }

    private void drawScore() {
        batch.enableBlending();

        layout.setText(font, Integer.toString(world.spawner.slimesKilled));
        float x = 30;
        float y = (Settings.RESOLUTION.y - layout.height);
        System.out.println(x + "," + y);
        font.draw(batch, layout, x, y);
    }

    private void drawIndicator(SpawnIndicator s) {
        batch.draw(Assets.crosshair, s.x, s.y, Assets.crosshair.getWidth()*4, Assets.crosshair.getHeight()*4);
    }

    private void drawFPS() {
        batch.enableBlending();

        layout.setText(font, Integer.toString(Gdx.graphics.getFramesPerSecond()));
        float x = 0;
        float y = layout.height;
        font.draw(batch, layout, x, y);
    }

    void draw(GunWorld world) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Array<Body> bodies = new Array<Body>();
        world.world.getBodies(bodies);

        batch.begin();
        drawBackground();

        drawPlayer(world.player);

        for (Body b : bodies) {
            Object id = b.getUserData();
            if (id != null) {
                if (id instanceof BossSlime) {
                    BossSlime bossSlime = (BossSlime) id;
                    drawBossSlime(bossSlime, b.getPosition().x, b.getPosition().y);
                } else if (id instanceof StrongSlime) {
                    StrongSlime slime = (StrongSlime) id;
                    drawSlime(slime, b.getPosition().x, b.getPosition().y, Assets.strongSlimeAnimations);
                } else if (id instanceof Slime) {
                    Slime slime = (Slime) id;
                    drawSlime(slime, b.getPosition().x, b.getPosition().y, Assets.slimeAnimations);
                }
            }
        }

        for (Crate b : world.crates) {
            drawCrate(b, b.x, b.y);
        }

        for (Bullet b : world.bullets) {
            drawBullet(b, b.body.getPosition().x, b.body.getPosition().y);
        }
        batch.end();

        batch.enableBlending();
        rayHandler.setCombinedMatrix(camera.combined, 0, 0, Settings.RESOLUTION.x/Settings.PPM,
                Settings.RESOLUTION.y/Settings.PPM);
        rayHandler.updateAndRender();

        batch.begin();
        for (SpawnIndicator s : world.indicators) {
            drawIndicator(s);
        }

        drawHealth(world.player.health);

        batch.setProjectionMatrix(hudCamera.combined);
        drawScore();
        if (Settings.DEV_MODE) {
            drawFPS();
        }
        batch.end();
    }
}
