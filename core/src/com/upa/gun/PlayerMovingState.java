package com.upa.gun;

/**
 * Handles movement
 */
class PlayerMovingState extends PlayerState {
    /**
     * Set move state and increments time
     * @param delta - time value
     */
    @Override
    public void update(float delta) {
        controllable = true;
        timeElapsed += delta;
        System.out.println("moving state");
    }

    @Override
    public SpriteState getTextureState() {
        return SpriteState.MOVING;
    }
}
