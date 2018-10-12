package com.upa.gun;

class FriendlyBullet extends Bullet {
    FriendlyBullet(float x, float y, double angle) {
        super(x, y, angle, Assets.bulletEnemy.getRegionWidth(), Assets.bulletEnemy.getRegionHeight());
    }
}
