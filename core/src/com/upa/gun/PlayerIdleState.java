package com.upa.gun;

/**
 * Handles idling
 */
public class PlayerIdleState extends PlayerState {
    /**
     * Sets idle state and increments time
     * @param delta - time value
     */
    @Override
    public void update(float delta) {
        controllable = true;
        timeElapsed += delta;
        System.out.println("idle state");
    }

    @Override
    public SpriteState getTextureState() {
        return SpriteState.IDLE;
    }
}
