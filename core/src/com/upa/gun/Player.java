package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.upa.gun.enemy.Powerup;
import org.w3c.dom.css.Rect;

public class Player extends Entity {
    private static final float HITBOX_SIZE = 15f;

    static final float IFRAME_AFTER_HIT_LENGTH = 0.2f;

    public Hitboxes hitbox;
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
        crateCheckHitbox = new Hitboxes();

        RectangularHitbox center = new RectangularHitbox(position, new Vector2(2f, 2f));
        centerRectangularHitbox(center);
        hitbox.addHitbox("center", center);

        RectangularHitbox leftFoot = new RectangularHitbox(position, new Vector2(12f, 20f));
        leftFoot.setPosition(new Vector2(position.x - 4, position.y));
        crateCheckHitbox.addHitbox("leftFoot", leftFoot);

        RectangularHitbox rightFoot = new RectangularHitbox(position, new Vector2(12f, 20f));
        rightFoot.setPosition(new Vector2(position.x + getSize().x - 8, position.y));
        crateCheckHitbox.addHitbox("rightFoot", rightFoot);

        RectangularHitbox topFoot = new RectangularHitbox(position, new Vector2(43f, 4f));
        topFoot.setPosition(new Vector2(position.x-4, position.y + 16));
        crateCheckHitbox.addHitbox("topFoot", topFoot);

        RectangularHitbox botFoot = new RectangularHitbox(position, new Vector2(43f, 4f));
        botFoot.setPosition(new Vector2(position.x-4, position.y));
        crateCheckHitbox.addHitbox("botFoot", botFoot);


        hitbox.setActive(true);
        crateCheckHitbox.setActive(true);

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

    /**
     * Handles if the player's velocity will leave them in a crate
     * @param delta
     * @return
     */
    private void handleFutureCollision(float delta) {
        Vector2 current = new Vector2(getPosition().x, getPosition().y);
        setPosition(getPosition().x + getVelocity().x * delta, getPosition().y + getVelocity().y * delta);

        Hitbox leftFoot = World.player.crateCheckHitbox.getChild("leftFoot");
        Hitbox rightFoot = World.player.crateCheckHitbox.getChild("rightFoot");
        Hitbox topFoot = World.player.crateCheckHitbox.getChild("topFoot");
        Hitbox botFoot = World.player.crateCheckHitbox.getChild("botFoot");

        for(Crate c : World.currentMap.getCrates()) {

            Hitbox rightEdge = c.getHitbox().getChild("rightEdge");
            Hitbox leftEdge = c.getHitbox().getChild("leftEdge");
            Hitbox topEdge = c.getHitbox().getChild("topEdge");
            Hitbox botEdge = c.getHitbox().getChild("botEdge");

            if(rightEdge.colliding(leftFoot) && getVelocity().x < 0) {
                setVelocity(((rightEdge.getX() + rightEdge.getWidth() - 1) - (leftFoot.getX())) / delta, getVelocity().y);
            }
            if(leftEdge.colliding(rightFoot) && getVelocity().x > 0) {
                setVelocity(((leftEdge.getX() + 1) - (rightFoot.getX() + rightFoot.getWidth())) / delta, getVelocity().y);
            }
            if(topEdge.colliding(botFoot) && getVelocity().y < 0) {
                setVelocity(getVelocity().x, ((topEdge.getY() + topEdge.getHeight() - 1) - (botFoot.getY())) / delta);
            }
            if(botEdge.colliding(topFoot) && getVelocity().y > 0) {
                setVelocity(getVelocity().x, ((botEdge.getY() + 1) - (topFoot.getY() + topFoot.getHeight())) / delta);
            }

        }
        setPosition(current);
    }

    @Override
    public void update(float delta) {
        handleFutureCollision(delta);
        super.update(delta);
        state.update(delta);
        crateCheckHitbox.updateHitboxes(getVelocity().x * delta, getVelocity().y * delta);
        timeSinceRoll += delta;

        bulletCooldown -= delta;

        if (state.controllable) {
            inputHandler.update(delta);
        }
    }

    public void specialMove(float delta) {
        setPosition(getPosition().x + getVelocity().x * delta, getPosition().y + getVelocity().y * delta);
        hitbox.updateHitboxes(getVelocity().x * delta, getVelocity().y * delta);
        crateCheckHitbox.updateHitboxes(getVelocity().x * delta, getVelocity().y * delta);
    }
}
