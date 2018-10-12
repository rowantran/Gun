package com.upa.gun.enemy;

import com.upa.gun.Assets;

class BossBullet extends EnemyBullet {
    BossBullet(float x, float y, double angle) {
        super(x, y, angle, Assets.bulletBoss);
    }
}
