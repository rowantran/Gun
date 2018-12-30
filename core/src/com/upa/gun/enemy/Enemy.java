package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;

import java.util.Map;

public class Enemy extends Entity {
    private static final float DAMAGE_FLASH_LENGTH = 1/20f;

    public float timeElapsed;

    private int startHealth;
    private int health;

    float timeSinceAttack;

    private EnemyState state;

    public boolean damagedFrame;
    private float damagedFrameTime;

    AttackRotation rotation;

    public float opacity;

    public Map<String, String> sprites;
    public String sprite;

    private Hitbox hitbox;

    private int id;

    Enemy(EnemyInfo info, float x, float y) {
        super(x, y, info.width, info.height, 0, 0);

        try {
            createHitbox(info.hitboxType, info.hitboxWidth, info.hitboxHeight);
        } catch (UnrecognizedHitboxTypeException e) {
            e.printStackTrace();
        }

        timeElapsed = 20.0f;

        health = info.health;
        startHealth = health;

        state = new EnemyActiveState();

        damagedFrame = false;
        damagedFrameTime = 0f;

        sprites = info.sprites;
        sprite = "default";
        opacity = 1f;

        rotation = info.rotation.copy();
        rotation.setEnemy(this);

        id = info.id;
    }

    public int getID() { return id; }
    public int getStartHealth() { return startHealth; }
    public int getHealth() { return health; }


    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    private void createHitbox(String hitboxType, int width, int height) throws UnrecognizedHitboxTypeException {
        if (hitboxType.equals("rectangular")) {
            hitbox = new RectangularHitbox(getPosition().x, getPosition().y, width, height);
        } else {
            throw new UnrecognizedHitboxTypeException(hitboxType);
        }

        centerHitbox();
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            state = new EnemyFadingState(this);



            World.spawner.slimesKilled++;
            World.spawner.slimesKilledSinceLastBoss++;
        } else {
            damagedFrame = true;
        }
    }

    /**
     * Change this enemy's currently showing sprite.
     * @param spriteKey Key of the sprite to change to
     */
    private void changeSprite(String spriteKey) {
        if (sprites.containsKey((spriteKey))) {
            sprite = spriteKey;
        }
    }

    public void update(float delta) {
        super.update(delta);
        timeElapsed += delta;
        rotation.cycle(delta, getPosition());

        changeSprite(rotation.currentAttack().getSprite());

        if (damagedFrame) {
            damagedFrameTime += delta;
            if (damagedFrameTime >= DAMAGE_FLASH_LENGTH) {
                damagedFrame = false;
                damagedFrameTime = 0f;
            }
        }

        if (rotation.currentAttack().isMobile()) {
            move();
        } else {
            setVelocity(0, 0);
        }

        state.update(delta);
    }

    private void move() {
        Vector2 playerPos = World.player.getPosition();
        float playerX = playerPos.x;
        float playerY = playerPos.y;

        float slimeX = getPosition().x;
        float slimeY = getPosition().y;

        float pythagMultiplier = 0.7071f;

        if(slimeX < playerX) {
            setVelocity(Settings.SLIME_SPEED, 0);
        } else if(slimeX > playerX) {
            setVelocity(-Settings.SLIME_SPEED, 0);
        } else {
            setVelocity(0, 0);
        }
        if(slimeY < playerY) {
            setVelocity(getVelocity().x * pythagMultiplier,
                    Settings.SLIME_SPEED * pythagMultiplier);
        } else if(slimeY > playerY) {
            setVelocity(getVelocity().x * pythagMultiplier,
                    -Settings.SLIME_SPEED * pythagMultiplier);
        } else {
            setVelocity(getVelocity().x, 0);
        }
        if(getVelocity().x == 0 && getVelocity().y != 0) {
            setVelocity(0, getVelocity().y / pythagMultiplier);
        }


        if(slimeX <= 113f && getVelocity().x < 0) {
            setVelocity(0, getVelocity().y);
        }
        if(slimeX >= 1160f && getVelocity().x > 0) {
            setVelocity(0, getVelocity().y);
        }
        if(slimeY <= 136f && getVelocity().y < 0) {
            setVelocity(getVelocity().x, 0);
        }
        if(slimeY >= 674f && getVelocity().y > 0) {
            setVelocity(getVelocity().x, 0);
        }
    }

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
