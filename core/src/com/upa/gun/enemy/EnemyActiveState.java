package com.upa.gun.enemy;

import java.util.ListIterator;

public class EnemyActiveState extends EnemyState {
    @Override
    public void update(float delta) {}

    public int mobileType() { return 1; }

    @Override
    public void iterationUpdate(ListIterator<Enemy> iterator) {}
}
