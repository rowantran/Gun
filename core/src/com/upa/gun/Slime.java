package com.upa.gun;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
//is this the right import?


public class Slime extends Enemy {
    int timeSinceAttack;

    Body body;

    int rotation;
    public static int LEFT = 0;
    public static int RIGHT = 1;

    public float attackTimeElapsed;

    public Slime(float x, float y, World world, GunWorld gunWorld) {
        super(gunWorld);
        attackTimeElapsed = 0.0f;
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

        rotation = Slime.LEFT;
    }

    public void update(float delta) {
        if (shooting) {
            attackTimeElapsed += delta;
        } else {
            timeElapsed += delta;
        }

        if(timeElapsed >= 3) {
            shooting = true;
            if(attackTimeElapsed >= 0.75) {
                attackTimeElapsed = 0;
                timeElapsed = 0;
                shooting = false;
            }
        } else {
            shooting = false;
        }

        move(delta);
        shoot();
    }


    public void move(float delta) {

        int playerX = (int) gunWorld.player.body.getTransform().getPosition().x;
        int playerY = (int) gunWorld.player.body.getTransform().getPosition().y;

        int slimeX = (int) body.getTransform().getPosition().x;
        int slimeY = (int) body.getTransform().getPosition().y;


        if(!shooting) {
            if (slimeX < playerX) {
                if (slimeY < playerY) {
                    body.setLinearVelocity(Settings.SLIME_SPEED * 3/4, Settings.SLIME_SPEED * 3/4);
                }
                if (slimeY > playerY) {
                    body.setLinearVelocity(Settings.SLIME_SPEED * 3/4, -Settings.SLIME_SPEED * 3/4);
                }
                if (slimeY == playerY) {
                    body.setLinearVelocity(Settings.SLIME_SPEED, 0);
                }
            }
            if (slimeX > playerX) {
                if (slimeY < playerY) {
                    body.setLinearVelocity(-Settings.SLIME_SPEED * 3/4, Settings.SLIME_SPEED * 3/4);
                }
                if (slimeY > playerY) {
                    body.setLinearVelocity(-Settings.SLIME_SPEED * 3/4, -Settings.SLIME_SPEED * 3/4);
                }
                if (slimeY == playerY) {
                    body.setLinearVelocity(-Settings.SLIME_SPEED, 0);
                }
            }
            if (slimeX == playerX) {
                if (slimeY < playerY) {
                    body.setLinearVelocity(0, Settings.SLIME_SPEED);
                }
                if (slimeY > playerY) {
                    body.setLinearVelocity(0, -Settings.SLIME_SPEED);
                }
                if(slimeY == playerY) {
                    body.setLinearVelocity(0, 0);
                }
            }
        }

        if(shooting) {
            body.setLinearVelocity(0, 0);
        }

    }

    public void shoot() {
        if (shooting) {
            // shooty shooty
        } else {
            // no shooty shooty
        }
    }


}
