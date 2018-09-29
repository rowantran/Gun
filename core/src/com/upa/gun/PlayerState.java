package com.upa.gun;

/**
 * Returns current state of player and handles updates
 */
public abstract class PlayerState {


    float timeElapsed; //counts ticks
    SpriteState textureState = SpriteState.IDLE; //information to choose sprite

    public static PlayerIdleState idle; //when the player is not moving
    public static PlayerMovingState moving; //when the player is using arrow keys
    public static PlayerIFrameState iframe; //when the player is invincible after being hit
    public static PlayerRollingState rolling; //when the player uses the roll key

    /**
     * sets static variables
     */
    static {
        idle = new PlayerIdleState();
        moving = new PlayerMovingState();
        iframe = new PlayerIFrameState();
        rolling = new PlayerRollingState();
    }

    /**
     * Constructor
     */
    public PlayerState() {
        timeElapsed = 0.0f;
    }

    /**
     * Abstract update function for each state
     * @param delta - time value
     */
    abstract void update(float delta);

    /**
     * Resets the tick counter every time state changes
     */
    public void resetTime() { //should reset timer every time state changes?
        timeElapsed = 0.0f;
    }

    /**
     * Returns the state of player
     * @return SpriteState - returns SpriteState enum
     */
    public SpriteState getTextureState() {
        return textureState;
    }

}
