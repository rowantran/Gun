package com.upa.gun.enemy;

/**
 * Thrown upon loading an entry in enemies.json declaring an invalid hitbox type.
 */
class UnrecognizedHitboxTypeException extends RuntimeException {
    UnrecognizedHitboxTypeException(String hitbox) {
        super("Unrecognized hitbox type: " + hitbox);
    }
}
