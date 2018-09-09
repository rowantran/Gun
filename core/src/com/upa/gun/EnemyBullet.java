package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class EnemyBullet extends Bullet {
    EnemyBullet(float x, float y, double angle, World world) {
        super(x, y, angle, world);
    }
}
