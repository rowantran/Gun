package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    public float timeElapsed;
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

    boolean iframe;
    float iframeTimer;

    private GunGame game;

    private InputHandler inputHandler;

    Sound shot;



    PlayerState state;


    Player(float x, float y, GunGame game) {
        super(x, y, 10, 10);
        spawnPoint = new Vector2(x, y);


        state = PlayerState.idle;


        bulletCooldown = 0.4;
        timeElapsed = 0.0f;
        timeRolling = 0f;
        dying = false;
        hurt = false;

        opacity = 1.0f;
        health = Settings.PLAYER_HEALTH;
        rotation = 0f;

        direction = Direction.DOWN;

        iframe = false;
        iframeTimer = 0f;

        this.game = game;
        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));

        inputHandler = new InputHandler();
    }

    @Override
    void createHitbox(float width, float height) {
        Vector2 position = getPosition();
        hitbox = new RectangularHitbox(position.x, position.y, width, height);
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
        return state.getTextureState();
    }

    void roll() {
        if (!rolling) {
            rolling = true;
            Vector2 rollAngle = Direction.getAngle(direction);
            setLength(rollAngle, Settings.ROLL_SPEED);
            setVelocity(rollAngle);
        }
    }

    void move(Direction dir) {
        Vector2 moveAngle = Direction.getAngle(dir);
        setLength(moveAngle, Settings.PLAYER_SPEED);
        setVelocity(moveAngle);
    }

    private void stop() {
        setVelocity(0, 0);
    }

    private void setLength(Vector2 vec, float length) {
        vec.clamp(length, length);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        state.update(delta);

        bulletCooldown -= delta;

        if (iframe) {
            iframeTimer += delta;
            if (iframeTimer > Settings.I_FRAME_LENGTH) {
                //System.out.println("iframe over");
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
                setPosition(spawnPoint);
                game.setScreen(new GameOver(game));
            }
        } if (rolling) {
            timeRolling += delta;
            if (timeRolling > Settings.ROLL_LENGTH) {
                rolling = false;
                timeRolling = 0f;
                stop();
            }
        }

        if (!dying && !fading && !rolling) {
            inputHandler.update(delta);
        }

        timeElapsed += delta;
    }
}
