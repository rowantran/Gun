package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.Bullet;
import com.upa.gun.Settings;

public class EnemyBullet extends Bullet {
    public EnemyBullet(Vector2 position, double angle, TextureRegion texture) {
        super(position, new Vector2(texture.getRegionWidth(), texture.getRegionHeight()), angle, Settings.ENEMY_BULLET_SPEED_MULTIPLIER);
    }
}