package com.upa.gun;

class BossSlimeFactory {
    BossSlime makeBossSlime(int health, float x, float y) {
        return new BossSlime(health, x, y);
    }
}
