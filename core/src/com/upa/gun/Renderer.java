package com.upa.gun;

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

    private GunWorld world;

    private GlyphLayout layout;
    private BitmapFont font;

    Renderer(SpriteBatch batch, GunWorld world) {
        this.batch = batch;
        this.world = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        layout = new GlyphLayout();
        font = new BitmapFont();
        font.getData().setScale(4);
    }

    private void drawBackground() {
        batch.disableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(Assets.backgroundRoom1, (Settings.RESOLUTION.x - Assets.backgroundRoom1.getWidth()) / 2,
                0, Assets.backgroundRoom1.getWidth(), Assets.backgroundRoom1.getHeight());
    }

    private void drawPlayer(Player player) {
        batch.enableBlending();
        Animation<TextureRegion> currentAnimation = Assets.playerAnimations.get(player.getState()).get(player.direction);
        TextureRegion currentFrame = currentAnimation.getKeyFrame(world.player.timeElapsed);

        batch.draw(currentFrame, (player.body.getPosition().x - currentFrame.getRegionWidth()/2),
                (player.body.getPosition().y -currentFrame.getRegionHeight()/2), 0, 0,
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), 1, 1, world.player.rotation);

    }

    private void drawHealth(int health) {
        int startX = 50;
        int incrementX = 40;
        int startY = 72;
        if (health > 0) {
            for (int i = 1; i <= health; i++) {
                batch.draw(Assets.heart, startX, startY, Assets.heart.getWidth()*2, Assets.heart.getHeight()*2);
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

        batch.draw(currentFrame, (x-currentFrame.getRegionWidth()/2), (y-currentFrame.getRegionHeight()/2),
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
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
        batch.draw(currentFrame, x-currentFrame.getRegionWidth()*4, y-currentFrame.getRegionHeight()*4,
                currentFrame.getRegionWidth()*8, currentFrame.getRegionHeight()*8);
    }

    private void drawBullet(Bullet bullet, float x, float y) {
        batch.enableBlending();

        bullet.bulletSprite.setX(x-bullet.bulletSprite.getRegionWidth()/2);
        bullet.bulletSprite.setY(y-bullet.bulletSprite.getRegionHeight()/2);
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
        int x = 30;
        int y = (int) (Settings.RESOLUTION.y - layout.height);
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

        drawHealth(world.player.health);

        drawScore();
        batch.end();
    }
}
