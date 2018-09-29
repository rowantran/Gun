package com.upa.gun;

/**
 * Returns current state of player and handles updates
 */
public abstract class PlayerState {

    public static GunGame game; //for use in dying state

    public float timeElapsed; //counts ticks
    public float rotation; //player rotation
    public float opacity; //player opacity
    SpriteState textureState; //information to choose sprite

    public static PlayerIdleState idle; //when the player is not moving
    public static PlayerMovingState moving; //when the player is using arrow keys
    public static PlayerRollingState rolling; //when the player uses the roll key
    public static PlayerDyingState dying; //when the player dies

    /**
     * sets static variables
     */
    static {
        idle = new PlayerIdleState();
        moving = new PlayerMovingState();
        rolling = new PlayerRollingState();
        dying = new PlayerDyingState();
    }

    /**
     * Constructor
     */
    public PlayerState() {
        textureState = SpriteState.IDLE;
        timeElapsed = 0.0f;
        rotation = 0.0f;
        opacity = 1.0f;
    }

    /**
     * Abstract update function for each state
     * @param delta - time value
     */
    abstract void update(float delta);

    /**
     * Resets the tick counter every time state changes
     */
    public void resetState() { //should reset timer every time state changes?
        timeElapsed = 0.0f;
        rotation = 0.0f;
        opacity = 1.0f;
    }

    /**
     * Sets the GunGame for use in the dying state
     */
    public void setGame(GunGame game) {
        this.game = game;
    }

    /**
     * Returns the state of player
     * @return SpriteState - returns SpriteState enum
     */
    public SpriteState getTextureState() {
        return textureState;
    }

}
