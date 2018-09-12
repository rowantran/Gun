package com.upa.gun;

//TODO: parent class for all objects to include collsion, etc.?

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Crate {

    public float x;
    public float y;

    Body body;

    Sprite crateSprite;

    public Crate(float x, float y, Texture texture, World world) {

        crateSprite = new Sprite(texture);

        this.x = x;
        this.y = y;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);
        body.setTransform(x, y, (float) 0);

        PolygonShape crateBox = new PolygonShape();
        crateBox.setAsBox(crateSprite.getWidth(), crateSprite.getHeight()-27);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = crateBox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

    }





}
