package com.upa.gun.enemy.attacks;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.World;
import com.upa.gun.enemy.BossBullet;
import com.upa.gun.enemy.Enemy;

public class CircularAttack implements Attack {
    private static final String SPRITE_KEY = "attacking";

    private float attackLength;
    private float attackInterval;
    private boolean mobile;

    static double angleInterval = Math.PI / 16;

    public CircularAttack(float attackLength, float attackInterval, boolean mobile) {
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
