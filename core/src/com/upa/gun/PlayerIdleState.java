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
        if (!vulnerable) {
            iframeTime += delta;
            if (iframeTime > Player.IFRAME_AFTER_HIT_LENGTH) {
                vulnerable = true;
                iframeTime = 0f;
            }
        }
    }

    @Override
    public SpriteState getTextureState() {
        return SpriteState.IDLE;
    }
}
