package com.upa.gun;

public class PlayerRollingState extends PlayerState{

    public PlayerRollingState() {

    }

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.IDLE;

    }

}
