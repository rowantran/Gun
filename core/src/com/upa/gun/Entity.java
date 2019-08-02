package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

/**
 * Parent class for all entities
 * Stores an entity's position, size, velocity, and hitbox
 * Handles basic movement
 */
public abstract class Entity implements Updatable {

    protected Vector2 position;
    protected Vector2 size;
    protected Vector2 velocity;
    public Hitboxes hitbox;

    public Entity(Vector2 position, Vector2 size) {
        this.position = position.cpy();
        this.size = size.cpy();
        velocity = new Vector2(0f, 0f);
        hitbox = new Hitboxes(position);
    }

    /**
     * Update function; handles basic movement and matches hitbox location
     * @param delta - clock
     */
    @Override
    public void update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        hitbox.setPosition(position);

    }

    public void specialMove(float delta) { //should be removed once cleanup is finished
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        Hitboxes hitbox = getHitbox();
        hitbox.updateHitboxes(velocity.x * delta, velocity.y * delta);
    }

    public Hitboxes getHitbox() { return hitbox; }
    public Vector2 getPosition() { return position.cpy(); }
    public Vector2 getSize() { return size.cpy(); }
    public Vector2 getVelocity() { return velocity.cpy(); }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }
}
