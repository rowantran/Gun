package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

interface Attack {
    /**
     * Execute one cycle of this attack.
     * @param position The current position of the enemy executing this attack.
     */
    void attack(Vector2 position);

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
}
