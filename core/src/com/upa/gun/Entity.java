package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements Updatable {
    private Vector2 position;
    private Vector2 velocity;
    private float rotation;
    Hitbox hitbox;

    Entity(float x, float y, float width, float height) {
        position = new Vector2(x, y);
        velocity = new Vector2(0f, 0f);
        rotation = 0f;
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

        // Update hitbox
        hitbox.setX(position.x);
        hitbox.setY(position.y);

        System.out.println(position);
    }
}
