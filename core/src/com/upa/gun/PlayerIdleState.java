package com.upa.gun;

/**
 * Handles idling
 */
public class PlayerIdleState extends PlayerState {

    /**
     * Sets idle state and increments time
     * @param delta - time value
     */
    void update(float delta) {

        controllable = true;
        timeElapsed += delta;
        textureState = SpriteState.IDLE;
        System.out.println("idle state");

    }


}
