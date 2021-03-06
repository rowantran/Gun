package com.upa.gun;

/**
 * Handles death
 */
class PlayerDyingState extends PlayerState {
    PlayerDyingState() {
        controllable = false;
    }

    /**
     * Rotates player for 90 degrees, then fades out and displays game over screen
     * @param delta Frame time for current tick
     */
    @Override
    public void update(float delta) {
        timeElapsed += delta;
        controllable = false;

        if(rotation < 90.0f) { //rotates to 90 degrees
            rotation += Settings.DEATH_ROTATE_SPEED * delta;
        } else if(opacity > 0.0f) { //fades out
            opacity -= Settings.DEATH_FADE_SPEED * delta;
        } else { //displays game over screen
            resetState();
            game.setScreen(new GameOver(game));
        }
    }
}
