package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

/**
 * Handles Rolling
 */
class PlayerRollingState extends PlayerState {
    private Vector2 velocity;

    PlayerRollingState(Direction direction) {
        velocity = Direction.getAngle(direction).setLength(Settings.ROLL_SPEED);
    }

    @Override
    public void update(float delta) {
        World.player.setVelocity(velocity);

        controllable = false;
        timeElapsed += delta;
        System.out.println("rolling state");

        if (timeElapsed > Settings.ROLL_LENGTH) {
            World.player.state = PlayerState.idle;
            timeElapsed = 0f;
        }
    }
}
