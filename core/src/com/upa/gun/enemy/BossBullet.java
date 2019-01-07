package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.Assets;

public class BossBullet extends EnemyBullet {
    public BossBullet(Vector2 position, double angle) {
        super(position, angle, Assets.bulletBoss);
    }
}
