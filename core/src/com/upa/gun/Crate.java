package com.upa.gun;

//TODO: parent class for all objects to include collsion, etc.?

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Crate {

    public float x;
    public float y;

    Body body;
    Vector2[] vertices;

    Sprite crateSprite;

    public Crate(float x, float y, Texture texture) {

        crateSprite = new Sprite(texture);
        crateSprite.setScale(1);

        this.x = x;
        this.y = y;
    }
}
