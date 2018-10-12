package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.upa.gun.Assets;
import com.upa.gun.Direction;
import com.upa.gun.SpriteState;

import java.util.Map;

class StrongSlime extends Slime {
    class StrongSlimeAttackRotation extends AttackRotation {
        StrongSlimeAttackRotation() {
            attacks.add(new TrackingBurstAttack(0.75f, 0.075f, true));
            attacks.add(new NoAttack(3.0f, true));
        }
    }

    StrongSlime(float x, float y) {
        super(x, y);
        speedMultiplier = 0.75f;
        rotation = new StrongSlimeAttackRotation();
    }

    @Override
    Map<SpriteState, Map<Direction, Animation<TextureRegion>>> loadSprite() {
        return Assets.strongSlimeAnimations;
    }

    @Override
    Enemy create(float x, float y) {
        return new StrongSlime(x, y);
    }
}
