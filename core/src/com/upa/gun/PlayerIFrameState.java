package com.upa.gun;

public class PlayerIFrameState extends PlayerState {

    public PlayerIFrameState() {

    }

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.IDLE;

    }

}
