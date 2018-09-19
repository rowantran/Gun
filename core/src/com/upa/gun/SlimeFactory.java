package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class SlimeFactory implements EnemyFactory<Slime> {
    @Override
    public Slime create(float x, float y, World world, GunWorld gunWorld) {
        return new Slime(x, y, world, gunWorld);
    }
}
