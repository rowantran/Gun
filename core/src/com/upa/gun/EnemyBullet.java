package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class EnemyBullet extends Bullet {
    EnemyBullet(float x, float y, double angle, TextureRegion texture) {
        super(x, y, angle, texture.getRegionWidth(), texture.getRegionHeight());
    }
}