package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Map;

import static com.upa.gun.Direction.LEFT;

class Renderer {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private World world;

    private GlyphLayout layout;
    private BitmapFont font;

    private ShapeRenderer sr;

    Renderer(SpriteBatch batch, World world) {
        this.batch = batch;
        this.world = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        layout = new GlyphLayout();
        font = new BitmapFont();
        font.getData().setScale(4);

        sr = new ShapeRenderer();
    }

    private void drawBackground() {
        batch.begin();
        batch.disableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(Assets.backgroundRoom1, (Settings.RESOLUTION.x - Assets.backgroundRoom1.getWidth()) /2f,
                0, (float)Assets.backgroundRoom1.getWidth(), (float)Assets.backgroundRoom1.getHeight());
        batch.end();
    }

    private void drawShadow(float x, float y, float objectWidth) {
        float shadowHeight = objectWidth / 3f;

        float shadowY = y - (shadowHeight*0.7f);
        batch.draw(Assets.shadow, x, shadowY, objectWidth, shadowHeight);
    }

    private void drawPlayer(Player player) {
        batch.begin();
        batch.enableBlending();
        Animation<TextureRegion> currentAnimation = Assets.playerAnimations.get(player.getState()).get(player.direction);
        TextureRegion currentFrame = currentAnimation.getKeyFrame(World.player.state.timeElapsed);

        Vector2 playerPos = player.getPosition();
        float playerX = (playerPos.x - (float)currentFrame.getRegionWidth()/2f);
        float playerY = (playerPos.y - (float)currentFrame.getRegionHeight()/2f);

        batch.setColor(1.0f, 1.0f, 1.0f, player.state.opacity);
        drawShadow(playerX, playerY, (float)currentFrame.getRegionWidth());
        batch.draw(currentFrame, playerX, playerY, 0, 0,
                (float)currentFrame.getRegionWidth(), (float)currentFrame.getRegionHeight(),
                1, 1, World.player.state.rotation);

        batch.end();

        if (Settings.DEV_MODE) {
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.rect(player.getPosition().x, player.getPosition().y, player.hitbox.getWidth(), player.hitbox.getHeight());
            sr.end();
        }
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

    private void drawEnemy(Enemy e) {
        batch.begin();
        batch.enableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, e.opacity);

        SpriteState state = e.getState();
        Animation<TextureRegion> animation = e.sprite().get(state).get(LEFT);

        TextureRegion frame;
        if (e.state == SpriteState.ATTACKING) {
            frame = animation.getKeyFrame(e.attackTimeElapsed);
        } else {
            frame = animation.getKeyFrame(e.timeElapsed);
        }

        drawShadow(e.getPosition().x, e.getPosition().y, 20);
        batch.draw(frame, e.getPosition().x, e.getPosition().y, e.getSize().x, e.getSize().y);
        batch.end();

        if (Settings.DEV_MODE) {
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.rect(e.getPosition().x, e.getPosition().y, e.hitbox.getWidth(), e.hitbox.getHeight());
            sr.end();
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

        float slimeX = (x-(float)currentFrame.getRegionWidth()/2f);
        float slimeY =  (y-(float)currentFrame.getRegionHeight()/2f);

        drawShadow(slimeX, slimeY, (float)currentFrame.getRegionWidth());
        batch.draw(currentFrame, slimeX, slimeY,
                (float)currentFrame.getRegionWidth(), (float)currentFrame.getRegionHeight());
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

        float slimeX = x-(float)currentFrame.getRegionWidth()*4f;
        float slimeY = y-(float)currentFrame.getRegionHeight()*4f;
        float slimeWidth = (float)currentFrame.getRegionWidth()*8f;
        float slimeHeight = (float)currentFrame.getRegionHeight()*8f;

        drawShadow(slimeX, slimeY, slimeWidth);
        currentFrame.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        batch.draw(currentFrame, slimeX, slimeY, slimeWidth, slimeHeight);
    }

    private void drawBullet(Bullet bullet) {
        batch.begin();
        batch.enableBlending();
        Vector2 pos = bullet.getPosition();
        batch.draw(Assets.bulletEnemy, pos.x, pos.y, 10, 10);
        batch.end();

        if (Settings.DEV_MODE) {
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.rect(bullet.getPosition().x, bullet.getPosition().y, bullet.hitbox.getWidth(), bullet.hitbox.getHeight());
            sr.end();
        }
    }

    private void drawCrate(Crate crate, float x, float y) {
        batch.begin();
        batch.enableBlending();

        crate.crateSprite.setX(x);
        crate.crateSprite.setY(y);
        crate.crateSprite.draw(batch);

        batch.end();
    }

    private void drawScore() {
        batch.enableBlending();

        layout.setText(font, Integer.toString(world.spawner.slimesKilled));
        float x = 30;
        float y = (Settings.RESOLUTION.y - layout.height);
        font.draw(batch, layout, x, y);
    }

    private void drawIndicator(SpawnIndicator s) {
        batch.draw(Assets.crosshair, s.x, s.y,
                Assets.crosshair.getWidth()*2, Assets.crosshair.getHeight()*2);
    }

    private void drawFPS() {
        batch.enableBlending();

        layout.setText(font, Integer.toString(Gdx.graphics.getFramesPerSecond()));

        float x = 0;
        float y = layout.height;
        font.draw(batch, layout, x, y);
    }

    void draw(World world) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawBackground();
        drawPlayer(World.player);

        for (Enemy e : World.enemies) {
            drawEnemy(e);
        }

        for (Enemy e : World.bosses) {
            drawEnemy(e);
        }

        for (Crate b : World.crates) {
            drawCrate(b, b.x, b.y);
            //System.out.println("crate");
        }

        for (Bullet b : World.playerBullets) {
            drawBullet(b);
        }

        for (Bullet b : World.enemyBullets) {
            drawBullet(b);
        }

        batch.begin();
        for (SpawnIndicator s : World.indicators) {
            drawIndicator(s);
        }

        drawHealth(World.player.getHealth());
        drawScore();

        if (Settings.DEV_MODE) {
            drawFPS();
        }

        batch.end();

        if (world.cinematicHappening) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.BLACK);
            sr.rect(0, 0, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.rect(0, Settings.RESOLUTION.y*0.8f, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.end();
        }
    }
}
