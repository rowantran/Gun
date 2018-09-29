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

        controllable = false;
        timeElapsed += delta;
        textureState = SpriteState.IDLE;
        System.out.println("rolling state");

    }

}
