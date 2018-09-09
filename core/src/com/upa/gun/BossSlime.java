package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class BossSlime extends Slime {

    public BossSlime(float x, float y, World world, GunWorld gunWorld) {
        super(x, y, world, gunWorld);
        shotInterval = 0.075f;
    }
}
