package com.upa.gun;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class BossSlimeFactory {
    BossSlime makeBossSlime(int x, int y, World world, GunWorld gunWorld) {
        CircleShape hitbox = new CircleShape();
        hitbox.setRadius(BossSlime.hitboxRadius);

        return new BossSlime(x, y, world, gunWorld, hitbox);
    }
}
