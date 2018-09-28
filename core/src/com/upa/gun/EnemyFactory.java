package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

public interface EnemyFactory<T> {
    T create(float x, float y);
}
