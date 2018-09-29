package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Map;

public class BossSlime extends Slime {
    boolean hurt;

	int health;

	float timeHurt;
	static float timeStayHurt = 0.5f;

	static float hitboxRadius = 50f;

	class BossSlimeRotation extends AttackRotation {
	    BossSlimeRotation() {
	        attacks.add(new CircularAttack(0.75f, 0.075f, false));
	        attacks.add(new NoAttack(2.0f, true));
	        attacks.add(new TrackingBurstAttack(1.5f, 0.075f, true));
	        attacks.add(new NoAttack(2.0f, true));
        }
    }

    BossSlime(int health, float x, float y) {
        super(x, y);
        speedMultiplier = 0.5f;

        this.health = health;

        hurt = false;

        timeHurt = 0f;

        rotation = new BossSlimeRotation();
    }

    @Override
    Map<SpriteState, Map<Direction, Animation<TextureRegion>>> loadSprite() {
	    return Assets.bossSlimeAnimations;
    }

    @Override
    Enemy create(float x, float y) {
	    return new BossSlime(20, x, y);
    }

    @Override
    void createHitbox(float width, float height) {
	    hitbox = new RectangularHitbox(getPosition().x, getPosition().y, width, height);
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
    }

    public SpriteState getState() {
        if (dying || hurt) {
            return SpriteState.HURT;
        } else if (!(rotation.currentAttack() instanceof NoAttack)) {
            return SpriteState.ATTACKING;
        } else {
            return SpriteState.MOVING;
        }
    }
}
