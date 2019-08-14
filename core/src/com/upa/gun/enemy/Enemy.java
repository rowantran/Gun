package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;
import java.util.Map;

/**
 * Holds basic information and methods common for all enemy types
 */
public abstract class Enemy extends Entity {

    private int id;
    public float timeElapsed;
    private int startHealth;
    private int health;
    public boolean damagedFrame;
    private float damagedFrameTime;
    public float opacity;
    protected EnemyState state;
    private AttackRotation rotation;
    public Map<String, String> sprites;
    public String sprite;
    public Hitboxes cCheckHitbox;

    Enemy(EnemyInfo info, Vector2 position) {
        super(position, new Vector2(info.width, info.height));

        cCheckHitbox = new Hitboxes(position);


        state = new EnemyActiveState();

        timeElapsed = 20.0f;
        health = info.health;
        startHealth = health;
        damagedFrame = false;
        damagedFrameTime = 0f;

        sprites = info.sprites;
        sprite = "default";
        opacity = 1f;

        rotation = info.rotation.copy();
        rotation.setEnemy(this);

        id = info.id;
    }

    /**
     * Damages the entity
     * @param damage - The amount of damage dealt
     */
    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            state = new EnemyFadingState(this);
            hitbox.setActive(false);

            /*
            int rand = (int)(Math.random() * 1); //random powerup checker
            if(rand == 0) {
                int type = (int)(Math.random() * PowerupFactory.getInstance().powerups.size());
                World.powerups.add(World.getInstance().getPowerupFactory().createPowerup(type, this.getPosition()));
                Powerup added = World.powerups.get(World.powerups.size()-1);
                //System.out.println(added.info.effectDescription);
            }
            */

            //
            World.spawner.slimesKilled++;
            World.spawner.slimesKilledSinceLastBoss++;
            //
        }
        else {
            damagedFrame = true;
        }
    }

    /**
     * Change this enemy's currently showing sprite
     * @param spriteKey - Key of the sprite to change to
     */
    private void changeSprite(String spriteKey) {
        if (sprites.containsKey((spriteKey))) {
            sprite = spriteKey;
        }
    }

    /**
     * Update function; handles animation changes, state updates, crate collision, and attack timer
     * @param delta - Clock
     */
    public void update(float delta) {

        if (damagedFrame) {
            damagedFrameTime += delta;
            if (damagedFrameTime >= Settings.DAMAGE_FLASH_LENGTH) {
                damagedFrame = false;
                damagedFrameTime = 0f;
            }
        }
        World.collisionChecker.checkFutureCollisions(delta, this, cCheckHitbox);
        super.update(delta);
        state.update(delta);
        cCheckHitbox.setPosition(position);
        timeElapsed += delta;
        rotation.cycle(delta, getPosition());
        changeSprite(rotation.currentAttack().getSprite());

        Gdx.app.log("Enemy", "Position: (" + position.x + ", " + position.y + ")");


    }

    public int getID() { return id; }
    public int getStartHealth() { return startHealth; }
    public int getHealth() { return health; }
    public EnemyState getState() { return state; }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
