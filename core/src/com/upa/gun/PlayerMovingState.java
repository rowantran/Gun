package com.upa.gun;

/**
 * Handles movement
 */
class PlayerMovingState extends PlayerState {
    PlayerMovingState() {
        controllable = true;
    }

    /**
     * Set move state and increments time
     * @param delta - time value
     */
    @Override
    public void update(float delta) {
        timeElapsed += delta;
        super.checkIframe(delta);
        controllable = true;
    }

    @Override
    public SpriteState getTextureState() {
        return SpriteState.MOVING;
    }
}
