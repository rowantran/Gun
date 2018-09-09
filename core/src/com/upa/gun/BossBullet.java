package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class BossBullet extends EnemyBullet {
    BossBullet(float x, float y, double angle, World world) {
        super(x, y, angle, world, Assets.bulletBoss);
    }
}
