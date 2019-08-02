package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;
import com.badlogic.gdx.Gdx;
import org.w3c.dom.css.Rect;

import java.util.Map;
import static com.upa.gun.Settings.*;

public abstract class Enemy extends Entity {
    private static final float DAMAGE_FLASH_LENGTH = 1/20f;

    public float timeElapsed;

    private int startHealth;
    private int health;

    float timeSinceAttack;

    protected EnemyState state;

    public boolean damagedFrame;
    private float damagedFrameTime;

    AttackRotation rotation;

    public float opacity;

    public boolean topStop = false;
    public boolean botStop = false;
    public boolean leftStop = false;
    public boolean rightStop = false;

    public Map<String, String> sprites;
    public String sprite;

    protected Hitboxes hitbox;
    public Hitboxes crateCheckHitbox;

    private int id;

    Enemy(EnemyInfo info, Vector2 position) {
        super(position, new Vector2(info.width, info.height));

        hitbox = new Hitboxes();
        crateCheckHitbox = new Hitboxes();

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
    public Hitboxes getHitbox() { return hitbox; }



    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
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
     * Change this enemy's currently showing sprite.
     * @param spriteKey Key of the sprite to change to
     */
    private void changeSprite(String spriteKey) {
        if (sprites.containsKey((spriteKey))) {
            sprite = spriteKey;
        }
    }

    public void update(float delta) {

        timeElapsed += delta;
        rotation.cycle(delta, getPosition());

        changeSprite(rotation.currentAttack().getSprite());

        if (id == 2) { //bandaid boss hitbox fix
            Hitbox center = getHitbox().getChild("center");
            center.setPosition(new Vector2(getPosition().x + getSize().x/2 - center.getWidth()/2, getPosition().y + getSize().y/2 - center.getHeight()/2));
        }

        if (damagedFrame) {
            damagedFrameTime += delta;
            if (damagedFrameTime >= DAMAGE_FLASH_LENGTH) {
                damagedFrame = false;
                damagedFrameTime = 0f;
            }
        }

        handleFutureCollision(delta);
        handleStops();
        super.update(delta);
        crateCheckHitbox.updateHitboxes(getVelocity().x * delta, getVelocity().y * delta); //should not be in this class
        state.update(delta);
    }

    protected abstract void move();

    private void handleStops() {
        if(botStop && getVelocity().y < 0) {
            setVelocity(getVelocity().x, 0);
        }
        if(topStop && getVelocity().y > 0) {
            setVelocity(getVelocity().x, 0);
        }
        if(leftStop && getVelocity().x < 0) {
            setVelocity(0, getVelocity().y);
        }
        if(rightStop && getVelocity().x > 0) {
            setVelocity(0, getVelocity().y);
        }
    }

    public void handleFutureCollision(float delta) {
        Vector2 current = new Vector2(getPosition().x, getPosition().y);
        setPosition(getPosition().x + getVelocity().x * delta, getPosition().y + getVelocity().y * delta);

        Hitbox left = crateCheckHitbox.getChild("left");
        Hitbox right = crateCheckHitbox.getChild("right");
        Hitbox top = crateCheckHitbox.getChild("top");
        Hitbox bot = crateCheckHitbox.getChild("bot");

        for(Crate c : World.currentMap.getCrates()) {

            Hitbox rightEdge = c.getHitbox().getChild("rightEdge");
            Hitbox leftEdge = c.getHitbox().getChild("leftEdge");
            Hitbox topEdge = c.getHitbox().getChild("topEdge");
            Hitbox botEdge = c.getHitbox().getChild("botEdge");

            if(left.colliding(rightEdge) && getVelocity().x < 0) {
                setVelocity(((rightEdge.getX() + rightEdge.getWidth()-1) - (left.getX())) / delta, getVelocity().y);
            }
            if(right.colliding(leftEdge) && getVelocity().x > 0) {
                setVelocity(((leftEdge.getX()+1) - (right.getX() + right.getWidth())) / delta, getVelocity().y);
            }
            if(top.colliding(botEdge) && getVelocity().y > 0) {
                setVelocity(getVelocity().x, ((botEdge.getY()+1) - (top.getY() + top.getHeight())) / delta);
            }
            if(bot.colliding(topEdge) && getVelocity().y < 0) {
                setVelocity(getVelocity().x, ((topEdge.getY() + topEdge.getHeight()-1) - (bot.getY())) / delta);
            }
        }
        setPosition(current);
    }

    public EnemyState getState() { return state; }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
