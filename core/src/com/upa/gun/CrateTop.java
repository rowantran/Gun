package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class CrateTop {

    private Hitbox hitbox;

    public float x;
    public float y;

    Sprite crateTopSprite;

    CrateTop(float x, float y) {
        crateTopSprite = new Sprite(Assets.crateTop);
        crateTopSprite.setScale(1);

        this.x = x;
        this.y = y;
    }
}
