package com.upa.gun;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
//is this the right import?


public class Slime extends Enemy {
    int timeSinceAttack;
    Vector2 spawnPoint;
    float opacity;

    Body body;

    int rotation;
    public static int LEFT = 0;
    public static int RIGHT = 1;

    public Slime(float x, float y, World world) {

        super();

        spawnPoint = new  Vector2(x,y);
        opacity = 1.0f;

        //body things

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape hitbox = new CircleShape();
        hitbox.setRadius(5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitbox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        timeSinceAttack = 0;

        rotation = Slime.LEFT;
    }

    public void update(float delta) {
        move();
        shoot();
        super.update(delta);
    }

    public void draw() {

    }


    public void move() {

        

    }

    public void shoot() {
        if(timeSinceAttack <= 5) {
            //shooty shooty
        }
    }


}
