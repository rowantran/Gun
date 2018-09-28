package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public class StrongSlimeFactory implements EnemyFactory<StrongSlime> {
    @Override
    public StrongSlime create(float x, float y) {
        return new StrongSlime(x, y);
    }
}
