package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.upa.gun.Bullet;

class EnemyBullet extends Bullet {
    EnemyBullet(float x, float y, double angle, TextureRegion texture) {
        super(x, y, angle, texture.getRegionWidth(), texture.getRegionHeight());
    }
}