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
    private Hitbox hitbox;

    class SlimeAttackRotation extends AttackRotation {
        SlimeAttackRotation() {
            attacks.add(new TrackingBurstAttack(0.75f, 0.15f, true));
            attacks.add(new NoAttack(3.0f, true));
        }
    }

    Enemy(EnemyInfo info, float x, float y) {
        super(x, y, info.width, info.height, 0, 0);

        try {
            createHitbox(info.hitboxType, info.hitboxWidth, info.hitboxHeight);
        } catch (UnrecognizedHitboxTypeException e) {
            e.printStackTrace();
        }

        timeElapsed = 20.0f;
        dying = false;
        markedForDeletion = false;
        sprite = loadSprite(info.sprite);
        state = SpriteState.IDLE;
        opacity = 1f;
        rotation = info.rotation.copy();
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    private void createHitbox(String hitboxType, int width, int height) throws UnrecognizedHitboxTypeException {
        if (hitboxType.equals("rectangular")) {
            hitbox = new RectangularHitbox(getPosition().x, getPosition().y, width, height);
        } else {
            throw new UnrecognizedHitboxTypeException(hitboxType);
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

        if (rotation.isAttacking()) {
            rotation.attack(this.getPosition());
        }
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    private Map<SpriteState, Map<Direction, Animation<TextureRegion>>> loadSprite(String sprite) {
        return Assets.playerAnimations;
    }
}
