package com.upa.gun;

public class PlayerMovingState  extends PlayerState {

    public PlayerMovingState() {

    }

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.MOVING;

    }

}
