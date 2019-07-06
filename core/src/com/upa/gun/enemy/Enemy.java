package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;
import com.badlogic.gdx.Gdx;

import java.util.Map;

public class Enemy extends Entity {
    private static final float DAMAGE_FLASH_LENGTH = 1/20f;

    public float timeElapsed;

    private int startHealth;
    private int health;

    private float horizontalDifference;
    private float verticalDifference;

    float timeSinceAttack;

    private EnemyState state;

    public boolean damagedFrame;
    private float damagedFrameTime;

    AttackRotation rotation;

    public float opacity;

    public Map<String, String> sprites;
    public String sprite;

    private Hitboxes hitbox;

    private int id;

    Enemy(EnemyInfo info, Vector2 position) {
        super(position, new Vector2(info.width, info.height));

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
    public Hitboxes getHitbox() {
        return hitbox;
    }

    private void createHitbox(String hitboxType, int width, int height) throws UnrecognizedHitboxTypeException {
        hitbox = new Hitboxes();
        if (hitboxType.equals("rectangular")) {
            RectangularHitbox center = new RectangularHitbox(getPosition(), new Vector2(width, height));
            centerRectangularHitbox(center);
            hitbox.addHitbox("center", center);

        } else {
            Gdx.app.log("Enemy creation:","Failed to load hitbox type.");
            throw new UnrecognizedHitboxTypeException(hitboxType);
        }

        hitbox.setActive(true);


    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            state = new EnemyFadingState(this);

            int rand = (int)(Math.random() * 1); //random powerup checker
            if(rand == 0) {
                int type = (int)(Math.random() * PowerupFactory.getInstance().powerups.size());
                World.powerups.add(World.getInstance().getPowerupFactory().createPowerup(type, this.getPosition()));
                Powerup added = World.powerups.get(World.powerups.size()-1);
                //System.out.println(added.info.effectDescription);
            }


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

        if(id == 2) {
            centerRectangularHitbox((RectangularHitbox)getHitbox().getChild("center"));
        }

        if (damagedFrame) {
            damagedFrameTime += delta;
            if (damagedFrameTime >= DAMAGE_FLASH_LENGTH) {
                damagedFrame = false;
                damagedFrameTime = 0f;
            }
        }

        if (state.mobileType() == 1) { //kinda wonky)
            updateDirection();
            move();
        } else if(state.mobileType() == 2) {
            move();
            setVelocity(getVelocity().x/2, getVelocity().y/2);
        } else {
            setVelocity(0, 0);
        }

        state.update(delta);
    }

    private void updateDirection() {
        Vector2 playerPos = World.player.getPosition();
        float playerX = playerPos.x;
        float playerY = playerPos.y;
        float slimeX = getPosition().x;
        float slimeY = getPosition().y;
        horizontalDifference = playerX - slimeX;
        verticalDifference = playerY - slimeY;
    }

    private void move() {
        boolean diag = true;
        float pythag = 0.7071f;

        float xSquare = horizontalDifference * horizontalDifference;
        float ySquare = verticalDifference * verticalDifference;
        double currentSquare = xSquare + ySquare;
        float currentSpeed = (float)Math.sqrt(currentSquare);
        float speedRatio = Settings.SLIME_SPEED / currentSpeed;
        setVelocity(horizontalDifference * speedRatio, verticalDifference * speedRatio);

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
