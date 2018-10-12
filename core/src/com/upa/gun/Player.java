package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    private static final float HITBOX_SIZE = 15f;

    static final float IFRAME_AFTER_HIT_LENGTH = 0.2f;

    Vector2 spawnPoint;

    double bulletCooldown;

    public boolean topStop = false;
    public boolean botStop = false;
    public boolean leftStop = false;
    public boolean rightStop = false;

    private int health;

    private GunGame game;
    private InputHandler inputHandler;

    float timeSinceRoll;

    Sound shot;

    Direction direction;

    PlayerState state;

    Player(float x, float y, GunGame game) {
        super(x, y, Assets.getTextureSize(Assets.playerAnimations).x, Assets.getTextureSize(Assets.playerAnimations).y,
                0, 0);
        spawnPoint = new Vector2(x, y);

        state = PlayerState.idle;

        bulletCooldown = 0.2f;

        health = Settings.PLAYER_HEALTH;

        direction = Direction.DOWN;

        timeSinceRoll = Settings.ROLL_DELAY;

        this.game = game;
        state.setGame(game);
        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));

        inputHandler = new InputHandler();
    }

    /**
     * Create player's hitbox (called by Entity constructor.)
     */
    @Override
    void createHitbox() {
        Vector2 position = getPosition();
        hitbox = new RectangularHitbox(position.x, position.y, HITBOX_SIZE, HITBOX_SIZE);
        centerHitbox();
        System.out.println("Size: " + getSize());
        System.out.println("Hitbox size: " + hitbox.getWidth() + ", " + hitbox.getHeight());
        System.out.println("Offset: " + getHitboxOffset());
    }

    /**
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
        if (state.isVulnerable() && !game.world.cinematicHappening) {
            health -= damage;
            state.vulnerable = false;
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

    @Override
    public void update(float delta) {
        super.update(delta);
        state.update(delta);

        timeSinceRoll += delta;

        bulletCooldown -= delta;

        if (state.controllable) {
            inputHandler.update(delta);
        }
    }
}
