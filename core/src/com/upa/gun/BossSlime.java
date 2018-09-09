package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BossSlime extends Slime {
    boolean hurt;

	double interval;

	int health;

	float timeHurt;
	static float timeStayHurt = 0.5f;

	static float hitboxRadius = 50f;

    public BossSlime(int health, float x, float y, World world, GunWorld gunWorld, Shape hitbox) {
        super(x, y, world, gunWorld, hitbox);
        timeBetweenAttacks = 8.0f;
        shotInterval = 0.075f;
        speedMultiplier = 0.5f;
        
        interval = Math.PI/16;

        this.health = health;

        hurt = false;

        timeHurt = 0f;
    }
    
    public void shoot() {
        if (shooting) {
            Vector2 slimePos = body.getTransform().getPosition();
            for (int i = 0; i<32; i++) {
            	double angle = interval * (double) i;
            	gunWorld.bullets.add(new BossBullet(slimePos.x, slimePos.y, angle,
                        world));
            }
            fireSound();
        }
    }

    public void update(float delta) {
        super.update(delta);
        if (hurt) {
            timeHurt += delta;
            if (timeHurt >= timeStayHurt) {
                timeHurt = 0f;
                hurt = false;
            }
        }

        if (health == 0) {
            Assets.bossDieSound.stop();
            Assets.bossDieSound.play(1.0f);
            dying = true;
        }
        //System.out.println(health);
    }
}
