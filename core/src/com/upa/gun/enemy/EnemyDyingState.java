package com.upa.gun.enemy;

import java.util.ListIterator;

public class EnemyDyingState extends EnemyState {
    @Override
    public void update(float delta) {}

    @Override
    public void iterationUpdate(ListIterator<Enemy> iterator) {
        iterator.remove();
    }
}
