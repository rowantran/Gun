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
        controllable = true;
        checkIframe(delta);
    }

    @Override
    public SpriteState getTextureState() {
        return SpriteState.IDLE;
    }
}
