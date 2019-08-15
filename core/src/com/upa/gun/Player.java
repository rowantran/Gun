package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upa.gun.enemy.Powerup;

/**
 * Class for the player
 */
public class Player extends Entity {

    static final float IFRAME_AFTER_HIT_LENGTH = 0.2f;

    public Hitboxes cCheckHitbox;
    public Vector2 spawnPoint;
    float bulletCooldown;
    public boolean topStop = false;
    public boolean botStop = false;
    public boolean leftStop = false;
    public boolean rightStop = false;
    private int health;
    private GunGame game;
    public InputHandler inputHandler;
    float timeSinceRoll;
    public Array<Powerup> powerupsActive;
    Sound shot;
    Direction direction;
    PlayerState state;

    Player(Vector2 position, GunGame game) {
        super(position, Assets.getTextureSize(Assets.playerAnimations));
        spawnPoint = position.cpy();

        bulletCooldown = Settings.playerBulletCooldown;
        health = Settings.playerHealth;

        state = PlayerState.idle;
        direction = Direction.DOWN;
        timeSinceRoll = Settings.ROLL_DELAY;

        powerupsActive = new Array<Powerup>();

        this.game = game;
        state.setGame(game);
        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));

        inputHandler = new InputHandler();

        cCheckHitbox = new Hitboxes(position);

        RectangularHitbox cCheck = new RectangularHitbox(position, new Vector2(size.x, 20f));
        cCheckHitbox.addHitbox("cCheck", cCheck);

        RectangularHitbox center = new RectangularHitbox(position, new Vector2(2f, 2f));
        center.setPosition(new Vector2(getPosition().x + getSize().x/2 - center.getWidth()/2, getPosition().y + getSize().y/2 - center.getHeight()/2));
        hitbox.addHitbox("center", center);

        hitbox.generateCorrectOffsets();
        cCheckHitbox.setActive(true);
        hitbox.setActive(true);

    }

    /**
     * Resets all values
     */
    public void reset() {
        setPosition(spawnPoint);

        state = PlayerState.idle;
        direction = Direction.DOWN;
        timeSinceRoll = Settings.ROLL_DELAY;

        health = Settings.playerHealth;

        //centerHitbox();
    }

    /**
     * Damages player if player is currently vulnerable, kills them if health drops below zero (this should probably be
     * handled in update() instead)
      * @param damage - Damage to player in hitpoints
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
     * Determines if a powerup is active
     * @return - Whether the powerup with the given ID is currently active
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
     * Update function; checks for inputs and terrain collisions
     * @param delta - Clock
     */
    @Override
    public void update(float delta) {
        if (state.controllable) {
            inputHandler.update(delta);
        }
        World.collisionChecker.checkFutureCollisions(delta, this, cCheckHitbox);
        super.update(delta);
        state.update(delta);
        cCheckHitbox.setPosition(position);
        timeSinceRoll += delta;
        bulletCooldown -= delta;
    }

    /**
     * Basic update function that alo moves the crate check hitbox
     * @param delta - Clock
     */
    @Override
    public void basicUpdate(float delta) {
        super.basicUpdate(delta);
        cCheckHitbox.setPosition(position);
    }

    @Override
    public Hitboxes getHitbox() {
        return hitbox;
    }
    int getHealth() {
        return health;
    }
    SpriteState getState() {
        return state.getTextureState();
    }
}
