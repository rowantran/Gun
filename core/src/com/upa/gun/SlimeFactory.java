package com.upa.gun;

public class SlimeFactory implements EnemyFactory<Slime> {
    @Override
    public Slime create(float x, float y) {
        return new Slime(x, y);
    }
}
