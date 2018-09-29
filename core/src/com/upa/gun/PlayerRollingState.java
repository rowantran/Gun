package com.upa.gun;

/**
 * Handles Rolling
 */
public class PlayerRollingState extends PlayerState{

    /**
     *
     * @param delta - time value
     */
    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.IDLE;

    }

}
