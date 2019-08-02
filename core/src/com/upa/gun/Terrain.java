package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public class Terrain extends Entity {

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

    public void setDisplaySide(boolean displaySide) { this.displaySide = displaySide; }

    public boolean getDisplaySide() { return displaySide; }

    public void resetPosition() {
        setPosition(originalPosition.cpy());
        hitbox.setPosition(originalPosition.cpy());
    }

}
