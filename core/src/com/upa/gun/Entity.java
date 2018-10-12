package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements Updatable {
    private Vector2 position;
    private Vector2 size;
    private Vector2 velocity;
    private float rotation;

    Hitbox hitbox;
    private Vector2 hitboxOffset; // Represents where the hitbox should be placed relative to the entity's position

    float attackTimeElapsed;

    Entity(Vector2 position, Vector2 size, Vector2 hitboxOffset) {
        this.position = position.cpy();
        this.size = size.cpy();
        velocity = new Vector2(0f, 0f);
        rotation = 0f;

        this.hitboxOffset = hitboxOffset.cpy();
        createHitbox();

        attackTimeElapsed = 0f;
    }

    Entity(float x, float y, float width, float height, float hitboxOffsetX, float hitboxOffsetY) {
        this(new Vector2(x, y), new Vector2(width, height), new Vector2(hitboxOffsetX, hitboxOffsetY));
    }

    abstract void createHitbox();

    void centerHitbox() {
        setHitboxOffset((getSize().x - hitbox.getWidth()) / 2, (getSize().y - hitbox.getHeight()) / 2);
    }

    @Override
    public void update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        // Update hitbox to match new position
        hitbox.setX(position.x + hitboxOffset.x);
        hitbox.setY(position.y + hitboxOffset.y);
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    Vector2 getSize() {
        return size.cpy();
    }

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }

    float getRotation() {
        return rotation;
    }

    void setRotation(float rotation) {
        this.rotation = rotation;
    }

    Vector2 getHitboxOffset() {
        return hitboxOffset.cpy();
    }

    void setHitboxOffset(float x, float y) {
        hitboxOffset.x = x;
        hitboxOffset.y = y;
    }
}
