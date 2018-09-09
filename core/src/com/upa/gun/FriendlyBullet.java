package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class FriendlyBullet extends Bullet {
    FriendlyBullet(float x, float y, double angle, World world) {
        super(x, y, angle, world, Assets.bulletLaser);
    }
    
    public void update (float delta) {
    	super.update(delta);
    	double vx = Math.cos(angle) * Settings.BULLET_SPEED;
        double vy = Math.sin(angle) * Settings.BULLET_SPEED;
        if (Settings.SLOW_BULLETS) {
            vx *= 0.1f;
            vy *= 0.1f;
        }

        body.setLinearVelocity((float) vx, (float) vy);
    }
}
