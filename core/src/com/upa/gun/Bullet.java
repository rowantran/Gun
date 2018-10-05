package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Bullet extends Entity {
    double angle;

    boolean markedForDeletion;

    Bullet(float x, float y, double angle, float width, float height) {
        super(x, y, width, height, 0, 0);
        this.angle = angle;

        markedForDeletion = false;
    }

    @Override
    void createHitbox(float width, float height) {
        Vector2 position = getPosition();
        hitbox = new RectangularHitbox(position.x, position.y, width, height);
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

        setVelocity(vx, vy);
    }
}
