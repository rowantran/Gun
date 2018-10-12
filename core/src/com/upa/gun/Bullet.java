package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Bullet extends Entity {
    double angle;

    private float HITBOX_SIZE;

    boolean markedForDeletion;

    Bullet(float x, float y, double angle, float width, float height) {
        super(x, y, width, height, 0, 0);
        this.angle = angle;

        HITBOX_SIZE = 20f;
        createHitbox();

        markedForDeletion = false;
    }

    @Override
    void createHitbox() {
        Vector2 position = getPosition();
        hitbox = new RectangularHitbox(position.x, position.y, HITBOX_SIZE, HITBOX_SIZE);
        centerHitbox();
    }

    public void update(float delta) {
        super.update(delta);
        Vector2 position = getPosition();
        if(position.x < 0 || position.x > Settings.RESOLUTION.x || position.y < 0 || position.y > Settings.RESOLUTION.y) {
            markedForDeletion = true;
        }

    	float vx = (float) Math.cos(angle) * Settings.BULLET_SPEED;
    	float vy = (float) Math.sin(angle) * Settings.BULLET_SPEED;
        if (Settings.SLOW_BULLETS) {
            vx *= 0.1f;
            vy *= 0.1f;
        }

        setVelocity(vx, vy);
    }
}