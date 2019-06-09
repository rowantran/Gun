package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upa.gun.enemy.Powerup;

public class Player extends Entity {
    private static final float HITBOX_SIZE = 15f;

    static final float IFRAME_AFTER_HIT_LENGTH = 0.2f;

    Hitboxes hitbox;
    //Hitbox footHixbox;

    Vector2 spawnPoint;

    float bulletCooldown;

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

    Player(Vector2 position, GunGame game) {
        super(position, Assets.getTextureSize(Assets.playerAnimations));
        spawnPoint = position.cpy();

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

        hitbox = new Hitboxes();
        //centerHitbox();
        //footHixbox = new RectangularHitbox(x, y, Assets.getTextureSize(Assets.playerAnimations).x, 2);

        /*
        RectangularHitbox center = new RectangularHitbox(position, new Vector2(HITBOX_SIZE, HITBOX_SIZE));
        centerRectangularHitbox(center);
        hitboxes.addHitbox("center", center);

        RectangularHitbox foot = new RectangularHitbox(position, new Vector2(Assets.getTextureSize(Assets.playerAnimations).x, 2));
        hitboxes.addHitbox("foot", foot);
        */
    }

    public void reset() {
        setPosition(spawnPoint);

        state = PlayerState.idle;

        health = Settings.playerHealth;

        direction = Direction.DOWN;

        timeSinceRoll = Settings.ROLL_DELAY;

        //centerHitbox();
    }

    @Override
    public Hitboxes getHitbox() {
        return hitbox;

    }

    /**
     * @return Player's current health
     */
    int getHealth() {
        return health;
    }

    public void resetStops() {
        leftStop = false;
        rightStop = false;
        topStop = false;
        botStop = false;
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

        //footHixbox.setX(getPosition().x); //move somewhere else
        //footHixbox.setY(getPosition().y);

        timeSinceRoll += delta;

        bulletCooldown -= delta;

        if (state.controllable) {
            inputHandler.update(delta);
        }
    }
}
