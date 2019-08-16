package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.SpawnIndicator;
import com.upa.gun.enemy.Powerup;

import javax.xml.soap.Text;
import java.util.ArrayList;

/**
 * Class to draw frames
 */
class Renderer {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World world;
    private GlyphLayout layout;
    private BitmapFont font;
    private ShapeRenderer sr;
    private Stage pauseStage;

    Renderer(SpriteBatch batch, World world) {
        this.batch = batch;
        this.world = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        pauseStage = new Stage();
        layout = new GlyphLayout();
        font = new BitmapFont();

        sr = new ShapeRenderer();

        generatePauseElements();
        disableAllButtons();
    }

    private void generatePauseElements() {

        float xCenter = Settings.RESOLUTION.x/2;
        float yCenter = Settings.RESOLUTION.y/2;

        Gdx.input.setInputProcessor(pauseStage);
        Skin skin = new Skin(Assets.buttonSkins);

        TextButton.TextButtonStyle longStyle = new TextButton.TextButtonStyle();
        longStyle.font = font;
        longStyle.up = skin.getDrawable("up-button-long");
        longStyle.down = skin.getDrawable("down-button-long");
        longStyle.over = skin.getDrawable("over-button-long");
        longStyle.checked = skin.getDrawable("checked-button-long");

        TextButton.TextButtonStyle shortStyle = new TextButton.TextButtonStyle();
        shortStyle.font = font;
        shortStyle.up = skin.getDrawable("up-button-short");
        shortStyle.down = skin.getDrawable("down-button-short");
        shortStyle.over = skin.getDrawable("over-button-short");
        shortStyle.checked = skin.getDrawable("checked-button-short");

        TextButton back = new TextButton("Return to game", longStyle);
        back.getLabel().setFontScale(2f);
        back.setPosition(xCenter - (back.getWidth()/2), (yCenter + (Settings.PAUSE_SCREEN_RESOLUTION.y / 2)) - 150 - back.getHeight());
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                World.activity = 0;
                disableAllButtons();
            }
        });

        TextButton stats = new TextButton("Stats", shortStyle);
        stats.getLabel().setFontScale(2f);
        stats.setPosition(back.getX(), back.getY() - stats.getHeight() - Settings.BUTTON_INCREMENT - 20);
        stats.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                World.activity = 2;
                disableAllButtons();
            }
        });

        TextButton progress = new TextButton("Progress", shortStyle);
        progress.getLabel().setFontScale(2f);
        progress.setPosition(back.getX() + back.getWidth() - progress.getWidth(),
                back.getY() - progress.getHeight() - Settings.BUTTON_INCREMENT - 20);
        progress.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                World.activity = 3;
                disableAllButtons();
            }
        });

        TextButton settings = new TextButton("Settings", shortStyle);
        settings.getLabel().setFontScale(2f);
        settings.setPosition(back.getX(), stats.getY() - settings.getHeight() - Settings.BUTTON_INCREMENT);
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                World.activity = 4;
                disableAllButtons();
            }
        });

        TextButton quit = new TextButton("Quit", shortStyle);
        quit.getLabel().setFontScale(2f);
        quit.setPosition(back.getX() + back.getWidth() - quit.getWidth(),
                progress.getY() - quit.getHeight() - Settings.BUTTON_INCREMENT);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

        pauseStage.addActor(back);
        pauseStage.addActor(stats);
        pauseStage.addActor(progress);
        pauseStage.addActor(settings);
        pauseStage.addActor(quit);
    }

    public void disableAllButtons() {
        for(Actor a : pauseStage.getActors()) {
            if(a instanceof TextButton) {
                ((TextButton)a).setDisabled(true);
            }
        }
    }

    public void enablePauseButtons() {
        for(Actor a : pauseStage.getActors()) {
            if(a instanceof TextButton) {
                ((TextButton)a).setDisabled(false);
            }
        }
    }

    /**
     * Draws the floor of the stage
     */
    private void drawBackground() {
        batch.begin();
        batch.disableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.draw(Assets.floor, Settings.RESOLUTION.x/2 - Assets.floor.getWidth()/2,
                Settings.RESOLUTION.y/2 - Assets.floor.getHeight()/2, (float)Assets.floor.getWidth(),
                (float)Assets.floor.getHeight());
        batch.end();
    }

    /**
     * Draws the black border
     */
    private void drawBorder() {
        batch.begin();
        batch.enableBlending();
        batch.setColor(1.0f,1.0f,1.0f,1.0f);
        batch.draw(Assets.border, 0f, 0f, (float)Assets.border.getWidth(), (float)Assets.border.getHeight());
        batch.end();
    }

    /**
     * Draws shadows below entities
     * @param x - X coordinate of shadow
     * @param y - Y coordinate of shadow
     * @param objectWidth - Width of object to which shadow is assigned
     */
    private void drawShadow(float x, float y, float objectWidth) {
        float shadowHeight = objectWidth / 3f;

        float shadowY = y - (shadowHeight*0.7f);
        batch.draw(Assets.shadow, x, shadowY, objectWidth, shadowHeight);
    }

    /**
     * Draws the player
     * @param player - Player entity
     */
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
    }

    /**
     * Draws the health of the player
     * @param health - Current health
     */
    private void drawPlayerHealth(int health) {
        batch.begin();
        int startX = 50;
        int startY = 72;
        int incrementX = Assets.healthFullLeft.getRegionWidth();
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
        batch.end();
    }

    /**
     * Draws the health of a boss
     * @param health - Current health
     * @param maxHealth - Maximum health
     * @param bossName - Name of boss to display above health bar
     */
    private void drawBossHealth(int health, int maxHealth, String bossName) {
        batch.begin();
        batch.enableBlending();

        int barWidth = 700;
        float y = 698f;
        float x = Settings.RESOLUTION.x / 2f;
        Texture edge = Assets.bossHealthEdge;
        Texture full = Assets.bossHealthFull;
        Texture empty = Assets.bossHealthEmpty;

        font.getData().setScale(3f);
        layout.setText(font, bossName);
        font.draw(batch, layout, Settings.RESOLUTION.x / 2 - layout.width/2, 770f);

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

    /**
     * Draws the health of an enemy
     * @param health - Current health
     * @param maxHealth - Maximum health
     * @param xLocation - X coordinate of enemy
     * @param yLocation - Y coordinate of enemy
     */
    private void drawEnemyHealth(int health, int maxHealth, float xLocation, float yLocation) {
        batch.begin();
        batch.enableBlending();

        int barWidth = 40;
        float y = yLocation + 42;
        float x = xLocation + 24;
        Texture edge = Assets.slimeHealthEdge;
        Texture full = Assets.slimeHealthFull;
        Texture empty = Assets.slimeHealthEmpty;

        double h = (double) health;
        double hMax = (double) maxHealth;
        double barsFull = (h / hMax) * (double)barWidth - (2 * (double)edge.getWidth());
        int barsToDraw = (int) barsFull;

        if(barsToDraw < 0) {
            barsToDraw = 0;
        }

        for(int i = 0; i < barsToDraw; i++) {
            int offset = barWidth/2 - edge.getWidth() - i; //increments placement of next bar
            batch.draw(full, x - offset, y, full.getWidth(), full.getHeight());
        }
        for(int i = barsToDraw; i < (double)barWidth - (2 * (double)edge.getWidth()); i++) {
            int offset = barWidth/2 - edge.getWidth() - i; //increments placement of next bar
            batch.draw(empty, x - offset, y, empty.getWidth(), empty.getHeight());
        }

        batch.draw(edge, x - barWidth/2, y, edge.getWidth(), edge.getHeight());
        batch.draw(edge, x + (barWidth/2 - edge.getWidth()), y, edge.getWidth(), edge.getHeight());
        batch.end();
    }

    /**
     * Draws powerup
     * @param p - Powerup
     */
    private void drawPowerup(Powerup p) {
        batch.begin();
        batch.enableBlending();
        batch.draw(Assets.powerup1, p.getPosition().x, p.getPosition().y, p.getSize().x, p.getSize().y);
        batch.end();
    }

    /**
     *  Draws enemy
     * @param e - Enemy
     */
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

        drawShadow(e.getPosition().x, e.getPosition().y, e.getSize().x);
        batch.draw(frame, e.getPosition().x, e.getPosition().y, e.getSize().x, e.getSize().y);
        batch.setShader(null);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.end();
    }

    /**
     * Draws bullet
     * @param b - Bullet
     */
    private void drawBullet(Bullet b) {
        batch.begin();
        batch.enableBlending();
        Vector2 pos = b.getPosition();
        batch.draw(Assets.bulletEnemy, pos.x, pos.y, b.getSize().x, b.getSize().y);
        batch.end();
    }

    /**
     * Draws crate
     * @param c - Crate
     */
    private void drawCrate(Crate c) {
        batch.begin();
        batch.enableBlending();
        c.crateTopSprite.setPosition(c.getPosition().x, c.getPosition().y + 27);
        c.crateSideSprite.setPosition(c.getPosition().x, c.getPosition().y);
        c.crateTopSprite.draw(batch);
        if(c.getDisplaySide()) {
            c.crateSideSprite.draw(batch);
        }
        batch.end();
    }

    /**
     * Draws door
     * @param d - Door
     */
    private void drawDoor(Door d) {
        batch.begin();
        batch.enableBlending();
        if(!World.doorsOpen) {
            switch (d.getDirection()) {
                case 1:
                    d.doorTopSprite.setPosition(d.getPosition().x, d.getPosition().y + 27 + 48);
                    d.doorSideSprite.setPosition(d.getPosition().x, d.getPosition().y + 48);
                    break;
                case 2:
                    d.doorTopSprite.setPosition(d.getPosition().x, d.getPosition().y + 27);
                    d.doorSideSprite.setPosition(d.getPosition().x, d.getPosition().y);
                    break;
                case 3:
                    d.doorTopSprite.setPosition(d.getPosition().x - 24, d.getPosition().y + 27 + 24);
                    break;
                case 4:
                    d.doorTopSprite.setPosition(d.getPosition().x + 24, d.getPosition().y + 27 + 24);
                    break;
                default:
                    break;
            }
            d.doorTopSprite.draw(batch);
            if (d.getDisplaySide()) {
                d.doorSideSprite.draw(batch);
            }
        }
        batch.end();
    }

    /**
     * Draws score
     */
    private void drawScore() {
        batch.begin();
        batch.enableBlending();
        font.getData().setScale(4f);
        layout.setText(font, Integer.toString(world.spawner.slimesKilled));
        float x = 30f;
        float y = (Settings.RESOLUTION.y - layout.height);
        font.draw(batch, layout, x, y);
        batch.end();
    }

    /**
     * Draws spawn indicator
     * @param s - Indicator
     */
    private void drawIndicator(SpawnIndicator s) {
        batch.begin();
        batch.draw(Assets.crosshair, s.x, s.y,
                Assets.crosshair.getWidth()*2, Assets.crosshair.getHeight()*2);
        batch.end();
    }

    /**
     * Draws FPS
     */
    private void drawFPS() {
        batch.begin();
        batch.enableBlending();
        font.getData().setScale(4f);
        layout.setText(font, Integer.toString(Gdx.graphics.getFramesPerSecond()));
        float x = 0f;
        float y = layout.height;
        font.draw(batch, layout, x, y);
        batch.end();
    }

    /**
     * Draws an entity's main hitbox
     * @param e - Entity
     */
    private void drawHitbox(Entity e) {
        if (e.getHitbox().isActive()) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.WHITE);
            for(Hitbox h : e.getHitbox()) {
                if (h.isActive()) {
                    sr.rect(h.getX(), h.getY(), h.getWidth(), h.getHeight());
                }
            }
            sr.end();
        }
    }

    /**
     * Draws a specific hitbox
     * @param hitboxes - Hitboxes to be drawn
     */
    private void drawSpecialHitbox(Hitboxes hitboxes) {
        if(hitboxes.isActive()) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.WHITE);
            for(Hitbox h : hitboxes) {
                if (h.isActive()) {
                    sr.rect(h.getX(), h.getY(), h.getWidth(), h.getHeight());
                }
            }
            sr.end();
        }
    }

    /**
     * Sorts entities by y value
     * @param entities - List of entities to be sorted
     * @param n - List size
     */
    private void mergeSortEntities(ArrayList<Entity> entities, int n) {
        if(n < 2) {
            return;
        }
        int mid = n/2;
        ArrayList<Entity> l = new ArrayList<Entity>();
        ArrayList<Entity> r = new ArrayList<Entity>();

        for(int i = 0; i < mid; i++) {
            l.add(entities.get(i));
        }
        for(int i = mid; i < n; i++) {
            r.add(entities.get(i));
        }

        mergeSortEntities(l, mid);
        mergeSortEntities(r, n-mid);
        mergeEntities(entities, l, r, mid, n-mid);
    }

    /**
     * Merge sort method
     * @param entities - List of entities
     * @param l - List of left side entities
     * @param r - List of right side entities
     * @param left - Left index
     * @param right - Right index
     */
    private void mergeEntities(ArrayList<Entity> entities, ArrayList<Entity> l, ArrayList<Entity> r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while(i < left && j < right) {
            if(l.get(i).getPosition().y > r.get(j).getPosition().y) {
                entities.set(k++, l.get(i++));
            }
            else {
                entities.set(k++, r.get(j++));
            }
        }
        while(i < left) {
            entities.set(k++, l.get(i++));
        }
        while(j < right) {
            entities.set(k++, r.get(j++));
        }
    }

    /**
     * Draws entities with those that have a lower y value in front
     * @param entityList - List of entities
     */
    private void drawLayered(ArrayList<Entity> entityList) {
        mergeSortEntities(entityList, entityList.size());
        for(Entity e : entityList) {
            if(e instanceof Player) {
                drawPlayer((Player)e);
            }
            else if(e instanceof Crate) {
                drawCrate((Crate)e);
            }
            else if(e instanceof Door) {
                drawDoor((Door)e);
            }
            else if(e instanceof Enemy) {
                drawEnemy((Enemy)e);
                if(((Enemy)e).getID() == 2) {
                    drawBossHealth(((Enemy)e).getHealth(), ((Enemy)e).getStartHealth(), "boss1");
                }
                else {
                    drawEnemyHealth(((Enemy)e).getHealth(), ((Enemy)e).getStartHealth(), e.getPosition().x, e.getPosition().y);
                }
            }
            else if(e instanceof Bullet) {
                drawBullet((Bullet)e);
            }
            else if(e instanceof Powerup) {

            }
            else {
                Gdx.app.log("Renderer", "Invalid entity found in entity list");
            }
        }
    }


    private void drawPauseBox() {
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        sr.setColor(20/255f, 20/255f, 20/255f, 0.85f);
        sr.rect(Settings.RESOLUTION.x/2 - Settings.PAUSE_SCREEN_RESOLUTION.x/2, Settings.RESOLUTION.y/2 - Settings.PAUSE_SCREEN_RESOLUTION.y/2,
                Settings.PAUSE_SCREEN_RESOLUTION.x, Settings.PAUSE_SCREEN_RESOLUTION.y);
        sr.end();
    }

    private void drawPauseScreen() {

        float xCenter = Settings.RESOLUTION.x/2;
        float yCenter = Settings.RESOLUTION.y/2;

        drawPauseBox();

        batch.begin();
        batch.enableBlending();
        font.getData().setScale(3f);
        layout.setText(font, "PAUSED");
        font.draw(batch, layout, xCenter-layout.width/2, yCenter + Settings.PAUSE_SCREEN_RESOLUTION.y/2 - layout.height);
        batch.end();

        pauseStage.draw();

    }

    private void drawStatsScreen() {

        float xCenter = Settings.RESOLUTION.x/2;
        float yCenter = Settings.RESOLUTION.y/2;

        float textYStart = yCenter + Settings.PAUSE_SCREEN_RESOLUTION.y/2 - 100;
        float yIncrement = 30f;
        float textXStart = xCenter - Settings.PAUSE_SCREEN_RESOLUTION.x/2 + 30;
        float textXStart2 = xCenter;

        drawPauseBox();

        batch.begin();
        batch.enableBlending();
        font.getData().setScale(3f);
        layout.setText(font, "STATS");
        font.draw(batch, layout, xCenter - layout.width/2, yCenter + Settings.PAUSE_SCREEN_RESOLUTION.y/2 - layout.height);

        font.getData().setScale(1.5f);
        layout.setText(font, "Attack speed: " + (10/Settings.playerBulletCooldown));
        font.draw(batch, layout, textXStart, textYStart);
        layout.setText(font, "Bullet speed: " + Settings.playerBulletSpeed);
        font.draw(batch, layout, textXStart2, textYStart);
        layout.setText(font, "Damage: " + Settings.playerDamage);
        font.draw(batch, layout, textXStart, textYStart - yIncrement);
        layout.setText(font, "Health: " + Settings.playerHealth);
        font.draw(batch, layout, textXStart2, textYStart - yIncrement);
        layout.setText(font, "Speed: " + Settings.playerSpeed);
        font.draw(batch, layout, textXStart, textYStart - 2 * yIncrement);

        batch.end();
    }

    private void drawProgressScreen() {

        float xCenter = Settings.RESOLUTION.x/2;
        float yCenter = Settings.RESOLUTION.y/2;

        float textYStart = yCenter + Settings.PAUSE_SCREEN_RESOLUTION.y/2 - 100;
        float yIncrement = 30f;
        float textXStart = xCenter - Settings.PAUSE_SCREEN_RESOLUTION.x/2 + 30;
        float textXStart2 = xCenter;

        drawPauseBox();

        batch.begin();
        batch.enableBlending();
        font.getData().setScale(3f);
        layout.setText(font, "PROGRESS");
        font.draw(batch, layout, xCenter - layout.width/2, yCenter + Settings.PAUSE_SCREEN_RESOLUTION.y/2 - layout.height);

        font.getData().setScale(1.5f);
        layout.setText(font, "Purple slimes killed: ");
        font.draw(batch, layout, textXStart, textYStart);
        font.getData().setScale(1.5f);
        layout.setText(font, "Green slimes killed: ");
        font.draw(batch, layout, textXStart2, textYStart);

        batch.end();

    }

    private void drawSettingsScreen() {

        float xCenter = Settings.RESOLUTION.x/2;
        float yCenter = Settings.RESOLUTION.y/2;

        float textYStart = yCenter + Settings.PAUSE_SCREEN_RESOLUTION.y/2 - 100;
        float yIncrement = 30f;
        float textXStart = xCenter - Settings.PAUSE_SCREEN_RESOLUTION.x/2 + 30;
        float textXStart2 = xCenter;

        drawPauseBox();

        batch.begin();
        batch.enableBlending();
        font.getData().setScale(3f);
        layout.setText(font, "Settings");
        font.draw(batch, layout, xCenter - layout.width/2, yCenter + Settings.PAUSE_SCREEN_RESOLUTION.y/2 - layout.height);

        batch.end();
    }


    /**
     * Draws necessary elements
     * @param world
     */
    void draw(World world) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawBackground();

        ArrayList<Entity> entityList = new ArrayList<Entity>();
        entityList.add(World.player);
        for(Crate c : World.currentMap.getCrates()) {
            entityList.add(c);
        }
        for(Enemy e : World.enemies) {
            entityList.add(e);
        }
        for(Bullet b : World.playerBullets) {
            entityList.add(b);
        }
        for(Bullet b : World.enemyBullets) {
            entityList.add(b);
        }
        for(Door d : World.currentMap.getDoors()) {
            entityList.add(d);
        }
        drawLayered(entityList);

        if(Settings.DEV_MODE) {
            for(Entity e : entityList) {
                drawHitbox(e);
            }
            drawSpecialHitbox(World.player.cCheckHitbox);
            for(Enemy e : World.enemies) {
                drawSpecialHitbox(e.cCheckHitbox);
            }
        }

        if(World.roomChange != 0) {
            drawLayered(World.oldEntities);
        }

        for (SpawnIndicator s : World.indicators) {
            drawIndicator(s);
        }
        drawBorder();
        drawPlayerHealth(World.player.getHealth());
        drawScore();
        if (Settings.DEV_MODE) {
            drawFPS();
        }

        if (world.cinematicHappening) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.BLACK);
            sr.rect(0, 0, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.rect(0, Settings.RESOLUTION.y*0.8f, Settings.RESOLUTION.x, Settings.RESOLUTION.y*0.2f);
            sr.end();
        }

        switch(World.activity) {
            case 0:
                break;
            case 1:
                drawPauseScreen();
                break;
            case 2:
                drawStatsScreen();
                break;
            case 3:
                drawProgressScreen();
                break;
            case 4:
                drawSettingsScreen();
                break;
            case 5:
                break;
            default:
                Gdx.app.log("Renderer", "Found invalid activity identifier (" + World.activity + ")");
                break;
        }
    }
}
