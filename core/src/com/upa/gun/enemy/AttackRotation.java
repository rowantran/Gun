package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

class AttackRotation {
    private float timeElapsed;
    private float timeElapsedSinceAttack;

    List<Attack> attacks;
    private int currentAttack;

    AttackRotation() {
        attacks = new ArrayList<Attack>();
        currentAttack = 0;
    }

    void cycle(float delta, Vector2 position) {
        timeElapsed += delta;
        timeElapsedSinceAttack += delta;

        if (timeElapsedSinceAttack >= attacks.get(currentAttack).interval()) {
            attack(position);
        }

        if (timeElapsed >= attacks.get(currentAttack).length()) {
            currentAttack++;
            timeElapsed = 0f;
            if (currentAttack >= attacks.size()) {
                currentAttack = 0;
            }
        }
    }

    Attack currentAttack() {
        return attacks.get(currentAttack);
    }

    void attack(Vector2 position) {
        Gdx.app.debug("AttackRotation", "Casting attack");
        currentAttack().attack(position);
        timeElapsedSinceAttack = 0f;
    }

    /**
     * @return A copy of this rotation for a new enemy
     */
    AttackRotation copy() {
        AttackRotation cpy = new AttackRotation();
        cpy.attacks.addAll(attacks);

        return cpy;
    }
}