package com.upa.gun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Crate {

    private Hitbox hitbox;

    public float x;
    public float y;

    Sprite crateSprite;

    Crate(float x, float y, Texture texture) {
        crateSprite = new Sprite(texture);
        crateSprite.setScale(1);

        this.x = x;
        this.y = y;
    }
}
