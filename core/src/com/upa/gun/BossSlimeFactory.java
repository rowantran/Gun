package com.upa.gun;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class BossSlimeFactory {
    BossSlime makeBossSlime(int health, int x, int y, World world, GunWorld gunWorld) {
        CircleShape hitbox = new CircleShape();
        hitbox.setRadius(BossSlime.hitboxRadius);

        return new BossSlime(health, x, y, world, gunWorld, hitbox);
    }
}
