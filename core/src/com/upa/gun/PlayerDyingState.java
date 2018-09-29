package com.upa.gun;

public class PlayerDyingState extends PlayerState {

    public PlayerDyingState() {

    }

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.HURT;

    }

}
