package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class Player {
    public float timeElapsed;
    boolean moving;
    boolean dying;
    boolean fading;
    boolean hurt;

    Vector2 spawnPoint;
    Polygon hitbox;
    float opacity;

    private World world;
    private GunWorld gunWorld;


    double bulletCooldown;

    public static int FRONT = 0;
    public static int BACK = 1;
    public static int LEFT = 2;
    public static int RIGHT = 3;
    public int health;

    int rotation;

    Body body;
    

    boolean iframe;
    float iframeTimer;
    float iframeLength;

    Sound shot;

    public Player(float x, float y, World world) {
        spawnPoint = new Vector2(x, y);

        bulletCooldown = 0.4;
        timeElapsed = 0.0f;
        moving = false;
        dying = false;
        hurt = false;

        opacity = 1.0f;
        health = 10;

        rotation = FRONT;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape hitbox = new CircleShape();
        hitbox.setRadius(10f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitbox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        this.world = world;
        this.gunWorld = new GunWorld(this, world);

        iframe = false;
        iframeTimer = 0f;
        iframeLength = 1.0f;

        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));
    }

    public void setWorld(GunWorld world) {
        this.gunWorld = world;
    }

    public void hurt(int damage) {
        if (!iframe) {
            health -= damage;
            iframe = true;
            System.out.println(Integer.toString(health) + ", " + Integer.toString(damage));
            if (health <= 0) {
                dying = true;
            }
        }
    }

    public void update(float delta) {
        moving = false;
        bulletCooldown -= delta;

        if (iframe) {
            iframeTimer += delta;
            if (iframeTimer > iframeLength) {
                System.out.println("iframe over");
                iframe = false;
                iframeTimer = 0f;
            }
        }

        if (dying) {
            Assets.playerIdleSprites[rotation].setX(body.getTransform().getPosition().x-
                    Assets.playerIdleSprites[rotation].getWidth()/2);
            Assets.playerIdleSprites[rotation].setY(body.getTransform().getPosition().y-
                    Assets.playerIdleSprites[rotation].getHeight()/2);
            Assets.playerIdleSprites[rotation].setOriginCenter();
            Assets.playerIdleSprites[rotation].rotate(Settings.DEATH_ROTATE_SPEED * delta);
            if (Assets.playerIdleSprites[rotation].getRotation() > 90) {
                dying = false;
                fading = true;
            }
        } if (fading) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
            if (opacity < 0.0f) {
                opacity = 1.0f;
                Assets.playerIdleSprites[rotation].setRotation(0);
                fading = false;
                this.body.setTransform(spawnPoint, 0);
                Gdx.app.exit();
            }
        }
        if (!dying && !fading) {
            int angle = (int) body.getTransform().getRotation();

            if (Gdx.input.isKeyPressed(Settings.KEY_LEFT)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                if(!(currentX - Settings.PLAYER_SPEED * delta < 113)) {
                    body.setTransform(currentX - Settings.PLAYER_SPEED * delta, currentY, angle);
                    moving = true;
                }
                rotation = LEFT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                if(!(currentX + Settings.PLAYER_SPEED * delta > 1164)) {
                    body.setTransform(currentX + Settings.PLAYER_SPEED * delta, currentY, angle);
                    moving = true;
                }
                rotation = RIGHT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_DOWN)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                if(!(currentY - Settings.PLAYER_SPEED * delta < 132)){
                    body.setTransform(currentX, currentY - Settings.PLAYER_SPEED * delta, angle);
                    moving = true;
                }
                rotation = FRONT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_UP)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                if(!(currentY + Settings.PLAYER_SPEED * delta > 702)){
                    body.setTransform(currentX, currentY + Settings.PLAYER_SPEED * delta, angle);
                    moving = true;
                }
                rotation = BACK;
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
                body.setLinearVelocity(0, 0);
                moving = false;
            }

            if (Gdx.input.justTouched()) {
                OrthographicCamera camera = new OrthographicCamera();
                camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

                if(bulletCooldown <= 0) {

                    Vector3 mousePos3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),
                            0));
                    Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
                    Vector2 bulletAngle = mousePos.sub(body.getTransform().getPosition());
                    gunWorld.bullets.add(new FriendlyBullet(body.getTransform().getPosition().x,
                            body.getTransform().getPosition().y,
                            bulletAngle.angleRad(), world));
                    shot.stop();
                    shot.play();
                    bulletCooldown = 0.4;
                }
            }
        }

        if (moving) {
            timeElapsed += delta;
        }
    }
}
