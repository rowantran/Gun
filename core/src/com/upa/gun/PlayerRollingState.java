package com.upa.gun;

/**
 * Handles Rolling
 */
class PlayerRollingState extends PlayerState {
    @Override
    public void update(float delta) {
        controllable = false;
        timeElapsed += delta;
        System.out.println("rolling state");

        if(timeElapsed < Settings.ROLL_LENGTH) {

        }
    }
}
