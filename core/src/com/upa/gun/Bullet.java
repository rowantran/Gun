package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bullet extends Entity {
    double angle;

    boolean markedForDeletion;

    Bullet(float x, float y, double angle, float width, float height) {
        super(x, y, width, height);
        this.angle = angle;

        markedForDeletion = false;
    }

    @Override
    void createHitbox(float width, float height) {
        Vector2 position = getPosition();
        hitbox = new RectangularHitbox(position.x, position.y, width, height);
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