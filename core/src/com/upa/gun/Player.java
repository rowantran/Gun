package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    boolean rolling;
    boolean hurt;

    Vector2 spawnPoint;

    double bulletCooldown;

    public boolean topStop = false;
    public boolean botStop = false;
    public boolean leftStop = false;
    public boolean rightStop = false;

    private int health;

    boolean iframe;
    float iframeTimer;

    private GunGame game;
    private InputHandler inputHandler;

    Sound shot;

    Direction direction;

    PlayerState state;

    Player(float x, float y, GunGame game) {
        super(x, y, 10, 10, 0, 0);
        spawnPoint = new Vector2(x, y);

        state = PlayerState.idle;

        bulletCooldown = 0.2;
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

    /**
     * Create player's hitbox (called by Entity constructor.)
     * @param width Width of the hitbox in pixels.
     * @param height Height of the hitbox in pixels.
     */
    @Override
    void createHitbox(float width, float height) {
        Vector2 position = getPosition();
        hitbox = new RectangularHitbox(position.x, position.y, width, height);
    }

    /**
     * Return player's current health
     * @return Player's current health
     */
    int getHealth() {
        return health;
    }

    /**
     * Damages player if player is currently vulnerable, kills them if health drops below zero (this should probably be
     * handled in update() instead.)
      * @param damage Amount of damage in hit points to deal to player.
     */
    void hurt(int damage) {
        if (!iframe && !game.world.cinematicHappening) {
            health -= damage;
            iframe = true;
            //opacity = 0.5f;
            if (health <= 0) {
                state = PlayerState.dying;
            }
        }
    }

    /**
     * Returns the correct SpriteState mapping the player's current state to the corresponding texture.
     * @return The SpriteState corresponding to the current state.
     */
    SpriteState getState() {
        return state.getTextureState();
    }

    /**
     * Start a roll in the current direction of movement, if not already rolling.
     */
    void roll() {
        if (!rolling) {
            rolling = true;
            Vector2 rollAngle = Direction.getAngle(direction).setLength(Settings.ROLL_SPEED);
            setVelocity(rollAngle);
        }
    }

    /**
     * Stop the player's movement.
     */
    private void stop() {
        setVelocity(0, 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        state.update(delta);

        bulletCooldown -= delta;

        if (state.controllable) {
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
    }
}
