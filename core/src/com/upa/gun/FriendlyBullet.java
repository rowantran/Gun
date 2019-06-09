package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

class FriendlyBullet extends Bullet {
    FriendlyBullet(Vector2 position, double angle) {
        super(position, new Vector2(Assets.bulletEnemy.getRegionWidth(), Assets.bulletEnemy.getRegionHeight()), angle, 1.0f);
    }
}
