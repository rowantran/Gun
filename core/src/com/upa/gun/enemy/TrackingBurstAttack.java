package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.Assets;
import com.upa.gun.World;

public class TrackingBurstAttack implements Attack {
    private static final String SPRITE_KEY = "attacking";

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
        Vector2 bulletAngle = World.player.getPosition().sub(position);
        World.enemyBullets.add(new EnemyBullet(position.x, position.y, bulletAngle.angleRad(), Assets.bulletEnemy));
    }

    @Override
    public void onBegin(Enemy enemy) {
        enemy.setTimeElapsed(0f);
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

    @Override
    public String getSprite() {
        return SPRITE_KEY;
    }
}
