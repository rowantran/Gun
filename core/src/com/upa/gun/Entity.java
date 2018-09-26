package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public class Entity implements Updatable {
    Vector2 position;
    private Vector2 velocity;
    private float rotation;
    Entity(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0f, 0f);
        rotation = 0f;
    }

    void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }

    void modifyVelocity(float x, float y) {
        velocity.x += x;
        velocity.y += y;
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
    }
}
