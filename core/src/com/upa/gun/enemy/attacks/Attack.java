package com.upa.gun.enemy.attacks;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.enemy.Enemy;

public interface Attack {
    /**
     * Execute one cycle of this attack.
     * @param position The current position of the enemy executing this attack.
     */
    void attack(Vector2 position);

    /**
     * Called when this attack begins.
     * @param enemy The Enemy executing this attack.
     */
    void onBegin(Enemy enemy);

    /**
     * @return The length (in seconds) of this attack.
     */
    float length();

    /**
     * @return The interval between cycles, i.e. calls of attack(), for this attack.
     */
    float interval();

    /**
     * @return Whether the enemy can continue to move while executing this attack.
     */
    boolean isMobile();

    /**
     * @return The sprite key corresponding to this attack.
     */
    String getSprite();
}
