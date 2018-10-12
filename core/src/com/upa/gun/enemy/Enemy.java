package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.upa.gun.Direction;
import com.upa.gun.Entity;
import com.upa.gun.SpriteState;

import java.util.Map;

public abstract class Enemy extends Entity {
    public float timeElapsed;
    float timeSinceAttack;
    public boolean dying;
    public boolean markedForDeletion;
    AttackRotation rotation;
    public float opacity;
    private Map<SpriteState, Map<Direction, Animation<TextureRegion>>> sprite;
    public SpriteState state;

    Enemy(float x, float y, float width, float height) {
        super(x, y, width, height, 0, 0);
        timeElapsed = 20.0f;
        dying = false;
        markedForDeletion = false;
        sprite = loadSprite();
        state = SpriteState.IDLE;
        opacity = 1f;
    }

    abstract Map<SpriteState, Map<Direction, Animation<TextureRegion>>> loadSprite();

    public Map<SpriteState, Map<Direction, Animation<TextureRegion>>> sprite() {
        return sprite;
    }

    public SpriteState getState() {
        return state;
    }

    abstract Enemy create(float x, float y);

    public void update(float delta) {
        super.update(delta);
        timeElapsed += delta;
        rotation.cycle(delta);
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }
}
