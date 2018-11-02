package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.World;

import java.util.ArrayList;
import java.util.List;

class AttackRotation {
    private float timeElapsed;
    private float timeElapsedSinceAttack;
    private boolean attacking;

    List<Attack> attacks;
    private int currentAttack;

    AttackRotation() {
        attacks = new ArrayList<Attack>();
        currentAttack = 0;
        attacking = false;
    }

    void cycle(float delta) {
        timeElapsed += delta;
        timeElapsedSinceAttack += delta;

        if (timeElapsedSinceAttack >= attacks.get(currentAttack).interval()) {
            attacking = true;
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
        attacking = false;
        timeElapsedSinceAttack = 0f;
    }

    boolean isAttacking() {
        return attacking;
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