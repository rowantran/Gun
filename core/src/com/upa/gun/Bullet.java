package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Bullet extends Entity {
    double angle;

    private float HITBOX_SIZE;
    private Hitboxes hitbox;

    boolean markedForDeletion;

    float speedMult;

    public Bullet(Vector2 position, Vector2 size, double angle, float speedMult) {
        super(position, size);
        this.angle = angle;

        HITBOX_SIZE = 10f;
        createHitbox();

        markedForDeletion = false;

        this.speedMult = speedMult;
    }

    private void createHitbox() {
        Vector2 position = getPosition();
        hitbox = new Hitboxes();
        RectangularHitbox center = new RectangularHitbox(position, new Vector2(HITBOX_SIZE, HITBOX_SIZE));
        center.setPosition(new Vector2(getPosition().x + getSize().x/2 - center.getWidth()/2, getPosition().y + getSize().y/2 - center.getHeight()/2));
        hitbox.addHitbox("center", center);
        hitbox.setActive(true);
    }

    @Override
    public Hitboxes getHitbox() {
        return hitbox;
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