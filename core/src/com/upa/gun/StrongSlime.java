package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class StrongSlime extends Slime {

    public StrongSlime(float x, float y, World world, GunWorld gunWorld) {
        super(x, y, world, gunWorld);
    }

    public void update(float delta) {
        if (shooting) {
            attackTimeElapsed += delta;
            timeSinceAttack += delta;
        } else {
            timeElapsed += delta;
        }

        if (dying) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
            if (opacity <= 0.0f) {
                dying = false;
                opacity = 0.0f;
                markedForDeletion = true;
            }
        } else {
            if (timeElapsed >= 3) {
                shooting = true;
                if (attackTimeElapsed >= 1.5) {
                    attackTimeElapsed = 0;
                    timeElapsed = 0;
                    shooting = false;
                }
            } else {
                shooting = false;
            }

            move(delta);

            if (timeSinceAttack >= 0.075f) {
                shoot();
                timeSinceAttack = 0.0f;
            }
        }
    }
}
