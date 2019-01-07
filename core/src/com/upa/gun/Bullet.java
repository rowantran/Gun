package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Bullet extends Entity {
    double angle;

    private float HITBOX_SIZE;

    boolean markedForDeletion;

    float speedMult;

    public Bullet(Vector2 position, Vector2 size, double angle, float speedMult) {
        super(position, size);
        this.angle = angle;

        HITBOX_SIZE = 20f;
        createHitbox();

        markedForDeletion = false;

        this.speedMult = speedMult;
    }

    private void createHitbox() {
        Vector2 position = getPosition();
        RectangularHitbox hitbox = new RectangularHitbox(position, new Vector2(HITBOX_SIZE, HITBOX_SIZE));
        centerRectangularHitbox(hitbox);

        hitboxes.addHitbox("hitbox", hitbox);
    }

    public void update(float delta) {
        super.update(delta);
        Vector2 position = getPosition();
        if(position.x < 0 || position.x > Settings.RESOLUTION.x || position.y < 0 || position.y > Settings.RESOLUTION.y) {
            markedForDeletion = true;
        }

    	float vx = (float) Math.cos(angle) * Settings.BULLET_SPEED * speedMult;
    	float vy = (float) Math.sin(angle) * Settings.BULLET_SPEED * speedMult;

        setVelocity(vx, vy);
    }
}