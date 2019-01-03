package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.SpawnIndicator;
import com.upa.gun.enemy.Powerup;

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

        batch.setColor(1.0f, 1.0f, 1.0f, player.state.opacity);
        drawShadow(playerPos.x, playerPos.y, (float)currentFrame.getRegionWidth());
        batch.draw(currentFrame, playerPos.x, playerPos.y, 0, 0, player.getSize().x, player.getSize().y,
                1, 1, World.player.state.rotation);

        batch.end();

        if (Settings.DEV_MODE) {
            drawHitbox(player);
        }
}

    private void drawHealth(int health) {
        int startX = 50;
        int incrementX = Assets.healthFullLeft.getRegionWidth();
        int startY = 72;
        if (health > 0) {
            batch.draw(Assets.healthFullLeft, startX, startY, Assets.healthFullLeft.getRegionWidth(),
                    Assets.healthFullLeft.getRegionHeight());
            startX += incrementX;
        } else {
            batch.draw(Assets.healthEmptyLeft, startX, startY, Assets.healthEmptyLeft.getRegionWidth(),
                    Assets.healthEmptyLeft.getRegionHeight());
            startX += incrementX;
        }
        for (int i = 2; i < Settings.playerHealth; i++) {
            if (i <= health) {
                batch.draw(Assets.healthFullMid, startX, startY, Assets.healthFullMid.getRegionWidth(),
                        Assets.healthFullMid.getRegionHeight());
                startX += incrementX;
            } else {
                batch.draw(Assets.healthEmptyMid, startX, startY, Assets.healthEmptyMid.getRegionWidth(),
                        Assets.healthFullMid.getRegionHeight());
                startX += incrementX;
            }
        }
        if (health == Settings.playerHealth) {
            batch.draw(Assets.healthFullRight, startX, startY, Assets.healthFullRight.getRegionWidth(),
                    Assets.healthFullRight.getRegionHeight());
        } else {
            batch.draw(Assets.healthEmptyRight, startX, startY, Assets.healthEmptyRight.getRegionWidth(),
                    Assets.healthEmptyRight.getRegionHeight());
        }
    }

    private void drawBossHealth(int health, int maxHealth) {
        batch.begin();
        batch.enableBlending();

        //display boss health by percentage. 506px bar.

        float y = 700;
        Texture edge = Assets.bossHealthEdge;
        Texture full = Assets.bossHealthFull;
        Texture empty = Assets.bossHealthEmpty;

        layout.setText(font, "BIG OL' BAD OL' BOSS");
        font.draw(batch, layout, Settings.RESOLUTION.x / 2 - layout.width/2, 770);

        double h = (double) health;
        double hMax = (double) maxHealth;
        double barsFull = (h / hMax) * 500;
        int barsToDraw = (int) barsFull;

        batch.draw(edge, Settings.RESOLUTION.x/2 - 253, y, edge.getWidth(), edge.getHeight()); //left edge
        batch.draw(edge, Settings.RESOLUTION.x/2 + 250, y, edge.getWidth(), edge.getHeight()); //right edge

        for(int i = 0; i < barsToDraw; i++) {
            int offset = -250 + i;
            batch.draw(full, Settings.RESOLUTION.x/2 + offset, y, full.getWidth(), full.getHeight());
        }

        for(int i = barsToDraw; i < 500; i++) {
            int offset = -250 + i;
            batch.draw(empty, Settings.RESOLUTION.x/2 + offset, y, empty.getWidth(), empty.getHeight());
        }
        
        batch.end();
    }

    private void drawPowerup(Powerup p) {
        batch.begin();
        batch.enableBlending();
        batch.draw(Assets.powerup1, p.getPosition().x, p.getPosition().y, p.getSize().x, p.getSize().y);
        batch.end();
        if(Settings.DEV_MODE) {
            drawHitbox(p);
        }

    }

    private void drawEnemy(Enemy e) {
        batch.begin();
        batch.enableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, e.opacity);

        if (e.damagedFrame) {
            batch.setShader(Assets.flashWhiteShader);
        }

        Animation<TextureRegion> animation = Assets.getAnimation(new AnimationKey("sprites/enemies.atlas",
                e.sprites.get(e.sprite)));

        TextureRegion frame = animation.getKeyFrame(e.timeElapsed);

        //Gdx.app.debug("Renderer", "Enemy has time elapsed of " + e.timeElapsed);

        drawShadow(e.getPosition().x, e.getPosition().y, e.getSize().x);
        batch.draw(frame, e.getPosition().x, e.getPosition().y, e.getSize().x, e.getSize().y);

        batch.setShader(null);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.end();

        if (Settings.DEV_MODE) {
            drawHitbox(e);
        }
    }



    private void drawBullet(Bullet bullet) {
        batch.begin();
        batch.enableBlending();
        Vector2 pos = bullet.getPosition();
        batch.draw(Assets.bulletEnemy, pos.x, pos.y, bullet.getSize().x, bullet.getSize().y);
        batch.end();

        if (Settings.DEV_MODE) {
            drawHitbox(bullet);
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

    private void drawHitbox(Entity e) {
        if (e.getHitbox().isActive()) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.rect(e.getHitbox().getX(), e.getHitbox().getY(), e.getHitbox().getWidth(), e.getHitbox().getHeight());
            sr.end();
        }
    }

    void draw(World world) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawBackground();
        drawPlayer(World.player);

        for (Enemy e : World.enemies) {
            drawEnemy(e);
            if(e.getID() == 2) {
                drawBossHealth(e.getHealth(), e.getStartHealth());
            }
        }

        for (Powerup p : World.powerups) {
            drawPowerup(p);
        }

        for (Crate c : World.crates) {
            drawCrate(c, c.x, c.y);
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

        // Draw powerups attached to player
        for (Powerup p : new Array.ArrayIterator<Powerup>(World.player.powerupsActive)) {
            drawPowerup(p);
        }

        if (world.cinematicHappening) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.BLACK);
            sr.rect(0, 0, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.rect(0, Settings.RESOLUTION.y*0.8f, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.end();
        }
    }

    /*
    Unused code

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
    */
}
