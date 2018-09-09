package com.upa.gun;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BossSlime extends Slime {

	double interval;
	
    public BossSlime(float x, float y, World world, GunWorld gunWorld) {
        super(x, y, world, gunWorld);
        shotInterval = 0.075f;
        speedMultiplier = 0.5f;
        
        interval = Math.PI/16;
    }
    
    public void shoot() {
        if (shooting) {
            Vector2 slimePos = body.getTransform().getPosition();
            for (int i = 0; i<32; i++) {
            	double angle = interval * (double) i;
            	gunWorld.bullets.add(new EnemyBullet(slimePos.x, slimePos.y, angle,
                        world));
            }
        }
    }
}
