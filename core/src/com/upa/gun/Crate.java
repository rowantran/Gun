package com.upa.gun;

//TODO: parent class for all objects to include collsion, etc.?

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Crate {

    public float x;
    public float y;

    Sprite crateSprite;

    public Crate(float x, float y, Texture texture) {

        crateSprite = new Sprite(texture);

        this.x = x;
        this.y = y;
    }





}
