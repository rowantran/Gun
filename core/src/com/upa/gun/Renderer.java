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

import java.util.ArrayList;

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
                0f, (float)Assets.backgroundRoom1.getWidth(), (float)Assets.backgroundRoom1.getHeight());
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
            drawSpecialHitbox(player.crateCheckHitbox);
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            //sr.rect(player.footHixbox.getX(), player.footHixbox.getY(), player.footHixbox.getWidth(), player.footHixbox.getHeight());
            sr.end();
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

    private void drawBossHealth(int health, int maxHealth, String bossName) {
        batch.begin();
        batch.enableBlending();

        //display boss health by percentage. 700px bar.

        int barWidth = 700;

        float y = 698f; //arbitrary value. not 700 b/c crate face bottoms show
        float x = Settings.RESOLUTION.x / 2; //center of screen
        Texture edge = Assets.bossHealthEdge;
        Texture full = Assets.bossHealthFull;
        Texture empty = Assets.bossHealthEmpty;

        layout.setText(font, bossName);
        font.draw(batch, layout, Settings.RESOLUTION.x / 2 - layout.width/2, 770f); //sets boss title over health bar

        double h = (double) health;
        double hMax = (double) maxHealth;
        double barsFull = (h / hMax) * (double)barWidth - (2 * (double)edge.getWidth());
        int barsToDraw = (int) barsFull;

        batch.draw(edge, x - barWidth/2, y, edge.getWidth(), edge.getHeight()); //left edge
        batch.draw(edge, x + (barWidth/2 - edge.getWidth()), y, edge.getWidth(), edge.getHeight()); //right edge

        for(int i = 0; i < barsToDraw; i++) {
            int offset = barWidth/2 - edge.getWidth() - i;
            batch.draw(full, x - offset, y, full.getWidth(), full.getHeight());
        }

        for(int i = barsToDraw; i < (double)barWidth - (2 * (double)edge.getWidth()); i++) {
            int offset = barWidth/2 - edge.getWidth() - i;
            batch.draw(empty, x - offset, y, empty.getWidth(), empty.getHeight());
        }

        batch.end();
    }

    private void drawSlimeHealth(int health, int maxHealth, float xLocation, float yLocation) {
        batch.begin();
        batch.enableBlending();

        //display slime health by percentage. 40px bar.

        int barWidth = 40;

        float y = yLocation + 42; //arbitrary value for height
        float x = xLocation + 24; //horizontal center of slime
        Texture edge = Assets.slimeHealthEdge;
        Texture full = Assets.slimeHealthFull;
        Texture empty = Assets.slimeHealthEmpty;

        double h = (double) health;
        double hMax = (double) maxHealth;
        double barsFull = (h / hMax) * (double)barWidth - (2 * (double)edge.getWidth());
        int barsToDraw = (int) barsFull;

        batch.draw(edge, x - barWidth/2, y, edge.getWidth(), edge.getHeight());
        batch.draw(edge, x + (barWidth/2 - edge.getWidth()), y, edge.getWidth(), edge.getHeight());

        for(int i = 0; i < barsToDraw; i++) {
            int offset = barWidth/2 - edge.getWidth() - i; //increments placement of next bar
            batch.draw(full, x - offset, y, full.getWidth(), full.getHeight());
        }

        for(int i = barsToDraw; i < (double)barWidth - (2 * (double)edge.getWidth()); i++) {
            int offset = barWidth/2 - edge.getWidth() - i; //increments placement of next bar
            batch.draw(empty, x - offset, y, empty.getWidth(), empty.getHeight());
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

    private void drawCrates(MapLayout map) {

        batch.begin();
        batch.enableBlending();


        ArrayList<CrateSide> crateSides = map.getCrateSides();
        for(CrateSide side : crateSides) {
            side.crateSideSprite.setX(side.x);
            side.crateSideSprite.setY(side.y);
            side.crateSideSprite.draw(batch);
        }
        ArrayList<CrateTop> crateTops = map.getCrateTops();
        for(CrateTop top : crateTops) {
            top.crateTopSprite.setX(top.getPosition().x);
            top.crateTopSprite.setY(top.getPosition().y);
            top.crateTopSprite.draw(batch);
        }
        batch.end();
        if(Settings.DEV_MODE) {
            for(CrateTop top : crateTops) {
                drawHitbox(top);
            }
        }

    }

    private void drawScore() {
        batch.enableBlending();

        layout.setText(font, Integer.toString(world.spawner.slimesKilled));
        float x = 30f;
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

        float x = 0f;
        float y = layout.height;
        font.draw(batch, layout, x, y);
    }

    private void drawHitbox(Entity e) {
        if (e.getHitbox().isActive()) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            for(Hitbox h : e.getHitbox()) {
                sr.rect(h.getX(), h.getY(), h.getWidth(), h.getHeight());
            }
            sr.end();

        }

        /*
        for (Hitbox hitbox : e.hitboxes) {
            if (hitbox.isActive()) {
                sr.setProjectionMatrix(camera.combined);
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.rect(hitbox.getPosition().x, hitbox.getPosition().y, ((RectangularHitbox) hitbox).getSize().x,
                        ((RectangularHitbox) hitbox).getSize().y);
                sr.end();
            }
        }
        */
    }

    private void drawSpecialHitbox(Hitboxes hitbox) {
        if(hitbox.isActive()) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            for(Hitbox h : hitbox) {
                sr.rect(h.getX(), h.getY(), h.getWidth(), h.getHeight());
            }
            sr.end();
        }
    }

    void draw(World world) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawBackground();
        drawCrates(World.currentMap);
        drawPlayer(World.player);

        for (Enemy e : World.enemies) {
            drawEnemy(e);
            if(e.getID() == 2) {
                drawBossHealth(e.getHealth(), e.getStartHealth(), "boss1");
            }
            else {
                drawSlimeHealth(e.getHealth(), e.getStartHealth(), e.getPosition().x, e.getPosition().y);
            }
        }

        for (Powerup p : World.powerups) {
            drawPowerup(p);
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
