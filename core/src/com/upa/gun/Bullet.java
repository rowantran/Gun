package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

/**
 * Class for all bullets
 */
public abstract class Bullet extends Entity {

    private double angle;
    boolean markedForDeletion;
    private float speedMult;

    public Bullet(Vector2 position, Vector2 size, double angle, float speedMult) {
        super(position, size);
        this.angle = angle;

        RectangularHitbox center = new RectangularHitbox(position, new Vector2(10, 10));
        center.setPosition(new Vector2(position.x + size.x/2 - center.getWidth()/2, position.y + size.y/2 - center.getHeight()/2));
        hitbox.addHitbox("center", center);
        hitbox.generateCorrectOffsets();
        hitbox.setActive(true);

        markedForDeletion = false;
        this.speedMult = speedMult;
    }

    /**
     * Update function; finds correct speed based on angle and deletes bullet if off-screen
     * @param delta - Clock
     */
    @Override
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