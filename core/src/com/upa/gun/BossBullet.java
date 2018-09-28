package com.upa.gun;

class BossBullet extends EnemyBullet {
    BossBullet(float x, float y, double angle) {
        super(x, y, angle, Assets.bulletBoss);
    }
}
