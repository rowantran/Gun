package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public class CircularAttack implements Attack {
    private float attackLength;
    private float attackInterval;
    private boolean mobile;

    static double angleInterval = Math.PI / 16;

    CircularAttack(float attackLength, float attackInterval, boolean mobile) {
        this.attackLength = attackLength;
        this.attackInterval = attackInterval;
        this.mobile = mobile;
    }

    @Override
    public void attack(Vector2 position) {
        for (int i = 0; i<32; i++) {
            double angle = angleInterval * (double) i;
            World.enemyBullets.add(new BossBullet(position.x, position.y, angle));
        }
    }

    @Override
    public float length() {
        return attackLength;
    }

    @Override
    public float interval() {
        return attackInterval;
    }

    @Override
    public boolean isMobile() {
        return mobile;
    }
}
