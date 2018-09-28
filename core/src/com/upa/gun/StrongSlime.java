package com.upa.gun;

public class StrongSlime extends Slime {
    class StrongSlimeAttackRotation extends AttackRotation {
        StrongSlimeAttackRotation() {
            attacks.add(new TrackingBurstAttack(0.75f, 0.075f, true));
            attacks.add(new NoAttack(3.0f, true));
        }
    }

    StrongSlime(float x, float y) {
        super(x, y);
        speedMultiplier = 0.75f;
        rotation = new StrongSlimeAttackRotation();
    }
}
