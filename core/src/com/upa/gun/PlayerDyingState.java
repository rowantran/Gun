package com.upa.gun;

/**
 * Handles death
 */
public class PlayerDyingState extends PlayerState {

    /**
     * Rotates player for 90 degrees, then fades out and displays game over screen
     * @param delta - time value
     */
    void update(float delta) {

        controllable = false;
        timeElapsed += delta;
        textureState = SpriteState.IDLE;
        System.out.println("dying state");

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
