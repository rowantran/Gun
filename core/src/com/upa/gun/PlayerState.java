package com.upa.gun;

/**
 * Returns current state of player and handles updates
 */
public abstract class PlayerState implements Updatable {

    static GunGame game;

    boolean controllable;

    protected boolean vulnerable;
    private float iframeTime;
    public float timeElapsed;
    float rotation;
    float opacity;

    static PlayerIdleState idle;
    static PlayerMovingState moving;
    static PlayerDyingState dying;

    static {
        idle = new PlayerIdleState();
        moving = new PlayerMovingState();
        dying = new PlayerDyingState();
    }

    PlayerState() {
        timeElapsed = 0.0f;
        rotation = 0.0f;
        opacity = 1.0f;
        controllable = true;
        iframeTime = 0f;
    }

    /**
      * @return Whether the player can take damage in this state
     */
    boolean isVulnerable() {
        return vulnerable;
    }

    /**
     * Resets the tick counter every time state changes
     */
    void resetState() { //should reset timer every time state changes?
        timeElapsed = 0.0f;
        rotation = 0.0f;
        opacity = 1.0f;
    }

    void checkIframe(float delta) {
        if(!vulnerable) {
            iframeTime += delta;
            opacity = 0.6f;
            if (iframeTime > Settings.IFRAME_AFTER_HIT_LENGTH) {
                vulnerable = true;
                iframeTime = 0f;
                opacity = 1.0f;
            }
        }
    }

    void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    /**
     * Sets the GunGame for use in the dying state
     */
    void setGame(GunGame game) {
        PlayerState.game = game;
    }

    /**
     * Sets the state and resets state variables
     * @param newState
     */
    void setState(PlayerState newState) {
        resetState();
        game.world.player.state = newState;
    }

    /**
     * Returns the state of player
     * @return SpriteState - returns SpriteState enum
     */
    public SpriteState getTextureState() {
        return SpriteState.IDLE;
    }
}