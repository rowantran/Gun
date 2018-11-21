package com.upa.gun.enemy;

import com.upa.gun.Updatable;

import java.util.ListIterator;

public abstract class EnemyState implements Updatable {
    /**
     * Called when the enemy is iterated upon after the update cycle.
     * @param iterator The iterator containing this enemy. Can be used to delete this enemy.
     */
    public abstract void iterationUpdate(ListIterator<Enemy> iterator);
}
