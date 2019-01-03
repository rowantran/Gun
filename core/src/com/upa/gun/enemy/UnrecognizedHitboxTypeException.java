package com.upa.gun.enemy;

/**
 * Thrown upon loading an entry in enemies.json declaring an invalid hitbox type.
 */
public class UnrecognizedHitboxTypeException extends RuntimeException {
    public UnrecognizedHitboxTypeException(String hitbox) {
        super("Unrecognized hitbox type: " + hitbox);
    }
}
