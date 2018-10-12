package com.upa.gun.enemy;

class BossSlimeFactory {
    BossSlime makeBossSlime(int health, float x, float y) {
        return new BossSlime(health, x, y);
    }
}
