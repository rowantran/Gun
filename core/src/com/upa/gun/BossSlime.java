package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BossSlime extends Slime {
    boolean hurt;

	int health;

	float timeHurt;
	static float timeStayHurt = 0.5f;

	static float hitboxRadius = 50f/Settings.PPM;

	class BossSlimeRotation extends AttackRotation {
	    BossSlimeRotation() {
	        attacks.add(new CircularAttack(0.75f, 0.075f, false));
	        attacks.add(new NoAttack(2.0f, true));
	        attacks.add(new TrackingBurstAttack(1.5f, 0.075f, true));
	        attacks.add(new NoAttack(2.0f, true));
        }
    }

    BossSlime(int health, float x, float y, World world, GunWorld gunWorld, Shape hitbox) {
        super(x, y, world, gunWorld, hitbox);
        speedMultiplier = 0.5f;

        this.health = health;

        hurt = false;

        timeHurt = 0f;

        rotation = new BossSlimeRotation();
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

    public ActionState getState() {
        if (dying || hurt) {
            return ActionState.HURT;
        } else if (!(rotation.currentAttack() instanceof NoAttack)) {
            return ActionState.ATTACKING;
        } else {
            return ActionState.MOVING;
        }
    }
}
