package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

/**
 * Handles Rolling
 */
class PlayerRollingState extends PlayerState {
    private Vector2 velocity;

    PlayerRollingState(Direction direction) {
        velocity = Direction.getAngle(direction).setLength(Settings.ROLL_SPEED);
        controllable = false;
        vulnerable = false;
        opacity = 0.5f;
    }

    /**
     * Set player velocity to a roll in the direction specified at construction, end roll after Settings.ROLL_LENGTH
     * has been reached.
     * @param delta Frame time for current tick
     */
    @Override
    public void update(float delta) {
        World.player.setVelocity(velocity);
        controllable = false;

        timeElapsed += delta;
        //System.out.println(timeElapsed);

        if (timeElapsed > Settings.ROLL_LENGTH) {
            resetState();
            World.player.state = PlayerState.idle;
            System.out.println("iframe over");
            //System.out.println(timeElapsed);
        }
    }

    public void setDirection(Direction direction) {
        velocity = Direction.getAngle(direction).setLength(Settings.ROLL_SPEED);
    }
}
