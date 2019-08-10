package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upa.gun.enemy.Powerup;

public class Player extends Entity {

    static final float IFRAME_AFTER_HIT_LENGTH = 0.2f;

    public Hitboxes crateCheckHitbox;
    public Vector2 spawnPoint;
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

        crateCheckHitbox = new Hitboxes(position);

        RectangularHitbox center = new RectangularHitbox(position, new Vector2(2f, 2f));
        center.setPosition(new Vector2(getPosition().x + getSize().x/2 - center.getWidth()/2, getPosition().y + getSize().y/2 - center.getHeight()/2));
        hitbox.addHitbox("center", center);

        RectangularHitbox leftFoot = new RectangularHitbox(position, new Vector2(size.x/2, 20f));
        crateCheckHitbox.addHitbox("leftFoot", leftFoot);

        RectangularHitbox rightFoot = new RectangularHitbox(position, new Vector2(size.x/2, 20f));
        rightFoot.setPosition(new Vector2(position.x + size.x/2, position.y));
        crateCheckHitbox.addHitbox("rightFoot", rightFoot);

        RectangularHitbox topFoot = new RectangularHitbox(position, new Vector2(size.x, 10f));
        topFoot.setPosition(new Vector2(position.x, position.y + 10));
        crateCheckHitbox.addHitbox("topFoot", topFoot);

        RectangularHitbox botFoot = new RectangularHitbox(position, new Vector2(size.x, 10f));
        crateCheckHitbox.addHitbox("botFoot", botFoot);

        hitbox.generateCorrectOffsets();
        crateCheckHitbox.generateCorrectOffsets();
        hitbox.setActive(true);
        crateCheckHitbox.setActive(true);

    }

    public void reset() {
        setPosition(spawnPoint);

        state = PlayerState.idle;
        direction = Direction.DOWN;
        timeSinceRoll = Settings.ROLL_DELAY;

        health = Settings.playerHealth;

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
        if (state.controllable) {
            inputHandler.update(delta);
        }
        World.collisionChecker.checkFutureCollisions(delta, this, crateCheckHitbox);
        super.update(delta);
        state.update(delta);
        crateCheckHitbox.setPosition(position);
        timeSinceRoll += delta;
        bulletCooldown -= delta;
    }

    public void specialMove(float delta) {
        setPosition(getPosition().x + getVelocity().x * delta, getPosition().y + getVelocity().y * delta);
        hitbox.updateHitboxes(getVelocity().x * delta, getVelocity().y * delta);
        crateCheckHitbox.updateHitboxes(getVelocity().x * delta, getVelocity().y * delta);
    }
}
