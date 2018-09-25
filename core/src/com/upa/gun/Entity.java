package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public class Entity {
    Vector2 position;
    Vector2 velocity;
    Entity(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0f, 0f);
    }

    void update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }
}
