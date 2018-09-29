package com.upa.gun;

public class PlayerRollingState extends PlayerState{

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.IDLE;

    }

}
