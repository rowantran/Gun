package com.upa.gun;

public class PlayerIdleState extends PlayerState {

    public PlayerIdleState() {

    }

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.IDLE;
        System.out.println("idle state");

    }


}
