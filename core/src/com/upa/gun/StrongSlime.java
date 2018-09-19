package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class StrongSlime extends Slime {
    class StrongSlimeAttackRotation extends AttackRotation {
        StrongSlimeAttackRotation() {
            attacks.add(new TrackingBurstAttack(0.75f, 0.075f, true));
            attacks.add(new NoAttack(3.0f, true));
        }
    }

    public StrongSlime(float x, float y, World world, GunWorld gunWorld) {
        super(x, y, world, gunWorld);
        speedMultiplier = 0.75f;
        rotation = new StrongSlimeAttackRotation();
    }
}
