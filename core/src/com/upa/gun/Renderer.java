package com.upa.gun;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

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
        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
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

            float opacity = 1.0f;
            if (world.player.iframe) {
                opacity = 0.5f;
            }
            batch.setColor(1,1,1,opacity);
            batch.draw(currentFrame, (x-currentFrame.getRegionWidth()/2), (y-currentFrame.getRegionHeight()/2),
                    currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        }
        batch.end();

        drawHealth(world.player.health);
    }

    private void drawHealth(int health) {
        batch.begin();
        int startX = 50;
        int incrementX = 40;
        int startY = 72;
        if (health > 0) {
            for (int i = 1; i <= health; i++) {
                batch.draw(Assets.heart, startX, startY, Assets.heart.getWidth()*2, Assets.heart.getHeight()*2);
                startX += incrementX;
            }
        }
        batch.end();
    }

    private void drawSlime(Slime slime, float x, float y) {
        batch.enableBlending();
        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, slime.opacity);
        TextureRegion currentFrame;
        if (slime.dying) {
            currentFrame = Assets.slimeDeathSprite;
        } else if (slime.shooting) {
            currentFrame = Assets.slimeAttackAnimations.get(slime.rotation).getKeyFrame(slime.attackTimeElapsed);
        } else {
            currentFrame = Assets.slimeMovementAnimations.get(slime.rotation).getKeyFrame(slime.timeElapsed);
        }

        batch.draw(currentFrame, (x-currentFrame.getRegionWidth()/2), (y-currentFrame.getRegionHeight()/2),
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        batch.end();
    }

    private void drawStrongSlime(StrongSlime strongSlime, float x, float y) {
        batch.enableBlending();
        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, strongSlime.opacity);
        TextureRegion currentFrame;
        if(strongSlime.dying) {
            currentFrame = Assets.strongSlimeDeathSprite;
        }
        else if (strongSlime.shooting) {
            currentFrame = Assets.strongSlimeAttackAnimations.get(strongSlime.rotation).getKeyFrame(strongSlime.attackTimeElapsed);
        }
        else {
            currentFrame = Assets.strongSlimeMovementAnimations.get(strongSlime.rotation).getKeyFrame(strongSlime.timeElapsed);
        }
        batch.draw(currentFrame, (x-currentFrame.getRegionWidth()/2), (y-currentFrame.getRegionHeight()/2),
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        batch.end();
    }

    private void drawBossSlime(BossSlime bossSlime, float x, float y) {
        batch.enableBlending();
        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, bossSlime.opacity);
        TextureRegion currentFrame;
        if (bossSlime.dying || bossSlime.hurt) {
            currentFrame = Assets.bossSlimePainSprite;
        }
        else if (bossSlime.shooting) {
            currentFrame = Assets.bossSlimeAttackAnimations.get(0).getKeyFrame(bossSlime.attackTimeElapsed);
        }
        else {
            currentFrame = Assets.bossSlimeMovementAnimations.get(0).getKeyFrame(bossSlime.timeElapsed);
        }

        currentFrame.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        batch.draw(currentFrame, x-currentFrame.getRegionWidth()*4, y-currentFrame.getRegionHeight()*4,
                currentFrame.getRegionWidth()*8, currentFrame.getRegionHeight()*8);
        batch.end();
    }

    private void drawBullet(Bullet bullet, float x, float y) {
        batch.enableBlending();

        batch.begin(); 
        bullet.bulletSprite.setX(x-bullet.bulletSprite.getRegionWidth()/2);
        bullet.bulletSprite.setY(y-bullet.bulletSprite.getRegionHeight()/2);
        bullet.bulletSprite.draw(batch);
        batch.end();
    }

    private void drawScore() {
        batch.enableBlending();

        batch.begin();

        layout.setText(font, Integer.toString(world.spawner.slimesKilled));
        int x = 30;
        int y = (int) (Settings.RESOLUTION.y - layout.height);

        font.draw(batch, layout, x, y);

        batch.end();
    }

    void draw(GunWorld world) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Array<Body> bodies = new Array<Body>();
        world.world.getBodies(bodies);

        drawBackground();
        for (Body b : bodies) {
            Object id = b.getUserData();
            if (id != null) {
                if (id instanceof Player) {
                    drawPlayer(b.getPosition().x, b.getPosition().y);
                } else if (id instanceof Bullet) {
                    Bullet bullet = (Bullet) id;
                    drawBullet(bullet, b.getPosition().x, b.getPosition().y);
                } if (id instanceof StrongSlime) {
                    StrongSlime strongSlime = (StrongSlime) id;
                    drawStrongSlime(strongSlime, b.getPosition().x, b.getPosition().y);
                } else if (id instanceof BossSlime) {
                    BossSlime bossSlime = (BossSlime) id;
                    drawBossSlime(bossSlime, b.getPosition().x, b.getPosition().y);
                } else if (id instanceof Slime) {
                    Slime slime = (Slime) id;
                    drawSlime(slime, b.getPosition().x, b.getPosition().y);
                }
            }
        }

        drawScore();
    }
}
