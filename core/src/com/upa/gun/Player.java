package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Class for the player
 */
public class Player extends Entity {

    public Hitboxes cCheckHitbox;
    public Vector2 spawnPoint;
    float bulletCooldown;
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

        RectangularHitbox center = new RectangularHitbox(position, new Vector2(30f, 30f));
        center.setPosition(new Vector2(getPosition().x + getSize().x/2 - center.getWidth()/2, getPosition().y + getSize().y/2 - center.getHeight()/2));
        hitbox.addHitbox("center", center);

        hitbox.generateCorrectOffsets();
        cCheckHitbox.setActive(true);
        hitbox.setActive(true);

        state.setVulnerable(true);
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
    }

    /**
     * Damages player if player is currently vulnerable, kills them if health drops below zero (this should probably be
     * handled in update() instead)
      * @param damage - Damage to player in hitpoints
     */
    void hurt(int damage) {
        if (state.isVulnerable() && !Settings.INVINCIBLE && !game.world.cinematicHappening) {
            System.out.println(state.isVulnerable());
            System.out.println("Damage");
            health -= damage;
            state.setVulnerable(false);
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

    public Vector2 getCenter() {
        return new Vector2(position.x + size.x/2, position.y + size.y/2);
    }

    @Override
    public Hitboxes getHitbox() {
        return hitbox;
    }
    public int getHealth() {
        return health;
    }
    public SpriteState getState() {
        return state.getTextureState();
    }
    public GunGame getGunGame() { return game; }
}
