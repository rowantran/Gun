package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class CrateSide {

    public float x;
    public float y;

    Sprite crateSideSprite;

    CrateSide(float x, float y) {
        crateSideSprite = new Sprite(Assets.crateSide);
        crateSideSprite.setScale(1);

        this.x = x;
        this.y = y;
    }
}
