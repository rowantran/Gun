package com.upa.gun;

/**
 * Handles Rolling
 */
class PlayerRollingState extends PlayerState {
    @Override
    public void update(float delta) {
        World.player.setVelocity(400, 400);

        controllable = false;
        timeElapsed += delta;
        System.out.println("rolling state");

        timeElapsed += delta;
        if (timeElapsed > Settings.ROLL_LENGTH) {
            World.player.state = PlayerState.idle;
            timeElapsed = 0f;
        }
    }
}
