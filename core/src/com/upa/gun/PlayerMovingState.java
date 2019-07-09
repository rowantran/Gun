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
        if (!vulnerable) {
            iframeTime += delta;
            if (iframeTime > Player.IFRAME_AFTER_HIT_LENGTH) {
                vulnerable = true;
            }
        }
        controllable = true;
    }

    @Override
    public SpriteState getTextureState() {
        return SpriteState.MOVING;
    }
}
