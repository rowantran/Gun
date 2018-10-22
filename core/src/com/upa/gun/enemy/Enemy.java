package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.upa.gun.*;

import java.util.Map;

public class Enemy extends Entity {
    public float timeElapsed;
    float timeSinceAttack;
    public boolean dying;
    public boolean markedForDeletion;
    AttackRotation rotation;
    public float opacity;
    private Map<SpriteState, Map<Direction, Animation<TextureRegion>>> sprite;
    public SpriteState state;

    private EnemyInfo info;

    Enemy(EnemyInfo info, float x, float y) {
        super(x, y, info.width, info.height, 0, 0);
        this.info = info;

        createHitbox(info.hitboxType, info.hitboxWidth, info.hitboxHeight);
        timeElapsed = 20.0f;
        dying = false;
        markedForDeletion = false;
        sprite = loadSprite(info.sprite);
        state = SpriteState.IDLE;
        opacity = 1f;
    }

    private void createHitbox(String hitboxType, int width, int height) {
        if (hitboxType.equals("rectangular")) {
            hitbox = new RectangularHitbox(getPosition().x, getPosition().y, width, height);
        }

        centerHitbox();
    }

    public Map<SpriteState, Map<Direction, Animation<TextureRegion>>> sprite() {
        return sprite;
    }

    public SpriteState getState() {
        return state;
    }

    public void update(float delta) {
        super.update(delta);
        timeElapsed += delta;
        rotation.cycle(delta);
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    private Map<SpriteState, Map<Direction, Animation<TextureRegion>>> loadSprite(String sprite) {
        return Assets.playerAnimations;
    }
}
