package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class EnemyBullet extends Bullet {
    EnemyBullet(float x, float y, double angle, World world, TextureRegion texture) {
        super(x, y, angle, world, texture);
    }
    
    public void update (float delta) {
    	super.update(delta);
    	double vx = Math.cos(angle) * Settings.ENEMY_BULLET_SPEED;
        double vy = Math.sin(angle) * Settings.ENEMY_BULLET_SPEED;
        if (Settings.SLOW_BULLETS) {
            vx *= 0.1f;
            vy *= 0.1f;
        }

        body.setLinearVelocity((float) vx, (float) vy);
    }
}
