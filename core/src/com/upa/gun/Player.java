package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.upa.gun.enemy.Powerup;

import java.util.List;

public class Player extends Entity {
    private static final float HITBOX_SIZE = 15f;

    static final float IFRAME_AFTER_HIT_LENGTH = 0.2f;

    Hitbox hitbox;

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

    public Array<Powerup> powerupsActive;

    Sound shot;

    Direction direction;

    PlayerState state;

    Player(float x, float y, GunGame game) {
        super(x, y, Assets.getTextureSize(Assets.playerAnimations).x, Assets.getTextureSize(Assets.playerAnimations).y,
                0, 0);
        spawnPoint = new Vector2(x, y);

        state = PlayerState.idle;

        bulletCooldown = Settings.playerBulletCooldown;

        health = Settings.playerHealth;

        direction = Direction.DOWN;

        timeSinceRoll = Settings.ROLL_DELAY;

        powerupsActive = new Array<Powerup>();

        this.game = game;
        state.setGame(game);
        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));

        inputHandler = new InputHandler();

        hitbox = new RectangularHitbox(x, y, HITBOX_SIZE, HITBOX_SIZE);
        centerHitbox();
    }

    public void reset() {
        setPosition(spawnPoint);

        state = PlayerState.idle;

        health = Settings.playerHealth;

        direction = Direction.DOWN;

        timeSinceRoll = Settings.ROLL_DELAY;

        centerHitbox();
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
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
        if ((state.isVulnerable() && !Settings.INVINCIBLE) && !game.world.cinematicHappening) {
            health -= damage;
            state.vulnerable = false;
            if (health <= 0) {
                state = PlayerState.dying;
            }
        }
    }

    /**
     * @return Whether the powerup with the given ID is currently active.
     */
    public boolean hasPowerup(int id) {
        for (Powerup powerup : new Array.ArrayIterator<Powerup>(powerupsActive)) {
            if (powerup.getId() == id) {
                return true;
            }
        }

        return false;
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
