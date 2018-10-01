package com.upa.gun;

/**
 * Handles idling
 */
public class PlayerIdleState extends PlayerState {
    PlayerIdleState() {
        controllable = true;
    }

    /**
     * Sets idle state and increments time
     * @param delta - time value
     */
    @Override
    public void update(float delta) {
        timeElapsed += delta;
        System.out.println("idle state");
    }

    @Override
    public SpriteState getTextureState() {
        return SpriteState.IDLE;
    }
}
