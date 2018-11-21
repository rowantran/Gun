package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.upa.gun.Bullet;
import com.upa.gun.Settings;

public class EnemyBullet extends Bullet {
    public EnemyBullet(float x, float y, double angle, TextureRegion texture) {
        super(x, y, angle, texture.getRegionWidth(), texture.getRegionHeight(), Settings.ENEMY_BULLET_SPEED_MULTIPLIER);
    }
}