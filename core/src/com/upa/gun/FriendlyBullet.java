package com.upa.gun;

public class FriendlyBullet extends Bullet {
    FriendlyBullet(float x, float y, double angle) {
        super(x, y, angle, Assets.bulletLaser.getRegionWidth(), Assets.bulletLaser.getRegionHeight());
    }
    
    public void update (float delta) {

    }
}
