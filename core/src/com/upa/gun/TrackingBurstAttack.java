package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public class TrackingBurstAttack implements Attack {
    private float attackLength;
    private float attackInterval;
    private boolean mobile;

    TrackingBurstAttack(float attackLength, float attackInterval, boolean mobile) {
        this.attackLength = attackLength;
        this.attackInterval = attackInterval;
        this.mobile = mobile;
    }

    @Override
    public void attack(Vector2 position) {
        Vector2 bulletAngle = GunWorld.getInstance().player.getPosition().sub(position);
        GunWorld.getInstance().bullets.add(new EnemyBullet(position.x, position.y, bulletAngle.angleRad(), Assets.bulletEnemy));
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
