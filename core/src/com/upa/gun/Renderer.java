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
        batch.draw(Assets.floor, Settings.RESOLUTION.x/2 - Assets.floor.getWidth()/2,
                Settings.RESOLUTION.y/2 - Assets.floor.getHeight()/2, (float)Assets.floor.getWidth(),
                (float)Assets.floor.getHeight());
        batch.end();
    }

    private void drawBorder() {
        batch.begin();
        batch.enableBlending();
        batch.setColor(1.0f,1.0f,1.0f,1.0f);
        batch.draw(Assets.border, 0f, 0f, (float)Assets.border.getWidth(), (float)Assets.border.getHeight());
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

    private void drawPowerup(Powerup p) {
        batch.begin();
        batch.enableBlending();
        batch.draw(Assets.powerup1, p.getPosition().x, p.getPosition().y, p.getSize().x, p.getSize().y);
        batch.end();
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
    }



    private void drawBullet(Bullet bullet) {
        batch.begin();
        batch.enableBlending();
        Vector2 pos = bullet.getPosition();
        batch.draw(Assets.bulletEnemy, pos.x, pos.y, bullet.getSize().x, bullet.getSize().y);
        batch.end();
    }

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
                if (h.isActive()) {
                    sr.rect(h.getX(), h.getY(), h.getWidth(), h.getHeight());
                }
            }
            sr.end();

        }
    }

    private void drawSpecialHitbox(Hitboxes hitbox) {
        if(hitbox.isActive()) {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            for(Hitbox h : hitbox) {
                if (h.isActive()) {
                    sr.rect(h.getX(), h.getY(), h.getWidth(), h.getHeight());
                }
            }
            sr.end();
        }
    }

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
                    drawSlimeHealth(((Enemy)e).getHealth(), ((Enemy)e).getStartHealth(), e.getPosition().x, e.getPosition().y);
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


        /*
        for (Powerup p : World.powerups) {
            drawPowerup(p);
        }
        */

        if(World.roomChange != 0) {
            drawLayered(World.oldEntities);
        }

        batch.begin();
        for (SpawnIndicator s : World.indicators) {
            drawIndicator(s);
        }
        batch.end(); //???
        drawBorder();
        batch.begin();
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
}
