package com.upa.gun.enemy;

import com.upa.gun.Assets;

public class BossBullet extends EnemyBullet {
    public BossBullet(float x, float y, double angle) {
        super(x, y, angle, Assets.bulletBoss);
    }
}
