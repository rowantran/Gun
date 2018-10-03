package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements Updatable {
    private Vector2 position;
    private Vector2 size;
    private Vector2 velocity;
    private float rotation;

    Hitbox hitbox;

    float attackTimeElapsed;

    Entity(float x, float y, float width, float height) {
        position = new Vector2(x, y);
        size = new Vector2(width, height);
        velocity = new Vector2(0f, 0f);
        rotation = 0f;
        attackTimeElapsed = 0f;
        createHitbox(width, height);
    }

    abstract void createHitbox(float width, float height);

    Vector2 getPosition() {
        return position.cpy();
    }

    void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    Vector2 getSize() {
        return size.cpy();
    }

    Vector2 getVelocity() {
        return velocity.cpy();
    }

    void setVelocity(float x, float y) {
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

    @Override
    public void update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        // Update hitbox to match new position
        hitbox.setX(position.x);
        hitbox.setY(position.y);
    }
}
