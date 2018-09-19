package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class Player {
    public float timeElapsed;
    boolean moving;
    boolean dying;
    boolean fading;
    boolean rolling;
    boolean hurt;
    float timeRolling;

    Vector2 spawnPoint;
    float opacity;
    float rotation;

    double bulletCooldown;

    public boolean topStop = false;
    public boolean botStop = false;
    public boolean leftStop = false;
    public boolean rightStop = false;

    public int health;

    Direction direction;

    Body body;

    boolean iframe;
    float iframeTimer;
    float iframeLength;

    private GunGame game;

    Sound shot;

    Player(float x, float y, GunGame game, World world) {
        spawnPoint = new Vector2(x, y);

        bulletCooldown = 0.4;
        timeElapsed = 0.0f;
        timeRolling = 0f;
        moving = false;
        dying = false;
        hurt = false;

        opacity = 1.0f;
        health = Settings.PLAYER_HEALTH;
        rotation = 0f;

        direction = Direction.DOWN;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape hitbox = new CircleShape();
        hitbox.setRadius(10f/Settings.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitbox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        iframe = false;
        iframeTimer = 0f;
        iframeLength = 1.0f;

        this.game = game;
        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));
    }

    void hurt(int damage) {
        if (!iframe && !game.world.cinematicHappening) {
            health -= damage;
            iframe = true;
            opacity = 0.5f;
            if (health <= 0) {
                dying = true;
            }
        }
    }

    SpriteState getState() {
        if (moving) {
            return SpriteState.MOVING;
        } else {
            return SpriteState.IDLE;
        }
    }

    void roll(Vector2 mousePos) {
        if (!rolling) {
            rolling = true;
            Vector2 rollAngle = mousePos.sub(body.getTransform().getPosition());
            rollAngle = rollAngle.scl(Settings.ROLL_SPEED/rollAngle.len());
            body.setLinearVelocity(rollAngle);
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
                opacity = 1f;
                iframeTimer = 0f;
            }
        }

        if (dying) {
            rotation += Settings.DEATH_ROTATE_SPEED * delta;
            if (rotation > 90) {
                dying = false;
                fading = true;
            }
        } if (fading) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
            if (opacity < 0.0f) {
                opacity = 1.0f;
                rotation = 0;
                fading = false;
                this.body.setTransform(spawnPoint, 0);
                game.setScreen(new GameOver(game));
            }
        } if (rolling) {
            timeRolling += delta;
            if (timeRolling > Settings.ROLL_LENGTH) {
                rolling = false;
                timeRolling = 0f;
                body.setLinearVelocity(0, 0);
            }
        }

        if (!dying && !fading && !rolling) {
            int angle = (int) body.getTransform().getRotation();

            if (Gdx.input.isKeyPressed(Settings.KEY_LEFT)) {
                float currentX = body.getTransform().getPosition().x;
                float currentY = body.getTransform().getPosition().y;
                if(!((currentX - Settings.PLAYER_SPEED * delta)*Settings.PPM < 113)) {
                    body.setTransform(currentX - Settings.PLAYER_SPEED * delta, currentY, angle);
                    moving = true;
                }
                direction = Direction.LEFT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT)) {
                float currentX = body.getTransform().getPosition().x;
                float currentY = body.getTransform().getPosition().y;
                if(!((currentX + Settings.PLAYER_SPEED * delta)*Settings.PPM > 1164)) {
                    body.setTransform(currentX + Settings.PLAYER_SPEED * delta, currentY, angle);
                    moving = true;
                }
                direction = Direction.RIGHT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_DOWN)) {
                float currentX = body.getTransform().getPosition().x;
                float currentY = body.getTransform().getPosition().y;
                if(!botStop){
                    body.setTransform(currentX, currentY - Settings.PLAYER_SPEED * delta, angle);
                    moving = true;
                }
                direction = Direction.DOWN;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_UP)) {
                float currentX = body.getTransform().getPosition().x;
                float currentY = body.getTransform().getPosition().y;
                if(!((currentY + Settings.PLAYER_SPEED * delta)*Settings.PPM > 702)){
                    body.setTransform(currentX, currentY + Settings.PLAYER_SPEED * delta, angle);
                    moving = true;
                }
                direction = Direction.UP;
            }

            if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
                moving = false;
            }

            if (Gdx.input.justTouched()) {
                OrthographicCamera camera = new OrthographicCamera();
                camera.setToOrtho(false, Settings.RESOLUTION.x/Settings.PPM, Settings.RESOLUTION.y/Settings.PPM);

                if(bulletCooldown <= 0) {

                    Vector3 mousePos3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),
                            0));
                    Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
                    Vector2 bulletAngle = mousePos.sub(body.getTransform().getPosition());
                    game.world.bullets.add(new FriendlyBullet(body.getTransform().getPosition().x,
                            body.getTransform().getPosition().y,
                            bulletAngle.angleRad(), game.world.world));
                    shot.stop();
                    shot.play(.5f);
                    bulletCooldown = 0.4;
                }
            }
        }

        timeElapsed += delta;
    }
}
