package com.upa.gun;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import java.util.Map;

import static com.upa.gun.Direction.LEFT;

class Renderer {
    private SpriteBatch batch;
    OrthographicCamera camera;
    private OrthographicCamera hudCamera;

    private GunWorld world;

    private GlyphLayout layout;
    private BitmapFont font;

    private ShapeRenderer sr;

    private RayHandler rayHandler;

    private Array<Light> lights;

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

        sr = new ShapeRenderer();

        /*
        rayHandler = new RayHandler(world.world);
        rayHandler.setShadows(true);
        rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 0f);
        rayHandler.setBlurNum(3);

        lights = new Array<Light>();
        lights.add(new PointLight(rayHandler, 128, new Color(1f, 0.3f,1f,0.8f), 6, 1, 1));
        lights.add(new PointLight(rayHandler, 128, new Color(1f, 0.3f, 1f, 0.8f), 6, 8, 8));
        lights.add(new PointLight(rayHandler, 128, new Color(1f, 0.3f, 1f, 0.8f), 6, 1, 8));
        lights.add(new PointLight(rayHandler, 128, new Color(1f, 0.3f, 1f, 0.8f), 6, 8, 1));
        //lights.add(new DirectionalLight(rayHandler, 128, new Color(1f, 0.3f, 1f, 0.8f), -91));


        for (Light l : lights) {
            l.setStaticLight(false);
            l.setSoft(true);
        }
        */
    }

    private void drawBackground() {
        batch.disableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(Assets.backgroundRoom1, (Settings.RESOLUTION.x - Assets.backgroundRoom1.getWidth()) /2f/Settings.PPM,
                0, (float)Assets.backgroundRoom1.getWidth()/Settings.PPM, (float)Assets.backgroundRoom1.getHeight()/Settings.PPM);
    }

    private void drawShadow(float x, float y, float objectWidth) {
        float shadowHeight = objectWidth / 3f;

        float shadowY = y - (shadowHeight*0.7f);
        batch.draw(Assets.shadow, x, shadowY, objectWidth, shadowHeight);
    }

    private void drawPlayer(Player player) {
        batch.enableBlending();
        Animation<TextureRegion> currentAnimation = Assets.playerAnimations.get(player.getState()).get(player.direction);
        TextureRegion currentFrame = currentAnimation.getKeyFrame(world.player.timeElapsed);

        float playerX = (player.body.getPosition().x - (float)currentFrame.getRegionWidth()/2f/Settings.PPM);
        float playerY = (player.body.getPosition().y - (float)currentFrame.getRegionHeight()/2f/Settings.PPM);

        batch.setColor(1.0f, 1.0f, 1.0f, player.opacity);
        drawShadow(playerX, playerY, (float)currentFrame.getRegionWidth()/Settings.PPM);
        batch.draw(currentFrame, playerX, playerY, 0, 0,
                (float)currentFrame.getRegionWidth()/Settings.PPM, (float)currentFrame.getRegionHeight()/Settings.PPM,
                1, 1, world.player.rotation);
}

    private void drawHealth(int health) {
        int startX = 50;
        int incrementX = 32;
        int startY = 72;
        if (health > 0) {
            batch.draw(Assets.healthFullLeft, startX, startY, Assets.healthFullLeft.getWidth(),
                    Assets.healthFullLeft.getHeight());
            startX += incrementX;
        } else {
            batch.draw(Assets.healthEmptyLeft, startX, startY, Assets.healthEmptyLeft.getWidth(),
                    Assets.healthEmptyLeft.getHeight());
            startX += incrementX;
        }
        for (int i = 2; i < Settings.PLAYER_HEALTH; i++) {
            if (i <= health) {
                batch.draw(Assets.healthFullMid, startX, startY, Assets.healthFullMid.getWidth(),
                        Assets.healthFullMid.getHeight());
                startX += incrementX;
            } else {
                batch.draw(Assets.healthEmptyMid, startX, startY, Assets.healthEmptyMid.getWidth(),
                        Assets.healthFullMid.getHeight());
                startX += incrementX;
            }
        }
        if (health == Settings.PLAYER_HEALTH) {
            batch.draw(Assets.healthFullRight, startX, startY, Assets.healthFullRight.getWidth(),
                    Assets.healthFullRight.getHeight());
        } else {
            batch.draw(Assets.healthEmptyRight, startX, startY, Assets.healthEmptyRight.getWidth(),
                    Assets.healthEmptyRight.getHeight());
        }
    }

    private void drawSlime(Slime slime, float x, float y, Map<SpriteState, Map<Direction, Animation<TextureRegion>>> animationMap) {
        batch.enableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, slime.opacity);

        SpriteState state = slime.getState();
        Animation<TextureRegion> currentAnimation = animationMap.get(state).get(LEFT);
        TextureRegion currentFrame;

        if (state == SpriteState.ATTACKING) {
            currentFrame = currentAnimation.getKeyFrame(slime.attackTimeElapsed);
        } else {
            currentFrame = currentAnimation.getKeyFrame(slime.timeElapsed);
        }

        float slimeX = (x-(float)currentFrame.getRegionWidth()/2f/Settings.PPM);
        float slimeY =  (y-(float)currentFrame.getRegionHeight()/2f/Settings.PPM);

        drawShadow(slimeX, slimeY, (float)currentFrame.getRegionWidth()/Settings.PPM);
        batch.draw(currentFrame, slimeX, slimeY,
                (float)currentFrame.getRegionWidth()/Settings.PPM, (float)currentFrame.getRegionHeight()/Settings.PPM);
    }

    private void drawBossSlime(BossSlime bossSlime, float x, float y) {
        batch.enableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, bossSlime.opacity);

        SpriteState state = bossSlime.getState();
        Animation<TextureRegion> currentAnimation = Assets.bossSlimeAnimations.get(state).get(LEFT);
        TextureRegion currentFrame;

        if (state == SpriteState.ATTACKING) {
            currentFrame = currentAnimation.getKeyFrame(bossSlime.attackTimeElapsed);
        } else {
            currentFrame = currentAnimation.getKeyFrame(bossSlime.timeElapsed);
        }

        float slimeX = x-(float)currentFrame.getRegionWidth()*4f/Settings.PPM;
        float slimeY = y-(float)currentFrame.getRegionHeight()*4f/Settings.PPM;
        float slimeWidth = (float)currentFrame.getRegionWidth()*8f/Settings.PPM;
        float slimeHeight = (float)currentFrame.getRegionHeight()*8f/Settings.PPM;

        drawShadow(slimeX, slimeY, slimeWidth);
        currentFrame.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        batch.draw(currentFrame, slimeX, slimeY, slimeWidth, slimeHeight);
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
        font.draw(batch, layout, x, y);
    }

    private void drawIndicator(SpawnIndicator s) {
        batch.draw(Assets.crosshair, s.x*Settings.PPM, s.y*Settings.PPM,
                Assets.crosshair.getWidth()*2, Assets.crosshair.getHeight()*2);
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

        drawPlayer(world.player);

        batch.end();

        /*batch.enableBlending();
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
        */

        // Set projection matrix to pixels instead of meters
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        for (SpawnIndicator s : world.indicators) {
            drawIndicator(s);
        }
        drawHealth(world.player.health);
        drawScore();
        if (Settings.DEV_MODE) {
            drawFPS();
        }
        batch.end();

        if (world.cinematicHappening) {
            sr.setProjectionMatrix(hudCamera.combined);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.BLACK);
            sr.rect(0, 0, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.rect(0, Settings.RESOLUTION.y*0.8f, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.end();
        }
    }
}
