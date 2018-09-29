package com.upa.gun;

/**
 * Handles movement
 */
public class PlayerMovingState extends PlayerState {

    /**
     * Sets move state and increments time
     * @param delta - time value
     */
    void update(float delta) {

        controllable = true;
        timeElapsed += delta;
        textureState = SpriteState.MOVING;
        System.out.println("moving state");

    }

}
