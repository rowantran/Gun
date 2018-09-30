package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    boolean rolling;
    boolean hurt;
    float timeRolling;

    Vector2 spawnPoint;

    double bulletCooldown;

    public boolean topStop = false;
    public boolean botStop = false;
    public boolean leftStop = false;
    public boolean rightStop = false;

    public int health;

    boolean iframe;
    float iframeTimer;

    private GunGame game;
    private InputHandler inputHandler;

    Sound shot;
    Direction direction;
    PlayerState state;


    Player(float x, float y, GunGame game) {
        super(x, y, 10, 10);
        spawnPoint = new Vector2(x, y);


        state = PlayerState.idle;


        bulletCooldown = 0.4;
        timeRolling = 0f;
        hurt = false;

        health = Settings.PLAYER_HEALTH;

        direction = Direction.DOWN;

        iframe = false;
        iframeTimer = 0f;

        this.game = game;
        state.setGame(game);
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
            //opacity = 0.5f;
            if (health <= 0) {
                state = state.dying;
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

        if(state.controllable) {
            inputHandler.update(delta);
        }


        if (iframe) {
            iframeTimer += delta;
            if (iframeTimer > Settings.I_FRAME_LENGTH) {
                //System.out.println("iframe over");
                iframe = false;
                //opacity = 1f;
                iframeTimer = 0f;
            }
        }



        if (rolling) {
            timeRolling += delta;
            if (timeRolling > Settings.ROLL_LENGTH) {
                rolling = false;
                timeRolling = 0f;
                stop();
            }
        }
    }
}
