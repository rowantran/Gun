package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

/**
 * Parent class for all static map elements
 * Stores original position for use in room transitions and determines if sides need to be drawn based on position
 */
public abstract class Terrain extends Entity {

    protected Vector2 originalPosition;
    protected boolean displaySide;

    public Terrain(Vector2 position) {
        this(position, new Vector2(64f, 64f));
    }

    public Terrain(Vector2 position, Vector2 size) {
        super(position, size);
        originalPosition = position.cpy();
        displaySide = true;
    }

    /**
     * Returns object position to intended location in current room
     */
    public void resetPosition() {
        setPosition(originalPosition.cpy());
        hitbox.setPosition(originalPosition.cpy());
    }

    public boolean getDisplaySide() { return displaySide; }
    public void setDisplaySide(boolean displaySide) { this.displaySide = displaySide; }
}
