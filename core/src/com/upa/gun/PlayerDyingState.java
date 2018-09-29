package com.upa.gun;

public class PlayerDyingState extends PlayerState {

    public PlayerDyingState() {

    }

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.IDLE;
        System.out.println("dying state");

        if(rotation < 90.0f) {
            rotation += Settings.DEATH_ROTATE_SPEED * delta;
        } else if(opacity > 0.0f) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
        } else {
            resetState();
            game.setScreen(new GameOver(game));
        }

    }

}
