package com.upa.gun;

import java.util.List;

abstract class AttackRotation {
    private float timeElapsed;

    List<Attack> attacks;
    private int currentAttack;

    void cycle(float delta) {
        timeElapsed += delta;
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
}