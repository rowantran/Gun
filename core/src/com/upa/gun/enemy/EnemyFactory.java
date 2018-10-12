package com.upa.gun.enemy;

import com.upa.gun.World;

public class EnemyFactory {
    private Enemy prototype;

    EnemyFactory(Enemy prototype) {
        this.prototype = prototype;
    }

    void spawn(float x, float y) {
        World.enemies.add(prototype.create(x, y));
    }
}
