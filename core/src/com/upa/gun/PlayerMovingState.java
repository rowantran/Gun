package com.upa.gun;

public class PlayerMovingState  extends PlayerState {

    void update(float delta) {

        timeElapsed += delta;
        textureState = SpriteState.MOVING;
        System.out.println("moving state");

    }

}
