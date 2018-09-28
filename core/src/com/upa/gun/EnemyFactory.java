package com.upa.gun;

class EnemyFactory {
    private Enemy prototype;
    private GunWorld world;

    EnemyFactory(Enemy prototype) {
        this.prototype = prototype;
        world = GunWorld.getInstance();
    }

    void spawn(float x, float y) {
        world.enemies.add(prototype.create(x, y));
    }
}
