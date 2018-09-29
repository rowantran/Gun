package com.upa.gun;

public abstract class PlayerState {


    float timeElapsed;
    SpriteState textureState;

    static PlayerIdleState idle;
    static PlayerMovingState moving;
    static PlayerIFrameState iframe;
    static PlayerRollingState rolling;

    static {
        idle = new PlayerIdleState();
        moving = new PlayerMovingState();
        iframe = new PlayerIFrameState();
        rolling = new PlayerRollingState();
    }

    public PlayerState() {
        timeElapsed = 0.0f;
    }

    abstract void update(float delta);

    public void resetTime() { //should reset timer every time state changes?
        timeElapsed = 0.0f;
    }

    public SpriteState getTextureState() {
        return textureState;
    }

}
